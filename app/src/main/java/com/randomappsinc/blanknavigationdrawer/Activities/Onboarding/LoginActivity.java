package com.randomappsinc.blanknavigationdrawer.Activities.Onboarding;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.blanknavigationdrawer.API.Callbacks.LoginCallback;
import com.randomappsinc.blanknavigationdrawer.API.Models.LoginBundle;
import com.randomappsinc.blanknavigationdrawer.API.Models.SnackbarEvent;
import com.randomappsinc.blanknavigationdrawer.API.RestClient;
import com.randomappsinc.blanknavigationdrawer.Activities.MainActivity;
import com.randomappsinc.blanknavigationdrawer.Activities.StandardActivity;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 12/24/15.
 */
public class LoginActivity extends StandardActivity {
    public static final String SCREEN_TAG = "LoginActivity";

    @Bind(R.id.parent) View parent;
    @Bind(R.id.username_input) EditText username;
    @Bind(R.id.password_input) EditText password;

    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        password.setTypeface(Typeface.DEFAULT);

        progressDialog = new MaterialDialog.Builder(this)
                .content(R.string.logging_you_in)
                .progress(true, 0)
                .cancelable(false)
                .build();
    }

    @OnClick(R.id.login)
    public void login(View view) {
        String usernameInput = username.getText().toString();
        String passwordInput = password.getText().toString();

        LoginBundle loginBundle = new LoginBundle();
        loginBundle.setPassword(FormUtils.getMD5Hash(passwordInput));

        if (!FormUtils.isValidPhoneNumber(usernameInput) && !FormUtils.isValidEmailAddress(usernameInput)) {
            FormUtils.showSnackbar(parent, getString(R.string.invalid_username));
        }
        else {
            FormUtils.hideKeyboard(this);
            progressDialog.show();

            if (FormUtils.isValidEmailAddress(usernameInput)) {
                loginBundle.setEmail(usernameInput);
                loginBundle.setPhoneNumber("");
            }
            else {
                loginBundle.setPhoneNumber(usernameInput.replaceAll("[^0-9]+", ""));
                loginBundle.setEmail("");
            }

            LoginCallback callback = new LoginCallback();
            RestClient.getInstance().getUserService().login(loginBundle).enqueue(callback);
        }
    }

    public void onEvent(String basicEvent) {
        if (basicEvent.equals(LoginCallback.LOGIN_SUCCESS)) {
            progressDialog.dismiss();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
