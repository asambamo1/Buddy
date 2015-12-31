package com.smartlifedigital.carpoolbuddy.Activities.Onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.smartlifedigital.carpoolbuddy.API.Models.User;
import com.smartlifedigital.carpoolbuddy.Activities.StandardActivity;
import com.smartlifedigital.carpoolbuddy.R;
import com.smartlifedigital.carpoolbuddy.Utils.Constants;
import com.smartlifedigital.carpoolbuddy.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationActivity extends StandardActivity {
    @Bind(R.id.parent) View parent;
    @Bind(R.id.village_input) EditText villageInput;
    @Bind(R.id.work_input) EditText workInput;

    private int currentVillageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_form);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.village_input)
    public void chooseVillage(View view) {
        new MaterialDialog.Builder(this)
                .title(R.string.villages)
                .items(R.array.villages)
                .itemsCallbackSingleChoice(currentVillageIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        currentVillageIndex = which;
                        villageInput.setText(text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    @OnClick(R.id.next)
    public void nextPage(View view) {
        String enteredVillage = villageInput.getText().toString();
        String zipInput = workInput.getText().toString();
        if (enteredVillage.isEmpty()) {
            FormUtils.showSnackbar(parent, getString(R.string.no_village));
        }
        else if (zipInput.isEmpty() || zipInput.length() < 5) {
            FormUtils.showSnackbar(parent, getString(R.string.invalid_zip));
        }
        else {
            User user = new User();
            user.setVillage(enteredVillage);
            user.setZipCode(Integer.parseInt(zipInput));

            Intent intent = new Intent(this, AboutMeActivity.class);
            intent.putExtra(Constants.USER_KEY, user);
            startActivity(intent);
        }
    }
}
