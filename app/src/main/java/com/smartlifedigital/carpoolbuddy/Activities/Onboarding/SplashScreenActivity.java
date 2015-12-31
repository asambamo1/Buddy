package com.smartlifedigital.carpoolbuddy.Activities.Onboarding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.smartlifedigital.carpoolbuddy.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexanderchiou on 12/24/15.
 */
public class SplashScreenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.signup)
    public void signUp(View view) {
        startActivity(new Intent(this, LocationActivity.class));
    }

    @OnClick(R.id.login)
    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
    }
}
