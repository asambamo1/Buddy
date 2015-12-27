package com.randomappsinc.blanknavigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.randomappsinc.blanknavigationdrawer.API.Callbacks.SuggestionsCallback;
import com.randomappsinc.blanknavigationdrawer.API.Models.SuggestionsEvent;
import com.randomappsinc.blanknavigationdrawer.API.RestClient;
import com.randomappsinc.blanknavigationdrawer.Adapters.SuggestionsAdapter;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;
import com.randomappsinc.blanknavigationdrawer.Utils.PreferencesManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 12/27/15.
 */
public class SuggestionsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.loading) View loadingSuggestions;
    @Bind(R.id.content) ListView suggestions;
    @Bind(R.id.parent) View parent;
    @Bind(R.id.fetch_new_content) SwipeRefreshLayout fetchNewStories;
    @Bind(R.id.no_content) TextView noStories;

    private SuggestionsAdapter suggestionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.suggestions, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);

        suggestionsAdapter = new SuggestionsAdapter(getActivity());
        suggestions.setAdapter(suggestionsAdapter);
        fetchNewStories.setColorSchemeResources(R.color.red, R.color.yellow, R.color.green, R.color.app_blue);
        fetchNewStories.setOnRefreshListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        long user_id = PreferencesManager.get().getProfile().getUserId();
        if (user_id != -1) {
            SuggestionsCallback callback = new SuggestionsCallback();
            RestClient.getInstance().getSuggestionService().fetchSuggestions(String.valueOf(user_id)).enqueue(callback);
        }
    }

    public void onEvent(SuggestionsEvent response) {
        loadingSuggestions.setVisibility(View.GONE);
        if (response.getSuggestionList() == null)
            FormUtils.showSnackbar(parent, getString(R.string.suggestions_fetch_error));
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
