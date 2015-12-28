package com.randomappsinc.blanknavigationdrawer.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.randomappsinc.blanknavigationdrawer.Adapters.RequestsTabsAdapter;
import com.randomappsinc.blanknavigationdrawer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 12/20/15.
 */
public class RequestsActivity extends StandardActivity {
    @Bind(R.id.tab_layout) TabLayout leaderboardNames;
    @Bind(R.id.view_pager) ViewPager leaderboardPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requests);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        leaderboardPager.setAdapter(new RequestsTabsAdapter(getFragmentManager()));
        leaderboardNames.setupWithViewPager(leaderboardPager);
    }
}
