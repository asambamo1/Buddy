package com.randomappsinc.blanknavigationdrawer.Activities;

import android.os.Bundle;

import com.randomappsinc.blanknavigationdrawer.R;

import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 12/27/15.
 */
public class ProfileActivity extends StandardActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
