package com.randomappsinc.blanknavigationdrawer.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.randomappsinc.blanknavigationdrawer.API.Callbacks.ProfileCallback;
import com.randomappsinc.blanknavigationdrawer.API.Models.Profile;
import com.randomappsinc.blanknavigationdrawer.API.Models.ProfileEvent;
import com.randomappsinc.blanknavigationdrawer.API.RestClient;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.Constants;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;
import com.randomappsinc.blanknavigationdrawer.Utils.PreferencesManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 12/27/15.
 */
public class ProfileActivity extends StandardActivity {
    @Bind(R.id.parent) View parent;
    @Bind(R.id.loading) View loading;
    @Bind(R.id.profile) View profile;
    @Bind(R.id.name) TextView name;
    @Bind(R.id.about_me) TextView aboutMe;
    @Bind(R.id.village) TextView village;
    @Bind(R.id.work_zip) TextView workZip;
    @Bind(R.id.email) TextView email;
    @Bind(R.id.phone_number) TextView phoneNumber;

    private Profile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EventBus.getDefault().register(this);
        String myId = String.valueOf(PreferencesManager.get().getProfile().getUserId());
        String targetId = String.valueOf(getIntent().getLongExtra(Constants.USER_ID_KEY, 0));
        ProfileCallback callback = new ProfileCallback();
        RestClient.getInstance().getSuggestionService().getProfile(myId, targetId).enqueue(callback);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(ProfileEvent response) {
        userProfile = response.getProfile();
        loading.setVisibility(View.GONE);
        profile.setVisibility(View.VISIBLE);
        if (response.getProfile() == null) {
            FormUtils.showSnackbar(parent, getString(R.string.profile_fetch_fail));
        }
        else {
            name.setText(response.getProfile().getName());
            aboutMe.setText(response.getProfile().getAboutMe());
            village.setText(response.getProfile().getVillage());
            workZip.setText(String.valueOf(response.getProfile().getZipCode()));

            String userEmail = response.getProfile().getEmail().isEmpty()
                    ? getString(R.string.none_added)
                    : response.getProfile().getEmail();
            email.setText(userEmail);

            String userPhoneNumber = response.getProfile().getPhoneNumber().isEmpty()
                    ? getString(R.string.none_added)
                    : FormUtils.formatUSNumber(response.getProfile().getPhoneNumber());
            phoneNumber.setText(userPhoneNumber);
        }
    }

    @OnClick(R.id.email_container)
    public void openEmailPage(View view) {
        String targetEmail = userProfile.getEmail();
        String uriText = "mailto:" + targetEmail + "?subject=" + getString(R.string.lets_carpool);
        Uri mailUri = Uri.parse(uriText);
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, mailUri);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(Intent.createChooser(sendIntent, getString(R.string.send_email)));
    }

    @OnClick(R.id.phone_number_container)
    public void openTextMessagePage(View view) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.setData(Uri.parse("sms:" + userProfile.getPhoneNumber()));
        startActivity(smsIntent);
    }
}
