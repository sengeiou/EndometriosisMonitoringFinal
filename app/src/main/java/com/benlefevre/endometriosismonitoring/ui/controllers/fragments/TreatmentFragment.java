package com.benlefevre.endometriosismonitoring.ui.controllers.fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.WorkManager;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.models.Treatment;
import com.benlefevre.endometriosismonitoring.utils.NotificationWorker;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.benlefevre.endometriosismonitoring.utils.Constants.AFTERNOON;
import static com.benlefevre.endometriosismonitoring.utils.Constants.CONDI;
import static com.benlefevre.endometriosismonitoring.utils.Constants.DOSAGE;
import static com.benlefevre.endometriosismonitoring.utils.Constants.EVENING;
import static com.benlefevre.endometriosismonitoring.utils.Constants.LAST_CYCLE_DAY;
import static com.benlefevre.endometriosismonitoring.utils.Constants.MORNING;
import static com.benlefevre.endometriosismonitoring.utils.Constants.NOON;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PILL;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PILL_CHECK;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PILL_HOUR;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PREFERENCES;
import static com.benlefevre.endometriosismonitoring.utils.Constants.TREATMENT;
import static com.benlefevre.endometriosismonitoring.utils.Constants.TREATMENT_LIST;
import static com.benlefevre.endometriosismonitoring.utils.Constants.TREATMENT_NAME;


public class TreatmentFragment extends Fragment {


    @BindView(R.id.check01)
    AppCompatCheckBox mCheck01;
    @BindView(R.id.check02)
    AppCompatCheckBox mCheck02;
    @BindView(R.id.check03)
    AppCompatCheckBox mCheck03;
    @BindView(R.id.check04)
    AppCompatCheckBox mCheck04;
    @BindView(R.id.check05)
    AppCompatCheckBox mCheck05;
    @BindView(R.id.check06)
    AppCompatCheckBox mCheck06;
    @BindView(R.id.check07)
    AppCompatCheckBox mCheck07;
    @BindView(R.id.check08)
    AppCompatCheckBox mCheck08;
    @BindView(R.id.check09)
    AppCompatCheckBox mCheck09;
    @BindView(R.id.check010)
    AppCompatCheckBox mCheck010;
    @BindView(R.id.check011)
    AppCompatCheckBox mCheck011;
    @BindView(R.id.check012)
    AppCompatCheckBox mCheck012;
    @BindView(R.id.check013)
    AppCompatCheckBox mCheck013;
    @BindView(R.id.check014)
    AppCompatCheckBox mCheck014;
    @BindView(R.id.check015)
    AppCompatCheckBox mCheck015;
    @BindView(R.id.check016)
    AppCompatCheckBox mCheck016;
    @BindView(R.id.check017)
    AppCompatCheckBox mCheck017;
    @BindView(R.id.check018)
    AppCompatCheckBox mCheck018;
    @BindView(R.id.check019)
    AppCompatCheckBox mCheck019;
    @BindView(R.id.check020)
    AppCompatCheckBox mCheck020;
    @BindView(R.id.check021)
    AppCompatCheckBox mCheck021;
    @BindView(R.id.check022)
    AppCompatCheckBox mCheck022;
    @BindView(R.id.check023)
    AppCompatCheckBox mCheck023;
    @BindView(R.id.check024)
    AppCompatCheckBox mCheck024;
    @BindView(R.id.check025)
    AppCompatCheckBox mCheck025;
    @BindView(R.id.check026)
    AppCompatCheckBox mCheck026;
    @BindView(R.id.check027)
    AppCompatCheckBox mCheck027;
    @BindView(R.id.check028)
    AppCompatCheckBox mCheck028;
    @BindView(R.id.first_day_txt)
    TextInputEditText mFirstDayTxt;
    @BindView(R.id.treatment_first_day_legend)
    TextInputLayout mFirstDayLegend;
    @BindView(R.id.treatment_hour_pills_txt)
    TextInputEditText mHourPillsTxt;
    @BindView(R.id.treatment_hour_pills_label)
    TextInputLayout mHourPillsLabel;
    @BindView(R.id.treatment_add_btn)
    ImageButton mAddBtn;
    @BindView(R.id.treatment_recycler_view)
    RecyclerView mRecyclerView;

    private Activity mActivity;
    private Gson mGson;
    private List<AppCompatCheckBox> mCheckBoxList;
    private SharedPreferences mSharedPreferences;
    private Calendar mCalendar;
    private SimpleDateFormat mDateFormat;
    private SimpleDateFormat mTimeFormat;
    private String mMorning;
    private String mNoon;
    private String mAfternoon;
    private String mEvening;
    private List<Treatment> mTreatmentList;

//    Views of custom dialog
    private View mCustomDialog;
    private TextInputEditText mTreatmentNameTxt;
    private TextInputEditText mTreamentDosageTxt;
    private MaterialAutoCompleteTextView mTreatmentCondiTxt;
    private MaterialAutoCompleteTextView mTreatmentDuration;
    private TextInputLayout mNameLegend;
    private TextInputLayout mDosageLegend;
    private TextInputLayout mCondiLegend;
    private MaterialTextView mPositiveButton;
    private MaterialTextView mNegativeButton;
    private Chip mMorningChip;
    private Chip mNoonChip;
    private Chip mAfternoonChip;
    private Chip mEveningChip;

    public TreatmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_treatment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mActivity = getActivity();
        mGson = new Gson();
        mSharedPreferences = mActivity.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        mCheckBoxList = new ArrayList<>();
        mCalendar = Calendar.getInstance();
        mDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        mTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        mTreatmentList = new ArrayList<>();
        initCheckBoxList();
        checkCheckbox();
        updateDayAndHourPill();
        getTreatment();
    }


    /**
     * Adds checkbox in a list to simplify the check of their state later
     */
    private void initCheckBoxList() {
        mCheckBoxList.add(mCheck01);
        mCheckBoxList.add(mCheck02);
        mCheckBoxList.add(mCheck03);
        mCheckBoxList.add(mCheck04);
        mCheckBoxList.add(mCheck05);
        mCheckBoxList.add(mCheck06);
        mCheckBoxList.add(mCheck07);
        mCheckBoxList.add(mCheck08);
        mCheckBoxList.add(mCheck09);
        mCheckBoxList.add(mCheck010);
        mCheckBoxList.add(mCheck011);
        mCheckBoxList.add(mCheck012);
        mCheckBoxList.add(mCheck013);
        mCheckBoxList.add(mCheck014);
        mCheckBoxList.add(mCheck015);
        mCheckBoxList.add(mCheck016);
        mCheckBoxList.add(mCheck017);
        mCheckBoxList.add(mCheck018);
        mCheckBoxList.add(mCheck019);
        mCheckBoxList.add(mCheck020);
        mCheckBoxList.add(mCheck021);
        mCheckBoxList.add(mCheck022);
        mCheckBoxList.add(mCheck023);
        mCheckBoxList.add(mCheck024);
        mCheckBoxList.add(mCheck025);
        mCheckBoxList.add(mCheck026);
        mCheckBoxList.add(mCheck027);
        mCheckBoxList.add(mCheck028);
    }

    /**
     * Fetches a Json to SharedPreferences and set checked the mCheckboxList checkbox
     */
    private void checkCheckbox() {
        Type collectionType = new TypeToken<List<Integer>>(){}.getType();
        List<Integer> checkBoxIndex = mGson.fromJson(mSharedPreferences.getString(PILL_CHECK,null),collectionType);
        if (checkBoxIndex != null && ! checkBoxIndex.isEmpty()){
            for(int i : checkBoxIndex)
                mCheckBoxList.get(i).setChecked(true);
        }
    }

    /**
     * Fetches all user's treatment in SharedPreferences
     */
    private void getTreatment(){
        Type collectionType = new TypeToken<List<Treatment>>(){}.getType();
        List<Treatment> treatments = mGson.fromJson(mSharedPreferences.getString(TREATMENT_LIST, null),collectionType);
        if (treatments != null && !treatments.isEmpty())
            mTreatmentList.addAll(treatments);
    }
    /**
     * Bind user input saved in SharedPreferences into fields
     */
    private void updateDayAndHourPill(){
        String firstDay = mSharedPreferences.getString(LAST_CYCLE_DAY,null);
        String hourPill = mSharedPreferences.getString(PILL_HOUR,null);
        if (firstDay != null)
            mFirstDayTxt.setText(firstDay);
        if (hourPill != null)
            mHourPillsTxt.setText(hourPill);
    }

    /**
     * Sets a OnDateSetListener with updateAndSaveDateLabel() and shows a DatePickerDialog
     */
    private void openDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR,year);
            mCalendar.set(Calendar.MONTH,month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateAndSaveDateLabel();
        };
        new DatePickerDialog(mActivity,dateSetListener,mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Fetches the chosen date by user, updates the corresponding field and saves the value in Shared
     * Preferences.
     */
    private void updateAndSaveDateLabel(){
        mFirstDayTxt.setText(mDateFormat.format(mCalendar.getTime()));
        if (mFirstDayTxt.getText() != null)
            mSharedPreferences.edit().putString(LAST_CYCLE_DAY,mFirstDayTxt.getText().toString()).apply();
    }

    /**
     * Sets a OnTimeSetListener with updateAndSavePillTimeLabel() and shows a TimePickerDialog
     */
    private void openPillTimePicker(){
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minute);
            updateAndSavePillTimeLabel();
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity,timeSetListener,
                mCalendar.get(Calendar.HOUR_OF_DAY),mCalendar.get(Calendar.MINUTE),true);
        timePickerDialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.delete_hour), (dialog, which) -> {
            mHourPillsTxt.setText("");
            mSharedPreferences.edit().remove(PILL_HOUR).apply();
            WorkManager.getInstance(mActivity).cancelAllWorkByTag(PILL);
        });
        timePickerDialog.show();
    }

    /**
     * Fetches the chosen time by user, updates the corresponding field and saves the value in Shared
     * Preferences.
     * Calls NotificationWorker.configurePillNotification to send notification according to the user's
     * time choice.
     */
    private void updateAndSavePillTimeLabel(){
        mHourPillsTxt.setText(mTimeFormat.format(mCalendar.getTime()));
        if (mHourPillsTxt.getText() != null){
            String hour = mHourPillsTxt.getText().toString();
            mSharedPreferences.edit().putString(PILL_HOUR,hour).apply();
            Data data = new Data.Builder()
                    .putString(TREATMENT,PILL)
                    .build();
            NotificationWorker.configurePillNotification(mActivity,data,hour);
        }
    }

    /**
     * Sets a OnTimeSetListener with setupTreatmentHourString() ans shows a TimePicker Dialog
     * @param hour a String constant used to know which chip is selected
     */
    private void openTreatmentTimePicker(String hour){
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minute);
            setupTreatmentHourString(hour);
        };
        new TimePickerDialog(mActivity, timeSetListener, mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE), true).show();
    }

    /**
     * Sets the value of each time string according to selected hour chip by user
     * @param hour a String Constant to identify which chip is selected
     */
    private void setupTreatmentHourString(String hour){
        switch (hour){
            case MORNING:
                mMorning = mTimeFormat.format(mCalendar.getTime());
                break;
            case NOON:
                mNoon = mTimeFormat.format(mCalendar.getTime());
                break;
            case AFTERNOON:
                mAfternoon = mTimeFormat.format(mCalendar.getTime());
                break;
            case EVENING:
                mEvening = mTimeFormat.format(mCalendar.getTime());
                break;
        }
    }

    private void openTreatmentDialog(){
        initHourString();
        setupTreatmentDialogFields();
        AlertDialog dialog = new MaterialAlertDialogBuilder(mActivity)
                .setView(mCustomDialog)
                .setCancelable(false)
                .show();

        mPositiveButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mTreatmentNameTxt.getText()) && !TextUtils.isEmpty(mTreamentDosageTxt.getText()) && !TextUtils.isEmpty(mTreatmentCondiTxt.getText())
                    && !TextUtils.isEmpty(mTreatmentDuration.getText())) {
                Data.Builder data = new Data.Builder();
                String treatmentName = mTreatmentNameTxt.getText().toString();
                data.putString(TREATMENT_NAME, treatmentName);
                String treatmentDosage = mTreamentDosageTxt.getText().toString();
                data.putString(DOSAGE, treatmentDosage);
                String treatmentCondi = mTreatmentCondiTxt.getText().toString();
                data.putString(CONDI, treatmentCondi);
                if (mMorningChip.isChecked() && !mMorning.equals(getString(R.string.morning)))
                    NotificationWorker.configureTreatmentNotification(mActivity,data.build(),mMorning,treatmentName);
                if (mNoonChip.isChecked() && !mNoon.equals(getString(R.string.noon)))
                    NotificationWorker.configureTreatmentNotification(mActivity,data.build(),mNoon,treatmentName);
                if (mAfternoonChip.isChecked() && !mAfternoon.equals(getString(R.string.afternoon)))
                    NotificationWorker.configureTreatmentNotification(mActivity,data.build(),mAfternoon,treatmentName);
                if (mEveningChip.isChecked() && !mEvening.equals(getString(R.string.evening)))
                    NotificationWorker.configureTreatmentNotification(mActivity,data.build(),mEvening,treatmentName);

                String duration = computeDuration(mTreatmentDuration.getText().toString());

                Treatment treatment = new Treatment(treatmentName,duration,treatmentDosage, treatmentCondi,
                        mMorning, mNoon, mAfternoon, mEvening);
                mTreatmentList.add(treatment);
                dialog.cancel();
            }else{
                if (TextUtils.isEmpty(mTreatmentNameTxt.getText()))
                    mNameLegend.setError(getString(R.string.enter_medicament_name));
                if (TextUtils.isEmpty(mTreamentDosageTxt.getText()))
                    mDosageLegend.setError(getString(R.string.enter_medicament_dosage));
                if (TextUtils.isEmpty(mTreatmentCondiTxt.getText()))
                    mCondiLegend.setError(getString(R.string.enter_medicament_conditioning));
            }
        });

        mNegativeButton.setOnClickListener(v -> dialog.cancel());
    }

    /**
     * Computes the treatment duration from the user input and the current date
     * @param duration the user input treatment duration
     * @return a string with the end date of treatment
     */
    private String computeDuration(String duration){
        if (!duration.equals(getString(R.string.permanent))){
            mCalendar = Calendar.getInstance();
            mCalendar.add(Calendar.DAY_OF_YEAR , Integer.parseInt(duration.substring(0,2).trim()));
            duration = mDateFormat.format(mCalendar.getTime());
        }
        return duration;
    }

    /**
     * Sets all needed fields behavior to the opened AlertDialog
     */
    private void setupTreatmentDialogFields(){
        mCustomDialog = LayoutInflater.from(mActivity).inflate(R.layout.dialog_treatment,null,false);
        mTreatmentNameTxt = mCustomDialog.findViewById(R.id.dialog_treatment_name_txt);
        mTreamentDosageTxt = mCustomDialog.findViewById(R.id.dialog_treatment_dosage_txt);
        mTreatmentCondiTxt = mCustomDialog.findViewById(R.id.dialog_treatment_condi_txt);
        mTreatmentDuration = mCustomDialog.findViewById(R.id.dialog_treatment_duration_txt);
        mNameLegend = mCustomDialog.findViewById(R.id.dialog_treatment_name_legend);
        mDosageLegend = mCustomDialog.findViewById(R.id.dialog_treatment_dosage_legend);
        mCondiLegend = mCustomDialog.findViewById(R.id.dialog_treatment_condi_legend);
        mPositiveButton = mCustomDialog.findViewById(R.id.dialog_treatment_pos_btn);
        mNegativeButton = mCustomDialog.findViewById(R.id.dialog_treatment_neg_btn);

        ArrayAdapter<CharSequence> condiAdapter = new ArrayAdapter<>(mActivity, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.conditioning));
        mTreatmentCondiTxt.setAdapter(condiAdapter);

        ArrayAdapter<CharSequence> durationAdapter = new ArrayAdapter<>(mActivity, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.duration));
        mTreatmentDuration.setAdapter(durationAdapter);

        mMorningChip = mCustomDialog.findViewById(R.id.dialog_treatment_morning_chip);
        mMorningChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                openTreatmentTimePicker(MORNING);
        });
        mNoonChip = mCustomDialog.findViewById(R.id.dialog_treatment_noon_chip);
        mNoonChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                openTreatmentTimePicker(NOON);
        });
        mAfternoonChip = mCustomDialog.findViewById(R.id.dialog_treatment_afternoon_chip);
        mAfternoonChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                openTreatmentTimePicker(AFTERNOON);
        });
        mEveningChip = mCustomDialog.findViewById(R.id.dialog_treatment_evening_chip);
        mEveningChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                openTreatmentTimePicker(EVENING);
        });

    }

    /**
     * Initializes string with a default value
     */
    private void initHourString(){
        mMorning = getString(R.string.morning);
        mNoon = getString(R.string.noon);
        mAfternoon = getString(R.string.afternoon);
        mEvening = getString(R.string.evening);
    }

    @OnClick({R.id.first_day_txt, R.id.treatment_hour_pills_txt, R.id.treatment_add_btn})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.first_day_txt:
                openDatePicker();
                break;
            case R.id.treatment_hour_pills_txt:
                openPillTimePicker();
                break;
            case R.id.treatment_add_btn:
                openTreatmentDialog();
                break;
        }
    }

    /**
     * Verifies if checkbox are checked and put their mCheckboxList index in a List<Integer>
     * @return a List that contains checkbox index
     */
    private List<Integer> isCheckboxChecked(){
        List<Integer> checkBoxList = new ArrayList<>();
        int i = 0;
        for (AppCompatCheckBox checkBox : mCheckBoxList) {
            if (checkBox.isChecked()) {
                checkBoxList.add(i);
            }
            i++;
        }
        return checkBoxList;
    }

    @Override
    public void onPause() {
        super.onPause();
//        Calls isCheckboxChecked and put the result in SharedPreferences
        mSharedPreferences.edit().putString(PILL_CHECK,mGson.toJson(isCheckboxChecked())).apply();
//        Save in SharedPreferences all user treatment
        mSharedPreferences.edit().putString(TREATMENT_LIST,mGson.toJson(mTreatmentList)).apply();
    }
}
