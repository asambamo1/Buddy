package com.randomappsinc.blanknavigationdrawer.Activities.Onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.blanknavigationdrawer.API.Models.User;
import com.randomappsinc.blanknavigationdrawer.Activities.AppActivities.StandardActivity;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.Constants;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexanderchiou on 12/23/15.
 */
public class AboutMeActivity extends StandardActivity {
    @Bind(R.id.parent) View parent;
    @Bind(R.id.name_input) EditText nameInput;
    @Bind(R.id.gender_input) EditText genderInput;
    @Bind(R.id.about_me_input) EditText aboutMeInput;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_me_form);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = getIntent().getParcelableExtra(Constants.USER_KEY);
    }

    @OnClick(R.id.gender_input)
    public void chooseGender(View view) {
        int currentGenderIndex = FormUtils.getGenderIndex(genderInput.getText().toString());
        new MaterialDialog.Builder(this)
                .items(R.array.genders)
                .itemsCallbackSingleChoice(currentGenderIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        genderInput.setText(text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    @OnClick(R.id.next)
    public void nextPage(View view) {
        String enteredName = nameInput.getText().toString();
        String enteredGender = genderInput.getText().toString();
        String enteredAboutMe = aboutMeInput.getText().toString();
        if (enteredName.isEmpty()) {
            FormUtils.showSnackbar(parent, getString(R.string.no_name));
        }
        else if (enteredGender.isEmpty()) {
            FormUtils.showSnackbar(parent, getString(R.string.no_gender));
        }
        else if (enteredAboutMe.isEmpty()) {
            FormUtils.showSnackbar(parent, getString(R.string.no_about_me));
        }
        else {
            user.setName(enteredName);
            user.setGender(enteredGender);
            user.setAboutMe(enteredAboutMe);

            Intent intent = new Intent(this, ContactInfoActivity.class);
            intent.putExtra(Constants.USER_KEY, user);
            startActivity(intent);
        }
    }
}
