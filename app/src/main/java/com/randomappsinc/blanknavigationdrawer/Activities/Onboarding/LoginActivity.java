package com.randomappsinc.blanknavigationdrawer.Activities.Onboarding;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;

import com.randomappsinc.blanknavigationdrawer.Activities.StandardActivity;
import com.randomappsinc.blanknavigationdrawer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 12/24/15.
 */
public class LoginActivity extends StandardActivity {
    @Bind(R.id.password_input) EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        password.setTypeface(Typeface.DEFAULT);
    }
}
