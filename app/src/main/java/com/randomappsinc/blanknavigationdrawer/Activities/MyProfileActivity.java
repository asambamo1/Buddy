package com.randomappsinc.blanknavigationdrawer.Activities;

import android.os.Bundle;
import android.view.View;

import com.randomappsinc.blanknavigationdrawer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 12/20/15.
 */
public class MyProfileActivity extends StandardActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}