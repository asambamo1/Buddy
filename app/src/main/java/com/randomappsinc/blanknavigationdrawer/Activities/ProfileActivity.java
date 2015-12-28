package com.randomappsinc.blanknavigationdrawer.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.randomappsinc.blanknavigationdrawer.API.Callbacks.ProfileCallback;
import com.randomappsinc.blanknavigationdrawer.API.Models.Profile;
import com.randomappsinc.blanknavigationdrawer.API.Models.ProfileEvent;
import com.randomappsinc.blanknavigationdrawer.API.Models.SuggestionsEvent;
import com.randomappsinc.blanknavigationdrawer.API.RestClient;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.Constants;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;
import com.randomappsinc.blanknavigationdrawer.Utils.PreferencesManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 12/27/15.
 */
public class ProfileActivity extends StandardActivity {
    @Bind(R.id.loading) View loading;
    @Bind(R.id.profile) View profile;
    @Bind(R.id.name) TextView name;
    @Bind(R.id.about_me) TextView about_me;
    @Bind(R.id.village) TextView village;
    @Bind(R.id.work_zip) TextView work_zip;
    @Bind(R.id.email) TextView email;
    @Bind(R.id.phone_number) TextView phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EventBus.getDefault().register(this);
        String my_id = String.valueOf(PreferencesManager.get().getProfile().getUserId());
        String target_id = String.valueOf(getIntent().getLongExtra(Constants.USER_ID_KEY, 0));
        ProfileCallback callback = new ProfileCallback();
        RestClient.getInstance().getSuggestionService().getProfile(my_id, target_id).enqueue(callback);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(ProfileEvent response) {
        loading.setVisibility(View.GONE);
        profile.setVisibility(View.VISIBLE);
        if (response.getProfile() == null) {
        }
        else {
            name.setText(response.getProfile().getName());
            about_me.setText(response.getProfile().getAbout_me());
            village.setText(response.getProfile().getVillage());
            work_zip.setText(String.valueOf(response.getProfile().getZipCode()));
            email.setText(response.getProfile().getEmail());
            phone_number.setText(response.getProfile().getPhone_number());
        }
    }
}
