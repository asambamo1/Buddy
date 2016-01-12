package com.randomappsinc.blanknavigationdrawer.Activities.Onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.randomappsinc.blanknavigationdrawer.API.Models.User;
import com.randomappsinc.blanknavigationdrawer.Activities.StandardActivity;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.Constants;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationActivity extends StandardActivity {
    @Bind(R.id.parent) View parent;
    @Bind(R.id.home_input) EditText homeInput;
    @Bind(R.id.work_input) EditText workInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_form);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.next)
    public void nextPage(View view) {
        String homeZip = homeInput.getText().toString();
        String workZip = workInput.getText().toString();
        if (homeZip.isEmpty() || homeZip.length() < 5) {
            FormUtils.showSnackbar(parent, getString(R.string.invalid_home_zip));
        }
        else if (workZip.isEmpty() || workZip.length() < 5) {
            FormUtils.showSnackbar(parent, getString(R.string.invalid_work_zip));
        }
        else {
            User user = new User();
            user.setHomeZip(Integer.parseInt(homeZip));
            user.setWorkZip(Integer.parseInt(workZip));

            Intent intent = new Intent(this, AboutMeActivity.class);
            intent.putExtra(Constants.USER_KEY, user);
            startActivity(intent);
        }
    }
}
