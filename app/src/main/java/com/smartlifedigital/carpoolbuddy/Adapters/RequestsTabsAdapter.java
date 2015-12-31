package com.smartlifedigital.carpoolbuddy.Adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.smartlifedigital.carpoolbuddy.Fragments.RequestsFragment;
import com.smartlifedigital.carpoolbuddy.R;
import com.smartlifedigital.carpoolbuddy.Utils.Constants;
import com.smartlifedigital.carpoolbuddy.Utils.MyApplication;

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
