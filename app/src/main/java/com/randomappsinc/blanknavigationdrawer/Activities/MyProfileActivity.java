package com.randomappsinc.blanknavigationdrawer.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.blanknavigationdrawer.API.Callbacks.UpdateProfileCallback;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.SnackbarEvent;
import com.randomappsinc.blanknavigationdrawer.API.Models.User;
import com.randomappsinc.blanknavigationdrawer.API.RestClient;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;
import com.randomappsinc.blanknavigationdrawer.Utils.PreferencesManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 12/20/15.
 */
public class MyProfileActivity extends StandardActivity {
    public static final String SCREEN_TAG = "MyProfileActivity";

    @Bind(R.id.parent) View parent;
    @Bind(R.id.name_input) EditText nameInput;
    @Bind(R.id.gender_input) EditText genderInput;
    @Bind(R.id.about_me_input) EditText aboutMeInput;
    @Bind(R.id.home_input) EditText homeZipInput;
    @Bind(R.id.work_input) EditText workZipInput;
    @Bind(R.id.email_input) EditText emailInput;
    @Bind(R.id.phone_input) EditText phoneInput;

    private int currentGenderIndex;
    private String lastSeenPhoneNumber;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        User user = PreferencesManager.get().getProfile();
        nameInput.setText(user.getName());
        genderInput.setText(user.getGender());
        aboutMeInput.setText(user.getAboutMe());
        homeZipInput.setText(String.valueOf(user.getHomeZip()));
        workZipInput.setText(String.valueOf(user.getWorkZip()));
        emailInput.setText(user.getEmail());
        phoneInput.setText(user.getPhoneNumber());

        currentGenderIndex = FormUtils.getGenderIndex(user.getGender());

        progressDialog = new MaterialDialog.Builder(this)
                .content(R.string.updating_your_profile)
                .progress(true, 0)
                .cancelable(false)
                .build();
    }

    @OnClick(R.id.gender_input)
    public void chooseGender(View view) {
        new MaterialDialog.Builder(this)
                .items(R.array.genders)
                .itemsCallbackSingleChoice(currentGenderIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        currentGenderIndex = which;
                        genderInput.setText(text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    @OnTextChanged(value = R.id.phone_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void phoneChanged(Editable s) {
        if (!s.toString().equals(lastSeenPhoneNumber)) {
            String formattedValue = FormUtils.formatUSNumber(s.toString());
            lastSeenPhoneNumber = formattedValue;
            phoneInput.setText(formattedValue);
            phoneInput.setSelection(formattedValue.length());
        }
    }

    @OnClick(R.id.save_changes)
    public void saveChanges(View view) {
        String enteredHomeZip = homeZipInput.getText().toString();
        String enteredWorkZip = workZipInput.getText().toString();
        String enteredEmail = emailInput.getText().toString();
        String enteredPhoneNumber = phoneInput.getText().toString();
        if (nameInput.getText().toString().isEmpty()) {
            FormUtils.showSnackbar(parent, getString(R.string.no_name));
        }
        else if (aboutMeInput.getText().toString().isEmpty()) {
            FormUtils.showSnackbar(parent, getString(R.string.no_about_me));
        }
        else if (enteredHomeZip.isEmpty() || enteredHomeZip.length() < 5) {
            FormUtils.showSnackbar(parent, getString(R.string.invalid_work_zip));
        }
        else if (enteredWorkZip.isEmpty() || enteredWorkZip.length() < 5) {
            FormUtils.showSnackbar(parent, getString(R.string.invalid_work_zip));
        }
        else if (!enteredEmail.isEmpty() && !FormUtils.isValidEmailAddress(enteredEmail)) {
            FormUtils.showSnackbar(parent, getString(R.string.invalid_email));
        }
        else if (!enteredPhoneNumber.isEmpty() && !FormUtils.isValidPhoneNumber(enteredPhoneNumber)) {
            FormUtils.showSnackbar(parent, getString(R.string.invalid_phone));
        }
        else if (enteredEmail.isEmpty() && enteredPhoneNumber.isEmpty()) {
            FormUtils.showSnackbar(parent, getString(R.string.no_contact_info));
        }
        else {
            FormUtils.hideKeyboard(this);
            progressDialog.show();

            User user = new User();
            user.setUserId(PreferencesManager.get().getProfile().getUserId());
            user.setName(nameInput.getText().toString());
            user.setGender(genderInput.getText().toString());
            user.setAboutMe(aboutMeInput.getText().toString());
            user.setHomeZip(Integer.parseInt(enteredHomeZip));
            user.setWorkZip(Integer.parseInt(enteredWorkZip));
            user.setEmail(enteredEmail);
            user.setPhoneNumber(enteredPhoneNumber.replaceAll("[^0-9]+", ""));

            UpdateProfileCallback callback = new UpdateProfileCallback(user);
            RestClient.getInstance().getUserService().updateProfile(user).enqueue(callback);
        }
    }

    public void onEvent(String basicEvent) {
        if (basicEvent.equals(UpdateProfileCallback.UPDATE_PROFILE_SUCCESS)) {
            progressDialog.dismiss();
            FormUtils.showSnackbar(parent, getString(R.string.profile_update_success));
        }
    }

    public void onEvent(SnackbarEvent event) {
        if (event.getScreen().equals(SCREEN_TAG)) {
            progressDialog.dismiss();
            FormUtils.showSnackbar(parent, event.getMessage());
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
