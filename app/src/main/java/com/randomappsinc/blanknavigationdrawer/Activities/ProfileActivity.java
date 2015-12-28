package com.randomappsinc.blanknavigationdrawer.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.blanknavigationdrawer.API.Callbacks.ProfileCallback;
import com.randomappsinc.blanknavigationdrawer.API.Callbacks.StatusChangeCallback;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.ProfileEvent;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.StatusChangeEvent;
import com.randomappsinc.blanknavigationdrawer.API.Models.Profile;
import com.randomappsinc.blanknavigationdrawer.API.Models.RequestBundle;
import com.randomappsinc.blanknavigationdrawer.API.RestClient;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.Constants;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;
import com.randomappsinc.blanknavigationdrawer.Utils.PreferencesManager;
import com.rey.material.widget.Button;

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
    @Bind(R.id.email_container) View emailContainer;
    @Bind(R.id.email) TextView email;
    @Bind(R.id.phone_number_container) View phoneNumberContainer;
    @Bind(R.id.phone_number) TextView phoneNumber;
    @Bind(R.id.buttons_container) View buttonsContainer;
    @Bind(R.id.accept_reject) View acceptReject;
    @Bind(R.id.full_button) Button fullButton;

    private long profileId;
    private Profile userProfile;
    private String currentStatus;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String myId = String.valueOf(PreferencesManager.get().getProfile().getUserId());
        profileId = getIntent().getLongExtra(Constants.USER_ID_KEY, 0);
        ProfileCallback callback = new ProfileCallback();
        RestClient.getInstance().getMatchingService().getProfile(myId, String.valueOf(profileId)).enqueue(callback);

        progressDialog = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .cancelable(false)
                .build();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(ProfileEvent response) {
        userProfile = response.getProfile();
        loading.setVisibility(View.GONE);
        if (response.getProfile() == null) {
            FormUtils.showSnackbar(parent, getString(R.string.profile_fetch_fail));
        }
        else {
            currentStatus = userProfile.getStatus();
            processStatus();
            profile.setVisibility(View.VISIBLE);
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

    public void processStatus() {
        switch (currentStatus) {
            case Constants.ACCEPTED:
                emailContainer.setVisibility(View.VISIBLE);
                phoneNumberContainer.setVisibility(View.VISIBLE);
                buttonsContainer.setVisibility(View.GONE);
                return;
            case Constants.PENDING:
                acceptReject.setVisibility(View.GONE);
                fullButton.setVisibility(View.VISIBLE);
                fullButton.setBackgroundResource(R.drawable.yellow_ripple_button);
                fullButton.setText(R.string.pending);
                break;
            case Constants.AWAITING_YOUR_APPROVAL:
                fullButton.setVisibility(View.GONE);
                break;
            case Constants.REJECTED:
            case Constants.REJECTED_BY_YOU:
                acceptReject.setVisibility(View.GONE);
                fullButton.setVisibility(View.VISIBLE);
                fullButton.setBackgroundResource(R.drawable.red_ripple_button);
                fullButton.setText(R.string.rejected);
                break;
            case Constants.UNCONNECTED:
                acceptReject.setVisibility(View.GONE);
                fullButton.setVisibility(View.VISIBLE);
                fullButton.setBackgroundResource(R.drawable.green_ripple_button);
                fullButton.setText(R.string.connect);
                break;
        }
        buttonsContainer.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.full_button)
    public void fullButtonAction(View view) {
        switch (currentStatus) {
            case Constants.UNCONNECTED:
                progressDialog.setContent(R.string.sending_request);
                progressDialog.show();
                RequestBundle bundle = new RequestBundle();
                bundle.setRequesterId(PreferencesManager.get().getProfile().getUserId());
                bundle.setTargetId(profileId);
                StatusChangeCallback callback = new StatusChangeCallback(Constants.PENDING);
                RestClient.getInstance().getMatchingService().sendRequest(bundle).enqueue(callback);
                break;
            case Constants.REJECTED_BY_YOU:
                break;
        }
    }

    @OnClick(R.id.accept)
    public void acceptRequest(View view) {
        progressDialog.setContent(R.string.accepting_request);
        progressDialog.show();
        RequestBundle bundle = new RequestBundle();
        bundle.setRequesterId(profileId);
        bundle.setTargetId(PreferencesManager.get().getProfile().getUserId());
        StatusChangeCallback callback = new StatusChangeCallback(Constants.ACCEPTED);
        RestClient.getInstance().getMatchingService().acceptRequest(bundle).enqueue(callback);
    }

    @OnClick(R.id.accept)
    public void rejectRequest(View view) {
        progressDialog.setContent(R.string.rejecting_request);
        progressDialog.show();
        RequestBundle bundle = new RequestBundle();
        bundle.setRequesterId(profileId);
        bundle.setTargetId(PreferencesManager.get().getProfile().getUserId());
        StatusChangeCallback callback = new StatusChangeCallback(Constants.REJECTED_BY_YOU);
        RestClient.getInstance().getMatchingService().rejectRequest(bundle).enqueue(callback);
    }

    public void onEvent(StatusChangeEvent event) {
        progressDialog.dismiss();
        if (event.getNewStatus() != null) {
            currentStatus = event.getNewStatus();
            processStatus();
        }
        else {
            FormUtils.showSnackbar(parent, getString(R.string.status_change_fail));
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
