package com.randomappsinc.blanknavigationdrawer.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.blanknavigationdrawer.Activities.Onboarding.SplashScreenActivity;
import com.randomappsinc.blanknavigationdrawer.Adapters.FontAwesomeAdapter;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;
import com.randomappsinc.blanknavigationdrawer.Utils.PreferencesManager;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by alexanderchiou on 12/20/15.
 */
public class SettingsActivity extends StandardActivity {
    public static final String SUPPORT_EMAIL = "support@smartlifedigital.com";
    public static final String SUPPORT_WEBSITE = "http://www.smartlifedigital.com/carpool_buddy";

    @Bind(R.id.parent) View parent;
    @Bind(R.id.settings_options) ListView settingsOptions;
    @BindString(R.string.play_store_error) String playStoreError;
    @BindString(R.string.feedback_subject) String feedbackSubject;
    @BindString(R.string.send_email) String sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settingsOptions.setAdapter(new FontAwesomeAdapter(this,
                getResources().getStringArray(R.array.settings_options),
                getResources().getStringArray(R.array.settings_icons)));
    }

    @OnItemClick(R.id.settings_options)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent;
        switch (position) {
            case 0:
                String uriText = "mailto:" + SUPPORT_EMAIL + "?subject=" + Uri.encode(feedbackSubject);
                Uri mailUri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, mailUri);
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(Intent.createChooser(sendIntent, sendEmail));
                return;
            case 1:
                Uri uri =  Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                intent = new Intent(Intent.ACTION_VIEW, uri);
                if (!(getPackageManager().queryIntentActivities(intent, 0).size() > 0)) {
                    FormUtils.showSnackbar(parent, playStoreError);
                    return;
                }
                startActivity(intent);
                break;
            case 2:
                Uri url = Uri.parse(SUPPORT_WEBSITE);
                intent = new Intent(Intent.ACTION_VIEW, url);
                startActivity(intent);
                break;
            case 3:
                new MaterialDialog.Builder(this)
                        .content(R.string.logout_confirmation)
                        .positiveText(android.R.string.yes)
                        .negativeText(android.R.string.no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                logout();
                            }
                        })
                        .show();
                break;
        }
    }

    public void logout() {
        PreferencesManager.get().logout();
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
