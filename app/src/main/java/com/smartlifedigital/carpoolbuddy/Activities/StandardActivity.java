package com.smartlifedigital.carpoolbuddy.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.smartlifedigital.carpoolbuddy.R;
import com.smartlifedigital.carpoolbuddy.Utils.FormUtils;

/**
 * Created by Aravind on 12/22/2015.
 */
public class StandardActivity extends AppCompatActivity {
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        FormUtils.hideKeyboard(this);
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
    }

    @Override
    public void finish() {
        FormUtils.hideKeyboard(this);
        super.finish();
        overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
