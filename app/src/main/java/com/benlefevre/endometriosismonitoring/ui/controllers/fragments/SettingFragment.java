package com.benlefevre.endometriosismonitoring.ui.controllers.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.WorkManager;

import com.benlefevre.endometriosismonitoring.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.benlefevre.endometriosismonitoring.utils.Constants.LAST_CYCLE_DAY;
import static com.benlefevre.endometriosismonitoring.utils.Constants.NEXT_CYCLE_DAY;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PILL_HOUR;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PREFERENCES;
import static com.benlefevre.endometriosismonitoring.utils.Constants.TREATMENT_LIST;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    @BindView(R.id.setting_reset_notification)
    MaterialTextView mSettingResetNotification;

    private Activity mActivity;
    private SharedPreferences mSharedPreferences;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mActivity = getActivity();
        if (mActivity != null)
            mSharedPreferences = mActivity.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    @OnClick(R.id.setting_reset_notification)
    public void onViewClicked() {
        openDialog();
    }

    private void openDialog() {
        new MaterialAlertDialogBuilder(mActivity)
                .setCancelable(false)
                .setTitle("Reset Notification")
                .setMessage("Are your sure?")
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel())
                .setPositiveButton("Reset", (dialog, which) -> {
                    WorkManager.getInstance(mActivity).cancelAllWork();
                    mSharedPreferences.edit().remove(TREATMENT_LIST).apply();
                    mSharedPreferences.edit().remove(PILL_HOUR).apply();
                    mSharedPreferences.edit().remove(LAST_CYCLE_DAY).apply();
                    mSharedPreferences.edit().remove(NEXT_CYCLE_DAY).apply();
                })
                .show();
    }
}
