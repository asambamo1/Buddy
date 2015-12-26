package com.randomappsinc.blanknavigationdrawer.Activities.Onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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
import butterknife.OnTextChanged;

public class ContactInfoActivity extends StandardActivity {
    @Bind(R.id.parent) View parent;
    @Bind(R.id.email_input) EditText emailForm;
    @Bind(R.id.phone_input) EditText phoneForm;

    private String lastSeenPhoneNumber;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_info_form);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = getIntent().getParcelableExtra(Constants.USER_KEY);
        phoneForm.setText(FormUtils.getPhoneNumber());
    }

    @OnTextChanged(value = R.id.phone_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void phoneChanged(Editable s) {
        if (!s.toString().equals(lastSeenPhoneNumber)) {
            String formattedValue = FormUtils.formatUSNumber(s.toString());
            lastSeenPhoneNumber = formattedValue;
            phoneForm.setText(formattedValue);
            phoneForm.setSelection(formattedValue.length());
        }
    }

    @OnClick(R.id.next)
    public void createAccount(View view) {
        String emailInput = emailForm.getText().toString();
        String phoneInput = phoneForm.getText().toString();
        if (!emailInput.isEmpty() && !FormUtils.isValidEmailAddress(emailInput)) {
            FormUtils.showSnackbar(parent, getString(R.string.invalid_email));
        }
        else if (!phoneInput.isEmpty() && !FormUtils.isValidPhoneNumber(phoneInput)) {
            FormUtils.showSnackbar(parent, getString(R.string.invalid_phone));
        }
        else if (emailInput.isEmpty() && phoneInput.isEmpty()) {
            FormUtils.showSnackbar(parent, getString(R.string.no_contact_info));
        }
        else {
            user.setEmail(emailInput);
            user.setPhoneNumber(phoneInput);

            Intent intent = new Intent(this, PasswordActivity.class);
            intent.putExtra(Constants.USER_KEY, user);
            startActivity(intent);
        }
    }
}
