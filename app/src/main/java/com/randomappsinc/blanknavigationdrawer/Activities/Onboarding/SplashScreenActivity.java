package com.randomappsinc.blanknavigationdrawer.Activities.Onboarding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;

import butterknife.ButterKnife;

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

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        FormUtils.hideKeyboard(this);
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
    }

    @Override
    public void onBackPressed() {}
}
