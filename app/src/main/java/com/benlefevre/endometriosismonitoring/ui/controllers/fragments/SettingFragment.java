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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.WorkManager;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.injection.Injection;
import com.benlefevre.endometriosismonitoring.injection.ViewModelFactory;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.benlefevre.endometriosismonitoring.utils.Constants.DURATION_CYCLE;
import static com.benlefevre.endometriosismonitoring.utils.Constants.LAST_CYCLE_DAY;
import static com.benlefevre.endometriosismonitoring.utils.Constants.NEXT_CYCLE_DAY;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PILL_HOUR;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PREFERENCES;
import static com.benlefevre.endometriosismonitoring.utils.Constants.TREATMENT_LIST;


public class SettingFragment extends Fragment {


    @BindView(R.id.setting_reset_notification)
    CardView mSettingResetNotification;
    @BindView(R.id.setting_reset_pain)
    CardView mSettingResetPain;
    @BindView(R.id.setting_reset_sleep)
    CardView mSettingResetSleep;
    @BindView(R.id.setting_reset_Symptom)
    CardView mSettingResetSymptom;
    @BindView(R.id.setting_reset_action)
    CardView mSettingResetAction;
    @BindView(R.id.setting_reset_mood)
    CardView mSettingResetMood;
    @BindView(R.id.setting_reset_temp)
    CardView mSettingResetTemp;

    private Activity mActivity;
    private SharedPreferences mSharedPreferences;
    private SharedViewModel mViewModel;

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
        configureViewModel();
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(mActivity);
        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity, viewModelFactory).get(SharedViewModel.class);
    }

    @OnClick({R.id.setting_reset_notification, R.id.setting_reset_pain, R.id.setting_reset_sleep,
            R.id.setting_reset_Symptom, R.id.setting_reset_action, R.id.setting_reset_mood, R.id.setting_reset_temp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_reset_notification:
                openNotificationDialog();
                break;
            case R.id.setting_reset_pain:
                openPainDialog();
                break;
            case R.id.setting_reset_sleep:
            case R.id.setting_reset_Symptom:
            case R.id.setting_reset_action:
            case R.id.setting_reset_mood:
            case R.id.setting_reset_temp:
                openCustomDialog(view.getId());
                break;
        }
    }

    private void openCustomDialog(int viewId) {
        new MaterialAlertDialogBuilder(mActivity)
                .setCancelable(false)
                .setTitle(getString(R.string.clear_data))
                .setMessage(getString(R.string.sure))
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel())
                .setPositiveButton(getString(R.string.reset), (dialog, which) -> configureDialog(viewId))
                .show();
    }


    private void openPainDialog() {
        new MaterialAlertDialogBuilder(mActivity)
                .setCancelable(false)
                .setTitle(getString(R.string.clear_data))
                .setMessage(getString(R.string.sure_all))
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel())
                .setPositiveButton(getString(R.string.reset), (dialog, which) -> {
                    mViewModel.deleteAllPains();
                    mViewModel.deleteAllTemp();
                })
                .show();
    }

    private void openNotificationDialog() {
        new MaterialAlertDialogBuilder(mActivity)
                .setCancelable(false)
                .setTitle(getString(R.string.reset_notif))
                .setMessage(getString(R.string.sure))
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel())
                .setPositiveButton(getString(R.string.reset), (dialog, which) -> {
                    WorkManager.getInstance(mActivity).cancelAllWork();
                    mSharedPreferences.edit().remove(TREATMENT_LIST).apply();
                    mSharedPreferences.edit().remove(PILL_HOUR).apply();
                    mSharedPreferences.edit().remove(LAST_CYCLE_DAY).apply();
                    mSharedPreferences.edit().remove(NEXT_CYCLE_DAY).apply();
                    mSharedPreferences.edit().remove(DURATION_CYCLE).apply();
                })
                .show();
    }

    private void configureDialog(int viewId) {
        switch (viewId) {
            case R.id.setting_reset_action:
                mViewModel.deleteAllActions(getString(R.string.sleep));
                break;
            case R.id.setting_reset_sleep:
                mViewModel.deleteAllSleep(getString(R.string.sleep));
                break;
            case R.id.setting_reset_mood:
                mViewModel.deleteAllMood();
                break;
            case R.id.setting_reset_Symptom:
                mViewModel.deleteAllSymptom();
                break;
            case R.id.setting_reset_temp:
                mViewModel.deleteAllTemp();
                break;
        }
    }
}
