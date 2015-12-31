package com.randomappsinc.blanknavigationdrawer.Activities.Onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.randomappsinc.blanknavigationdrawer.API.Models.User;
import com.randomappsinc.blanknavigationdrawer.Activities.StandardActivity;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.Constants;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexanderchiou on 12/23/15.
 */
public class AboutMeActivity extends StandardActivity {
    @Bind(R.id.parent) View parent;
    @Bind(R.id.name_input) EditText nameInput;
    @Bind(R.id.about_me_input) EditText aboutMeInput;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_me_form);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = getIntent().getParcelableExtra(Constants.USER_KEY);
    }

    @OnClick(R.id.next)
    public void nextPage(View view) {
        String enteredName = nameInput.getText().toString();
        String enteredAboutMe = aboutMeInput.getText().toString();
        if (enteredName.isEmpty()) {
            FormUtils.showSnackbar(parent, getString(R.string.no_name));
        }
        else if (enteredAboutMe.isEmpty()) {
            FormUtils.showSnackbar(parent, getString(R.string.no_about_me));
        }
        else {
            user.setName(enteredName);
            user.setAboutMe(enteredAboutMe);

            Intent intent = new Intent(this, ContactInfoActivity.class);
            intent.putExtra(Constants.USER_KEY, user);
            startActivity(intent);
        }
    }
}
