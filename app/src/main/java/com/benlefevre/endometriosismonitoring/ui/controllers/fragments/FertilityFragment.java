package com.benlefevre.endometriosismonitoring.ui.controllers.fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.injection.Injection;
import com.benlefevre.endometriosismonitoring.injection.ViewModelFactory;
import com.benlefevre.endometriosismonitoring.models.Temperature;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.benlefevre.endometriosismonitoring.utils.Constants.DURATION_CYCLE;
import static com.benlefevre.endometriosismonitoring.utils.Constants.LAST_CYCLE_DAY;
import static com.benlefevre.endometriosismonitoring.utils.Constants.NEXT_CYCLE_DAY;
import static com.benlefevre.endometriosismonitoring.utils.Constants.PREFERENCES;

public class FertilityFragment extends Fragment {


    @BindView(R.id.box1)
    MaterialTextView mBox1;
    @BindView(R.id.box2)
    MaterialTextView mBox2;
    @BindView(R.id.box3)
    MaterialTextView mBox3;
    @BindView(R.id.box4)
    MaterialTextView mBox4;
    @BindView(R.id.box5)
    MaterialTextView mBox5;
    @BindView(R.id.box6)
    MaterialTextView mBox6;
    @BindView(R.id.box7)
    MaterialTextView mBox7;
    @BindView(R.id.box8)
    MaterialTextView mBox8;
    @BindView(R.id.box9)
    MaterialTextView mBox9;
    @BindView(R.id.box10)
    MaterialTextView mBox10;
    @BindView(R.id.box11)
    MaterialTextView mBox11;
    @BindView(R.id.box12)
    MaterialTextView mBox12;
    @BindView(R.id.box13)
    MaterialTextView mBox13;
    @BindView(R.id.box14)
    MaterialTextView mBox14;
    @BindView(R.id.box15)
    MaterialTextView mBox15;
    @BindView(R.id.box16)
    MaterialTextView mBox16;
    @BindView(R.id.box17)
    MaterialTextView mBox17;
    @BindView(R.id.box18)
    MaterialTextView mBox18;
    @BindView(R.id.box19)
    MaterialTextView mBox19;
    @BindView(R.id.box20)
    MaterialTextView mBox20;
    @BindView(R.id.box21)
    MaterialTextView mBox21;
    @BindView(R.id.box22)
    MaterialTextView mBox22;
    @BindView(R.id.box23)
    MaterialTextView mBox23;
    @BindView(R.id.box24)
    MaterialTextView mBox24;
    @BindView(R.id.box25)
    MaterialTextView mBox25;
    @BindView(R.id.box26)
    MaterialTextView mBox26;
    @BindView(R.id.box27)
    MaterialTextView mBox27;
    @BindView(R.id.box28)
    MaterialTextView mBox28;
    @BindView(R.id.box29)
    MaterialTextView mBox29;
    @BindView(R.id.box30)
    MaterialTextView mBox30;
    @BindView(R.id.box31)
    MaterialTextView mBox31;
    @BindView(R.id.box32)
    MaterialTextView mBox32;
    @BindView(R.id.box33)
    MaterialTextView mBox33;
    @BindView(R.id.box34)
    MaterialTextView mBox34;
    @BindView(R.id.box35)
    MaterialTextView mBox35;
    @BindView(R.id.box36)
    MaterialTextView mBox36;
    @BindView(R.id.box37)
    MaterialTextView mBox37;
    @BindView(R.id.box38)
    MaterialTextView mBox38;
    @BindView(R.id.box39)
    MaterialTextView mBox39;
    @BindView(R.id.box40)
    MaterialTextView mBox40;
    @BindView(R.id.box41)
    MaterialTextView mBox41;
    @BindView(R.id.box42)
    MaterialTextView mBox42;
    @BindView(R.id.chip5)
    Chip mChip5;
    @BindView(R.id.chip4)
    Chip mChip4;
    @BindView(R.id.fertility_first_day_txt)
    TextInputEditText mFirstDayTxt;
    @BindView(R.id.fertility_first_day_legend)
    TextInputLayout mFirstDayLegend;
    @BindView(R.id.fertility_duration_txt)
    TextInputEditText mDurationTxt;
    @BindView(R.id.fertility_duration_legend)
    TextInputLayout mDurationLegend;
    @BindView(R.id.fertility_update_calendar_btn)
    MaterialButton mUpdateCalendarBtn;
    @BindView(R.id.slider)
    Slider mTempSlider;
    @BindView(R.id.temp_txt)
    MaterialTextView mTempTxt;
    @BindView(R.id.fertility_save_btn)
    MaterialButton mFertilitySaveBtn;
    @BindView(R.id.baby_temp_chart)
    LineChart mTempChart;

    private View mView;
    private Activity mActivity;
    private SharedViewModel mViewModel;
    private SharedPreferences mSharedPreferences;
    private List<Integer> mTextViewList;
    private Calendar mCalendar;
    private SimpleDateFormat mDateFormat;
    private int mMonth;
    private int mNbDaysInMonth;
    private int mFirstBox;
    private int mLastDayCycle;
    private int mFirstDayCycle;
    private double mTempValue;

    public FertilityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_fertility, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mActivity = getActivity();
        if (mActivity != null)
            mSharedPreferences = mActivity.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        mDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        configureViewModel();
        initTextViewList();
        initCalendarDate();
        setDurationListener();
        verifyIfNextMonth();
        initTemperatureCard();
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(mActivity);
        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity, viewModelFactory).get(SharedViewModel.class);
    }

    /**
     * Adds all TextView id in a List to handle them more easily
     */
    private void initTextViewList() {
        mTextViewList = new ArrayList<>();
        mTextViewList.add(R.id.box1);
        mTextViewList.add(R.id.box2);
        mTextViewList.add(R.id.box3);
        mTextViewList.add(R.id.box4);
        mTextViewList.add(R.id.box5);
        mTextViewList.add(R.id.box6);
        mTextViewList.add(R.id.box7);
        mTextViewList.add(R.id.box8);
        mTextViewList.add(R.id.box9);
        mTextViewList.add(R.id.box10);
        mTextViewList.add(R.id.box11);
        mTextViewList.add(R.id.box12);
        mTextViewList.add(R.id.box13);
        mTextViewList.add(R.id.box14);
        mTextViewList.add(R.id.box15);
        mTextViewList.add(R.id.box16);
        mTextViewList.add(R.id.box17);
        mTextViewList.add(R.id.box18);
        mTextViewList.add(R.id.box19);
        mTextViewList.add(R.id.box20);
        mTextViewList.add(R.id.box21);
        mTextViewList.add(R.id.box22);
        mTextViewList.add(R.id.box23);
        mTextViewList.add(R.id.box24);
        mTextViewList.add(R.id.box25);
        mTextViewList.add(R.id.box26);
        mTextViewList.add(R.id.box27);
        mTextViewList.add(R.id.box28);
        mTextViewList.add(R.id.box29);
        mTextViewList.add(R.id.box30);
        mTextViewList.add(R.id.box31);
        mTextViewList.add(R.id.box32);
        mTextViewList.add(R.id.box33);
        mTextViewList.add(R.id.box34);
        mTextViewList.add(R.id.box35);
        mTextViewList.add(R.id.box36);
        mTextViewList.add(R.id.box37);
        mTextViewList.add(R.id.box38);
    }


    /**
     * Initializes the custom calendar with the previous, current and next month days
     */
    private void initCalendarDate() {
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mMonth = mCalendar.get(Calendar.MONTH);
        setupNbDaysInMonth();
        setupFirstBoxValue();
        initDays(mFirstBox, mNbDaysInMonth);
        initFollowingMonthDays(mNbDaysInMonth);
        initPreviousMonthDays(mNbDaysInMonth, mMonth);
    }

    /**
     * Sets a value to mNbDaysInMonth according to the current month
     */
    private void setupNbDaysInMonth() {
        if (mMonth == 0 || mMonth == 2 || mMonth == 4 || mMonth == 6 || mMonth == 7 || mMonth == 9 || mMonth == 11)
            mNbDaysInMonth = 31;
        else if (mMonth == 3 || mMonth == 5 || mMonth == 8 || mMonth == 10)
            mNbDaysInMonth = 30;
        else
            mNbDaysInMonth = 28;
    }

    /**
     * Sets a value to mFirstBox according to the month begin on a given day
     */
    private void setupFirstBoxValue() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E", Locale.US);
        String firstDay = dateFormat.format(mCalendar.getTime());
        if (firstDay.equals("Mon"))
            mFirstBox = 0;
        if (firstDay.equals("Tue"))
            mFirstBox = 1;
        if (firstDay.equals("Wed"))
            mFirstBox = 2;
        if (firstDay.equals("Thu"))
            mFirstBox = 3;
        if (firstDay.equals("Fri"))
            mFirstBox = 4;
        if (firstDay.equals("Sat"))
            mFirstBox = 5;
        if (firstDay.equals("Sun"))
            mFirstBox = 6;
    }

    /**
     * Setup the calendar with the right number of days and each day is in the right box
     *
     * @param firstBox the start box to bind value
     * @param nbDays   the number of days for the current month
     */
    private void initDays(int firstBox, int nbDays) {
        for (int y = 1; y <= nbDays; y++) {
            MaterialTextView materialTextView = mView.findViewById(mTextViewList.get(firstBox));
            materialTextView.setText(String.valueOf(y));
            firstBox++;
        }
    }

    /**
     * Setup the calendar with the last box that corresponding to the next month
     *
     * @param nbDays the number of days for the current month
     */
    private void initFollowingMonthDays(int nbDays) {
        int date = 1;
        for (int i = mFirstBox + nbDays; i < mTextViewList.size(); i++) {
            MaterialTextView materialTextView = mView.findViewById(mTextViewList.get(i));
            materialTextView.setText(String.valueOf(date));
            materialTextView.setTextColor(getResources().getColor(R.color.colorBackground));
            date++;
        }
    }

    /**
     * Setup the empty first box that corresponding to the previous month
     *
     * @param nbDays the number of days for the current month
     * @param month  the value of the current month to know how many days there is in the previous month
     */
    private void initPreviousMonthDays(int nbDays, int month) {
        for (int i = mFirstBox - 1; i >= 0; i--) {
            MaterialTextView materialTextView = mView.findViewById(mTextViewList.get(i));
            if (month == 10 || month == 8 || month == 5 || month == 3) {
                materialTextView.setText(String.valueOf(nbDays + 1));
                materialTextView.setTextColor(getResources().getColor(R.color.colorBackground));
                nbDays--;
            } else if (month == 2) {
                materialTextView.setText(String.valueOf(nbDays - 3));
                materialTextView.setTextColor(getResources().getColor(R.color.colorBackground));
                nbDays--;
            } else if (month == 7 || month == 0) {
                materialTextView.setText(String.valueOf(nbDays));
                materialTextView.setTextColor(getResources().getColor(R.color.colorBackground));
                nbDays--;
            } else {
                materialTextView.setText(String.valueOf(nbDays - 1));
                materialTextView.setTextColor(getResources().getColor(R.color.colorBackground));
                nbDays--;
            }
        }
    }

    /**
     * Sets an OnTextChangedListener on mDurationTxt to update error label or save the input into
     * SharedPreferences
     */
    private void setDurationListener() {
        mDurationTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    mLastDayCycle = Integer.parseInt(s.toString());
                    if (mLastDayCycle >= 15 && mLastDayCycle <= 48) {
                        if (mDurationTxt.getText() != null) {
                            mDurationLegend.setError("");
                            mSharedPreferences.edit().putString(DURATION_CYCLE, mDurationTxt.getText().toString()).apply();
                            computeAndSaveNextCycle();
                        }
                    } else {
                        mDurationLegend.setError(getString(R.string.cycle_between));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Computes and saves in SharedPreferences the next first day cycle according to the user input
     */
    private void computeAndSaveNextCycle() {
        if (!TextUtils.isEmpty(mFirstDayTxt.getText())) {
            Date date = new Date();
            try {
                date = (mDateFormat.parse(mFirstDayTxt.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date != null)
                mCalendar.setTime(date);
//            Add the duration of the cycle
            mCalendar.add(Calendar.DAY_OF_YEAR, mLastDayCycle);
            mSharedPreferences.edit().putString(NEXT_CYCLE_DAY, mDateFormat.format(mCalendar.getTime())).apply();
        }
    }

    /**
     * Verifies if the current month is a new month and so update cycle values
     */
    private void verifyIfNextMonth() {
        String currentCycle = mSharedPreferences.getString(LAST_CYCLE_DAY, null);
        String nextCycle = mSharedPreferences.getString(NEXT_CYCLE_DAY, null);
        if (mSharedPreferences.getString(DURATION_CYCLE, null) != null)
            mLastDayCycle = Integer.parseInt(Objects.requireNonNull(mSharedPreferences.getString(DURATION_CYCLE, null)));

        Date currentCycleDate = new Date();
        Date nextCycleDate = new Date();

        int currentCycleMonth = -1;
        int nextCycleMonth = -1;

        if (currentCycle != null) {
            try {
                currentCycleDate = mDateFormat.parse(currentCycle);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (currentCycleDate != null)
                mCalendar.setTime(currentCycleDate);
            currentCycleMonth = mCalendar.get(Calendar.MONTH);
        }
        if (nextCycle != null) {
            try {
                nextCycleDate = mDateFormat.parse(nextCycle);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (nextCycleDate != null)
                mCalendar.setTime(nextCycleDate);
            nextCycleMonth = mCalendar.get(Calendar.MONTH);
        }

        if (nextCycle != null && mLastDayCycle != 0) {
            if (mMonth != 0) {
                if (mMonth > currentCycleMonth) {
                    if (nextCycleMonth == currentCycleMonth) {
                        mCalendar.setTime(nextCycleDate);
                        mCalendar.add(Calendar.DAY_OF_WEEK, mLastDayCycle);
                        currentCycle = mDateFormat.format(mCalendar.getTime());
                        mSharedPreferences.edit().putString(LAST_CYCLE_DAY, currentCycle).apply();
                    } else {
                        mSharedPreferences.edit().putString(LAST_CYCLE_DAY, nextCycle).apply();
                    }
                }
            } else {
                if (mMonth < currentCycleMonth) {
                    if (nextCycleMonth == currentCycleMonth) {
                        mCalendar.setTime(nextCycleDate);
                        mCalendar.add(Calendar.DAY_OF_WEEK, mLastDayCycle);
                        currentCycle = mDateFormat.format(mCalendar.getTime());
                        mSharedPreferences.edit().putString(LAST_CYCLE_DAY, currentCycle).apply();
                    } else {
                        mSharedPreferences.edit().putString(LAST_CYCLE_DAY, nextCycle).apply();
                    }
                }
            }
        }
        updateMenstruationFields();
    }

    /**
     * Fetches and sets the saved user input into fields
     */
    private void updateMenstruationFields() {
        String firstDay = mSharedPreferences.getString(LAST_CYCLE_DAY, null);
        String duration = mSharedPreferences.getString(DURATION_CYCLE, null);

        if (!TextUtils.isEmpty(firstDay)) {
            mFirstDayTxt.setText(firstDay);
            mFirstDayLegend.setError("");
            mFirstDayCycle = Integer.parseInt(firstDay.substring(0, 2));
        }
        if (!TextUtils.isEmpty(firstDay) && TextUtils.isEmpty(duration)) {
            mDurationLegend.setError(getString(R.string.enter_duration_cycle));
        }
        if (!TextUtils.isEmpty(duration)) {
            mDurationTxt.setText(duration);
            mDurationLegend.setError("");
            mLastDayCycle = Integer.parseInt(duration);
        }
        if (!TextUtils.isEmpty(duration) && TextUtils.isEmpty(firstDay)) {
            mFirstDayLegend.setError(getString(R.string.entre_previous_cycle));
        }
        if (!TextUtils.isEmpty(duration) && !TextUtils.isEmpty(firstDay)) {
            drawCycleInCalendar();
        }
    }

    /**
     * Compute and draws all cycle periods in the custom calendar
     */
    private void drawCycleInCalendar() {
        resetCalendar();
//        -------------------------------Draw last cycle--------------------------------------------
        int lastMens = mFirstBox + (mFirstDayCycle - 1);
        for (int i = lastMens; i < lastMens + 5; i++) {
            drawMenstruation(i);
        }

//        ---------------------------Draw fertilization period--------------------------------------
        if (mLastDayCycle != 0) {
            int lastIndex = (lastMens + mLastDayCycle) - 14;
            int nextMens = lastMens + mLastDayCycle;

//            -------------------Draw previous fertilization period---------------------------------

//            Verifies if the cycle can be draw entirely in the calendar
            if (lastIndex < mTextViewList.size()) {
                for (int i = lastIndex - 4; i <= lastIndex; i++) {
                    drawFertilization(i);
                }
                drawOvulation(lastIndex);
            } else {
                for (int i = lastIndex - 4; i < mTextViewList.size(); i++) {
                    drawFertilization(i);
                }
            }
//            --------------------Draw next fertilization period------------------------------------
            lastIndex = lastMens - 17;
            if (lastIndex >= 0) {
                for (int i = lastIndex; i <= lastIndex + 4; i++) {
                    drawFertilization(i);
                }
                drawOvulation(lastIndex + 4);
            }

//            -------------------------Draw next cycle----------------------------------------------
            if (nextMens + 5 < mTextViewList.size()) {
                for (int i = nextMens; i < nextMens + 5; i++) {
                    drawMenstruation(i);
                }
            } else {
                for (int i = nextMens; i < mTextViewList.size(); i++) {
                    drawMenstruation(i);
                }
            }
        }
    }

    /**
     * Draws the menstruation cycle in the calendar
     *
     * @param boxIndex the index of the box that needed to be drawn
     */
    private void drawMenstruation(int boxIndex) {
        MaterialTextView materialTextView = mView.findViewById(mTextViewList.get(boxIndex));
        materialTextView.setBackgroundColor(getResources().getColor(R.color.graph4));
        materialTextView.setTextColor(getResources().getColor(R.color.colorOnPrimary));
    }

    /**
     * Draws the ovulation day in the custom calendar
     *
     * @param lastIndex the index of the box that needed to be drawn
     */
    private void drawOvulation(int lastIndex) {
        MaterialTextView materialTextView = mView.findViewById(mTextViewList.get(lastIndex - 1));
        materialTextView.setBackgroundColor(getResources().getColor(R.color.graph2));
    }

    /**
     * Draws the fertilization period in the custom calendar
     *
     * @param boxIndex the index of the box that needed to be drawn
     */
    private void drawFertilization(int boxIndex) {
        MaterialTextView materialTextView = mView.findViewById(mTextViewList.get(boxIndex));
        materialTextView.setBackgroundColor(getResources().getColor(R.color.graph1));
        materialTextView.setTextColor(getResources().getColor(R.color.colorOnPrimary));
    }

    /**
     * Reset the calendar to the initial state without cycle
     */
    private void resetCalendar() {
        for (int i = 0; i < mTextViewList.size(); i++) {
            MaterialTextView materialTextView = mView.findViewById(mTextViewList.get(i));
            materialTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            materialTextView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
        initPreviousMonthDays(mNbDaysInMonth, mMonth);
        initFollowingMonthDays(mNbDaysInMonth);
    }

    /**
     * Sets an OnDateSetListener with updateDateLabel() and shows a DatePicker
     */
    private void openDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateLabel();
        };
        new DatePickerDialog(mActivity, dateSetListener, mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Updates the the TextView with the user's date choice and save it in SharedPreferences
     */
    private void updateDateLabel(){
        mFirstDayTxt.setText(mDateFormat.format(mCalendar.getTime()));
        if (!TextUtils.isEmpty(mFirstDayTxt.getText())){
            mFirstDayCycle = Integer.parseInt(mFirstDayTxt.getText().toString().substring(0,2));
            mSharedPreferences.edit().putString(LAST_CYCLE_DAY, mFirstDayTxt.getText().toString()).apply();
            verifyIfNextMonth();
        }

    }

    /**
     * Creates a temperature with user input and save it in locale DB with the ViewModel
     */
    private void createAndSaveTempInDb(){
        mCalendar = Calendar.getInstance();
        Temperature temperature = new Temperature(mTempValue, mCalendar.getTime());
        mViewModel.createTemp(temperature);
    }

    private void initTemperatureCard(){
        mViewModel.getAllTemperature().observe(getViewLifecycleOwner(), this::initTemperatureChart);
        mTempSlider.setOnChangeListener((slider, value) -> {
            mTempTxt.setText(value + "°");
            mTempValue = value;
        });
    }

    private void initTemperatureChart(List<Temperature> temperatureList){
        mTempChart.setDescription(null);
        mTempChart.setDrawBorders(false);
        List<String> dates = new ArrayList<>();
        List<Entry> entries = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        int i = 0;
        for (Temperature temp : temperatureList) {
            entries.add(new Entry(i, (float) temp.getValue()));
            dates.add(simpleDateFormat.format(temp.getDate()));
            i++;
        }

        XAxis xAxis = mTempChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        YAxis leftAxis = mTempChart.getAxisLeft();
        leftAxis.setGranularity(0.2f);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setAxisMinimum(36);
        leftAxis.setAxisMaximum(39.5f);
        YAxis rightY = mTempChart.getAxisRight();
        rightY.setEnabled(false);

        LineDataSet dataSet = new LineDataSet(entries, getString(R.string.body_temp));
        dataSet.setLineWidth(2);
        dataSet.setColor(getResources().getColor(R.color.colorSecondary));
        dataSet.setCircleColor(getResources().getColor(R.color.colorSecondary));
        dataSet.setDrawValues(false);
        LineData lineData = new LineData(dataSet);
        mTempChart.setData(lineData);
        mTempChart.invalidate();
    }

    @OnClick({R.id.fertility_update_calendar_btn, R.id.fertility_save_btn, R.id.fertility_first_day_txt})
    void onViewClicked(View item) {
        switch (item.getId()) {
            case R.id.fertility_first_day_txt:
                openDatePicker();
                break;
            case R.id.fertility_save_btn:
                createAndSaveTempInDb();
                break;
            case R.id.fertility_update_calendar_btn:
                verifyIfNextMonth();
                break;
        }
    }
}
