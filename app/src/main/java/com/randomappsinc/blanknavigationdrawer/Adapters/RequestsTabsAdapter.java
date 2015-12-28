package com.randomappsinc.blanknavigationdrawer.Adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.randomappsinc.blanknavigationdrawer.Fragments.RequestsFragment;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.Constants;
import com.randomappsinc.blanknavigationdrawer.Utils.MyApplication;

/**
 * Created by alexanderchiou on 12/27/15.
 */
public class RequestsTabsAdapter extends FragmentStatePagerAdapter{
    public String[] runTypes;

    public RequestsTabsAdapter (FragmentManager fragmentManager) {
        super(fragmentManager);
        runTypes = MyApplication.getAppContext().getResources().getStringArray(R.array.request_types);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.REQUEST_TYPE_KEY, runTypes[position]);
        RequestsFragment requestsFragment = new RequestsFragment();
        requestsFragment.setArguments(bundle);
        return requestsFragment;
    }

    @Override
    public int getCount() {
        return runTypes.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return runTypes[position];
    }
}
