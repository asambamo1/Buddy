package com.randomappsinc.blanknavigationdrawer.Activities.Onboarding;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.blanknavigationdrawer.API.Callbacks.CreateAccountCallback;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.SnackbarEvent;
import com.randomappsinc.blanknavigationdrawer.API.Models.User;
import com.randomappsinc.blanknavigationdrawer.API.RestClient;
import com.randomappsinc.blanknavigationdrawer.Activities.AppActivities.MainActivity;
import com.randomappsinc.blanknavigationdrawer.Activities.AppActivities.StandardActivity;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.Constants;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 12/25/15.
 */
public class PasswordActivity extends StandardActivity {
    public static final String SCREEN_TAG = "PasswordActivity";

    @Bind(R.id.parent) View parent;
    @Bind(R.id.password_input) EditText password;
    @Bind(R.id.confirm_password_input) EditText confirmPassword;

    private User user;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_form);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = getIntent().getParcelableExtra(Constants.USER_KEY);

        password.setTypeface(Typeface.DEFAULT);
        confirmPassword.setTypeface(Typeface.DEFAULT);

        progressDialog = new MaterialDialog.Builder(this)
                .content(R.string.creating_your_account)
                .progress(true, 0)
                .cancelable(false)
                .build();
    }

    @OnClick(R.id.create_account)
    public void createAccount(View view) {
        String passwordInput = password.getText().toString();
        String confirmPasswordInput = confirmPassword.getText().toString();

        if (!passwordInput.equals(confirmPasswordInput)) {
            FormUtils.showSnackbar(parent, getString(R.string.non_matching_passwords));
        }
        else if (passwordInput.length() < Constants.MIN_PASSWORD_LENGTH) {
            FormUtils.showSnackbar(parent, getString(R.string.password_too_short));
        }
        else {
            FormUtils.hideKeyboard(this);
            progressDialog.show();

            user.setPassword(FormUtils.getMD5Hash(passwordInput));
            CreateAccountCallback callback = new CreateAccountCallback(user);
            RestClient.getInstance().getUserService().createAccount(user).enqueue(callback);
        }
    }

    public void onEvent(String basicEvent) {
        if (basicEvent.equals(CreateAccountCallback.CREATE_ACCOUNT_SUCCESS)) {
            progressDialog.dismiss();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
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
