package com.randomappsinc.blanknavigationdrawer.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.widget.IconTextView;
import com.randomappsinc.blanknavigationdrawer.API.Callbacks.ChangeVisibilityCallback;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.ChangeVisibilityEvent;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.SnackbarEvent;
import com.randomappsinc.blanknavigationdrawer.API.RestClient;
import com.randomappsinc.blanknavigationdrawer.Adapters.FontAwesomeAdapter;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.PreferencesManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 12/20/15.
 */
public class NavigationDrawerFragment extends Fragment {
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private NavigationDrawerCallbacks mCallbacks;
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;
    ProgressDialog myDialog;

    @Bind(R.id.nav_drawer_tabs) ListView mDrawerListView;
    @Bind(R.id.name)TextView mName;
    @Bind(R.id.visibility) IconTextView visible;

    private int mCurrentSelectedPosition = 0;

    public NavigationDrawerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout navDrawer = (LinearLayout) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        ButterKnife.bind(this, navDrawer);
        mDrawerListView.setAdapter(new FontAwesomeAdapter(getActivity(),
                getResources().getStringArray(R.array.nav_drawer_tabs),
                getResources().getStringArray(R.array.nav_drawer_icons)));
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        if (!PreferencesManager.get().getProfile().getVisible())
            visible.setTextColor(getResources().getColor(R.color.light_gray));

        return navDrawer;
    }

    @Override
    public void onResume() {
        super.onResume();
        mName.setText(PreferencesManager.get().getProfile().getName());
    }

    //The standard for eventbus is to register/uniregister on onStart/onStop
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @OnItemClick(R.id.nav_drawer_tabs)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    @OnClick(R.id.visibility)
    public void changeVisibility() {
        String visible = Boolean.toString(!PreferencesManager.get().getProfile().getVisible());
        //String visible = Boolean.toString(false);
        String user_id = Long.toString(PreferencesManager.get().getProfile().getUserId());

        myDialog = new ProgressDialog(getActivity());
        myDialog.setMessage(getActivity().getString(R.string.changing));
        myDialog.setCancelable(false);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show();

        ChangeVisibilityCallback callback = new ChangeVisibilityCallback();
        RestClient.getInstance().getUserService().changeVisibility(visible, user_id).enqueue(callback);
        //Toast.makeText(getActivity(), Boolean.toString(PreferencesManager.get().getVisiblity()), Toast.LENGTH_SHORT).show();
    }

    public void onEvent(ChangeVisibilityEvent event) {
        String msg = event.getResponse();
        myDialog.hide();
        if (msg.equals(getActivity().getString(R.string.success))) {
            boolean newVisibility = !PreferencesManager.get().getProfile().getVisible();

            if (newVisibility == true) {
                Toast.makeText(getActivity(), getActivity().getString(R.string.visible), Toast.LENGTH_SHORT).show();
                visible.setTextColor(getResources().getColor(R.color.black));
            }
            else {
                Toast.makeText(getActivity(), getActivity().getString(R.string.invisible), Toast.LENGTH_SHORT).show();
                visible.setTextColor(getResources().getColor(R.color.light_gray));
            }
            PreferencesManager.get().setVisibility(newVisibility);
        }
        else
            Toast.makeText(getActivity(), getActivity().getString(R.string.failure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set up the drawer's list view with items and click listener
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                toolbar,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.menu_main, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.app_name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    public interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position);
    }
}

