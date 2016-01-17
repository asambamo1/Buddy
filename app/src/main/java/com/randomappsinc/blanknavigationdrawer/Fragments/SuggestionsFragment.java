package com.randomappsinc.blanknavigationdrawer.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.randomappsinc.blanknavigationdrawer.API.Callbacks.UserThumbnailsCallback;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.UserThumbnailsEvent;
import com.randomappsinc.blanknavigationdrawer.API.RestClient;
import com.randomappsinc.blanknavigationdrawer.Activities.ProfileActivity;
import com.randomappsinc.blanknavigationdrawer.Activities.SearchActivity;
import com.randomappsinc.blanknavigationdrawer.Adapters.UserThumbnailsAdapter;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.Constants;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;
import com.randomappsinc.blanknavigationdrawer.Utils.PreferencesManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 12/27/15.
 */
public class SuggestionsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String SCREEN_TAG = "SuggestionsFragment";

    @Bind(R.id.parent) View parent;
    @Bind(R.id.loading) View loadingSuggestions;
    @Bind(R.id.content) ListView suggestions;
    @Bind(R.id.search_button) FloatingActionButton search;
    @Bind(R.id.fetch_new_content) SwipeRefreshLayout fetchNewSuggestions;
    @Bind(R.id.no_content) TextView noSuggestions;

    private UserThumbnailsAdapter suggestionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.single_list_layout, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);

        noSuggestions.setText(R.string.no_suggestions);
        suggestionsAdapter = new UserThumbnailsAdapter(getActivity());
        suggestions.setAdapter(suggestionsAdapter);
        fetchNewSuggestions.setColorSchemeResources(R.color.red, R.color.yellow, R.color.green, R.color.app_blue);
        fetchNewSuggestions.setOnRefreshListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        long userId = PreferencesManager.get().getProfile().getUserId();
        if (userId != -1) {
            fetchNewSuggestions.setRefreshing(true);
            UserThumbnailsCallback callback = new UserThumbnailsCallback(SCREEN_TAG);
            RestClient.getInstance().getMatchingService().fetchSuggestions(String.valueOf(userId)).enqueue(callback);
        }
    }

    @OnClick(R.id.search_button)
    public void onSearch() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_up_in, R.anim.no_anim);
        //Toast.makeText(getActivity(), "Search!", Toast.LENGTH_SHORT).show();
    }

    //for handling changes to visbility
    public void onEvent(UserThumbnailsEvent response) {
        if (response.getScreen().equals(SCREEN_TAG)) {
            loadingSuggestions.setVisibility(View.GONE);
            fetchNewSuggestions.setVisibility(View.VISIBLE);
            fetchNewSuggestions.setRefreshing(false);
            if (response.getUserThumbnailsList() == null) {
                FormUtils.showSnackbar(parent, getString(R.string.suggestions_fetch_error));
            }
            else {
                suggestionsAdapter.setSuggestions(response.getUserThumbnailsList());
            }
            if (suggestionsAdapter.getCount() == 0) {
                noSuggestions.setVisibility(View.VISIBLE);
            }
            else {
                noSuggestions.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onRefresh() {
        long userId = PreferencesManager.get().getProfile().getUserId();
        UserThumbnailsCallback callback = new UserThumbnailsCallback(SCREEN_TAG);
        RestClient.getInstance().getMatchingService().fetchSuggestions(String.valueOf(userId)).enqueue(callback);
    }

    @OnItemClick(R.id.content)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(Constants.USER_ID_KEY, suggestionsAdapter.getItem(position).getUserId());
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
