package com.randomappsinc.blanknavigationdrawer.LoginActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.randomappsinc.blanknavigationdrawer.Activities.StandardActivity;
import com.randomappsinc.blanknavigationdrawer.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationActivity extends StandardActivity {

    @Bind(R.id.coordinator_layout)View Parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.next)
    public void next(){
        //FormUtils.showSnackbar(Parent, "Submit Clicked");
        startActivity(new Intent(this, ProfileActivity.class));
    }
}
