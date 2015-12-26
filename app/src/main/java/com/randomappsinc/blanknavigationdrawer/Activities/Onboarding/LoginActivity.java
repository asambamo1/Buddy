package com.randomappsinc.blanknavigationdrawer.Activities.Onboarding;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.blanknavigationdrawer.Activities.StandardActivity;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexanderchiou on 12/24/15.
 */
public class LoginActivity extends StandardActivity {
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
        FormUtils.hideKeyboard(this);

        progressDialog.show();
    }
}
