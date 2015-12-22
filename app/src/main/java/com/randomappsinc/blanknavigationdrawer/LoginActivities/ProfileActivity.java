package com.randomappsinc.blanknavigationdrawer.LoginActivities;

import android.os.Bundle;
import android.view.View;

import com.randomappsinc.blanknavigationdrawer.Activities.StandardActivity;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Aravind on 12/21/2015.
 */
public class ProfileActivity extends StandardActivity {

    @Bind(R.id.coordinator_layout)
    View Parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.submit)
    public void submit() {
        FormUtils.showSnackbar(Parent, "Submit Clicked");
    }
}


