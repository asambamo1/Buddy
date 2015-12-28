package com.randomappsinc.blanknavigationdrawer.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.randomappsinc.blanknavigationdrawer.API.Callbacks.UserThumbnailsCallback;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.UserThumbnailsEvent;
import com.randomappsinc.blanknavigationdrawer.API.RestClient;
import com.randomappsinc.blanknavigationdrawer.Activities.ProfileActivity;
import com.randomappsinc.blanknavigationdrawer.Adapters.RequestsAdapter;
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
public class RequestsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {

    @Bind(R.id.parent) View parent;
    @Bind(R.id.loading) View loadingRequest;
    @Bind(R.id.fetch_new_content) SwipeRefreshLayout updateRequests;
    @Bind(R.id.content) ListView requests;
    @Bind(R.id.no_content) TextView noRequests;

    private String requestType;
    private RequestsAdapter requestsAdapter;

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

        requestsAdapter = new RequestsAdapter(getActivity());
        requests.setAdapter(requestsAdapter);

        noRequests.setText(R.string.no_requests);
        updateRequests.setColorSchemeResources(R.color.red, R.color.yellow, R.color.green, R.color.app_blue);
        updateRequests.setOnRefreshListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRequests.setRefreshing(true);
        UserThumbnailsCallback callback = new UserThumbnailsCallback(requestType);
        String user_id = String.valueOf(PreferencesManager.get().getProfile().getUserId());
        RestClient.getInstance().getUserService().fetchRequests(requestType, user_id).enqueue(callback);
    }

    @OnItemClick(R.id.content)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(Constants.USER_ID_KEY, requestsAdapter.getItem(position).getUserId());
        getActivity().startActivity(intent);
    }

    public void onEvent(UserThumbnailsEvent response) {
        if (response.getScreen().equals(requestType)) {
            loadingRequest.setVisibility(View.GONE);
            updateRequests.setVisibility(View.VISIBLE);
            updateRequests.setRefreshing(false);
            if (response.getUserThumbnailsList() == null)
                FormUtils.showSnackbar(parent, getString(R.string.connections_fetch_fail));
            else if (response.getUserThumbnailsList().size() == 0) {
                noRequests.setVisibility(View.VISIBLE);
            }
            else {
                noRequests.setVisibility(View.GONE);
                requestsAdapter.setRequests(response.getUserThumbnailsList());
            }
        }
    }

    @Override
    public void onRefresh() {
        UserThumbnailsCallback callback = new UserThumbnailsCallback(requestType);
        String user_id = String.valueOf(PreferencesManager.get().getProfile().getUserId());
        RestClient.getInstance().getUserService().fetchRequests(requestType, user_id).enqueue(callback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
