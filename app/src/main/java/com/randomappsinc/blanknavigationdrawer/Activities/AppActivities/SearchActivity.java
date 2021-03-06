package com.randomappsinc.blanknavigationdrawer.Activities.AppActivities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.randomappsinc.blanknavigationdrawer.Fragments.SearchFragment;
import com.randomappsinc.blanknavigationdrawer.R;

public class SearchActivity extends AppCompatActivity {
    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Search");
        actionBar.setDisplayHomeAsUpEnabled(true);

        searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.search_params, searchFragment, "params").commit();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0 , R.anim.slide_down_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
