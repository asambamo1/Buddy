package com.randomappsinc.blanknavigationdrawer.Activities.Onboarding;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;

import com.randomappsinc.blanknavigationdrawer.Activities.StandardActivity;
import com.randomappsinc.blanknavigationdrawer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 12/25/15.
 */
public class PasswordActivity extends StandardActivity{
    @Bind(R.id.password_input) EditText password;
    @Bind(R.id.confirm_password_input) EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_form);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        password.setTypeface(Typeface.DEFAULT);
        confirmPassword.setTypeface(Typeface.DEFAULT);
    }
}
