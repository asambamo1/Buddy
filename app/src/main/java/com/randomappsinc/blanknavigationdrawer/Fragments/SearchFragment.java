package com.randomappsinc.blanknavigationdrawer.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.blanknavigationdrawer.API.Callbacks.UserThumbnailsCallback;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.UserThumbnailsEvent;
import com.randomappsinc.blanknavigationdrawer.API.Models.User;
import com.randomappsinc.blanknavigationdrawer.API.RestClient;
import com.randomappsinc.blanknavigationdrawer.Activities.AppActivities.ProfileActivity;
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

public class SearchFragment extends Fragment {

    @Bind(R.id.parent) View parent;
    @Bind(R.id.gender_input) EditText genderInput;
    @Bind(R.id.home_input) EditText homeZipInput;
    @Bind(R.id.work_input) EditText workZipInput;
    @Bind(R.id.results) ListView results;
    @Bind(R.id.input) View input;
    @Bind(R.id.noresult) TextView noresult;

    private int currentGenderIndex;
    private ProgressDialog progressDialog;
    private UserThumbnailsAdapter adapter;

    public static final String SCREEN_TAG = "SuggestionsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, parentViewGroup, false);
        ButterKnife.bind(this, view);
        adapter = new UserThumbnailsAdapter(getActivity());
        results.setAdapter(adapter);
        return view;
    }

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

    public void onEvent(UserThumbnailsEvent response) {
        progressDialog.hide();
        if (response.getScreen().equals(SCREEN_TAG)) {
            results.setVisibility(View.VISIBLE);
            if (response.getUserThumbnailsList() == null) {
                FormUtils.showSnackbar(parent, getString(R.string.suggestions_fetch_error));
            }
            else {
                adapter.setSuggestions(response.getUserThumbnailsList());
            }
            if (adapter.getCount() == 0) {
                Animation show =  new AlphaAnimation(0.0f, 1.0f);
                show.setDuration(250);
                noresult.startAnimation(show);
                noresult.setVisibility(View.VISIBLE);
            }
            else {
                noresult.setVisibility(View.GONE);
            }
        }
    }

    @OnItemClick(R.id.results)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(Constants.USER_ID_KEY, adapter.getItem(position).getUserId());
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
    }

    @OnClick(R.id.gender_input)
    public void chooseGender(View view) {
        new MaterialDialog.Builder(getActivity()).items(R.array.search_gender)
                .itemsCallbackSingleChoice(currentGenderIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        currentGenderIndex = which;
                        genderInput.setText(text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    @OnClick({R.id.autofill_home, R.id.autofill_work})
    public void autoFillCurrentZip(View view) {
        FormUtils.hideKeyboard(getActivity());
        String currentZip = FormUtils.getCurrentZip();
        if (currentZip.isEmpty()) {
            FormUtils.showSnackbar(parent, getString(R.string.need_gps));
        }
        else {
            switch (view.getId()) {
                case R.id.autofill_home:
                    homeZipInput.setText(currentZip);
                    break;
                case R.id.autofill_work:
                    workZipInput.setText(currentZip);
                    break;
            }
        }
    }

    private boolean emptyInput() {
        return genderInput.getText().toString().isEmpty() || homeZipInput.getText().toString().isEmpty() ||
                workZipInput.getText().toString().isEmpty();
    }

    @OnClick(R.id.search)
    public void onSearch() {
        FormUtils.hideKeyboard(getActivity());

        if (emptyInput()) {
            FormUtils.showSnackbar(parent, getActivity().getString(R.string.empty_input));
            return;
        }

        Animation hide =  new AlphaAnimation(1.0f, 0.0f);
        hide.setDuration(250);
        hide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                input.setVisibility(View.GONE);
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage(getActivity().getString(R.string.searching));
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                User yourInfo = PreferencesManager.get().getProfile();
                String gender_pref;
                if (currentGenderIndex == 2)
                    gender_pref = getActivity().getString(R.string.nopref);
                else
                    gender_pref = genderInput.getText().toString();

                UserThumbnailsCallback callback = new UserThumbnailsCallback(SCREEN_TAG);
                RestClient.getInstance().getMatchingService().searchResults(Long.toString(yourInfo.getUserId()), gender_pref,
                        homeZipInput.getText().toString(), workZipInput.getText().toString()).enqueue(callback);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        input.startAnimation(hide);
    }
}
