package com.randomappsinc.blanknavigationdrawer.Activities.AppActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.randomappsinc.blanknavigationdrawer.API.Callbacks.UserThumbnailsCallback;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.UserThumbnailsEvent;
import com.randomappsinc.blanknavigationdrawer.API.RestClient;
import com.randomappsinc.blanknavigationdrawer.Adapters.UserThumbnailsAdapter;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.Constants;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;
import com.randomappsinc.blanknavigationdrawer.Utils.PreferencesManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 12/27/15.
 */
public class ConnectionsActivity extends StandardActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String SCREEN_TAG = "ConnectionsActivity";

    @Bind(R.id.parent) View parent;
    @Bind(R.id.loading) View loadingConnections;
    @Bind(R.id.content) ListView connections;
    @Bind(R.id.fetch_new_content) SwipeRefreshLayout fetchNewConnections;
    @Bind(R.id.no_content) TextView noConnections;

    private UserThumbnailsAdapter connectionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noConnections.setText(R.string.no_connections);
        connectionsAdapter = new UserThumbnailsAdapter(this);
        connections.setAdapter(connectionsAdapter);
        fetchNewConnections.setColorSchemeResources(R.color.red, R.color.yellow, R.color.green, R.color.app_blue);
        fetchNewConnections.setOnRefreshListener(this);

        String user_id = String.valueOf(PreferencesManager.get().getProfile().getUserId());
        UserThumbnailsCallback callback = new UserThumbnailsCallback(SCREEN_TAG);
        RestClient.getInstance().getUserService().fetchConnections(user_id).enqueue(callback);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchNewConnections.setRefreshing(true);
        refreshConnections();
    }

    @Override
    public void onRefresh() {
        refreshConnections();
    }

    public void refreshConnections() {
        String userId = String.valueOf(PreferencesManager.get().getProfile().getUserId());
        UserThumbnailsCallback callback = new UserThumbnailsCallback(SCREEN_TAG);
        RestClient.getInstance().getUserService().fetchConnections(userId).enqueue(callback);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(UserThumbnailsEvent response) {
        if (response.getScreen().equals(SCREEN_TAG)) {
            loadingConnections.setVisibility(View.GONE);
            fetchNewConnections.setVisibility(View.VISIBLE);
            fetchNewConnections.setRefreshing(false);
            if (response.getUserThumbnailsList() == null) {
                FormUtils.showSnackbar(parent, getString(R.string.connections_fetch_fail));
            }
            else {
                connectionsAdapter.setSuggestions(response.getUserThumbnailsList());
            }
            if (connectionsAdapter.getCount() == 0) {
                noConnections.setVisibility(View.VISIBLE);
            }
            else {
                noConnections.setVisibility(View.GONE);
            }
        }
    }

    @OnItemClick(R.id.content)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(Constants.USER_ID_KEY, connectionsAdapter.getItem(position).getUserId());
        startActivity(intent);
    }
}
