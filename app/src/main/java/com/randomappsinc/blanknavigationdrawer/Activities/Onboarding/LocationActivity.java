package com.randomappsinc.blanknavigationdrawer.Activities.Onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.blanknavigationdrawer.Activities.StandardActivity;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;

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
        String zipInput = workInput.getText().toString();
        if (villageInput.getText().toString().isEmpty()) {
            FormUtils.showSnackbar(parent, getString(R.string.no_village));
        }
        else if (zipInput.isEmpty() || zipInput.length() < 5) {
            FormUtils.showSnackbar(parent, getString(R.string.invalid_zip));
        }
        else {
            startActivity(new Intent(this, AboutMeActivity.class));
        }
    }
}
