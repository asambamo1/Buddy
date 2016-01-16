package com.randomappsinc.blanknavigationdrawer.Activities;

import android.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.randomappsinc.blanknavigationdrawer.Fragments.NavigationDrawerFragment;
import com.randomappsinc.blanknavigationdrawer.Fragments.SearchParamsFragment;
import com.randomappsinc.blanknavigationdrawer.R;

public class SearchActivity extends AppCompatActivity {
    private SearchParamsFragment searchParamsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Search");
        actionBar.setDisplayHomeAsUpEnabled(true);

        searchParamsFragment = new SearchParamsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.search_params, searchParamsFragment, "params").commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
