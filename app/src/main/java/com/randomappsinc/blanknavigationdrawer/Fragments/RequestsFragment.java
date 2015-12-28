package com.randomappsinc.blanknavigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 12/27/15.
 */
public class RequestsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {
    @Bind(R.id.parent) View parent;
    @Bind(R.id.loading) View loadingLeaderboard;
    @Bind(R.id.fetch_new_content) SwipeRefreshLayout updateLeaderboard;
    @Bind(R.id.content) ListView leaderboard;
    @Bind(R.id.no_content) TextView noLeaderboard;

    private String requestType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.single_list_layout, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        requestType = getArguments().getString(Constants.REQUEST_TYPE_KEY);

        noLeaderboard.setText(R.string.no_requests);
        updateLeaderboard.setColorSchemeResources(R.color.red, R.color.yellow, R.color.green, R.color.app_blue);
        updateLeaderboard.setOnRefreshListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onEvent(String event) {

    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
