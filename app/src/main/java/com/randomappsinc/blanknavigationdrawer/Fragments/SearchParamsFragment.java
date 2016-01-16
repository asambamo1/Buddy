package com.randomappsinc.blanknavigationdrawer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.FormUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchParamsFragment extends Fragment {

    @Bind(R.id.parent) View parent;
    @Bind(R.id.gender_input) EditText genderInput;
    @Bind(R.id.home_input) EditText homeZipInput;
    @Bind(R.id.work_input) EditText workZipInput;
    private int currentGenderIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_params, parentViewGroup, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.gender_input)
    public void chooseGender(View view) {
        new MaterialDialog.Builder(getActivity())
                .items(R.array.genders)
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
}
