package com.benlefevre.endometriosismonitoring.ui.controllers.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
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
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
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
    TextInputLayout mFertilityFirstDayLegend;
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
    private List<Integer> mTextViewList;
    private Calendar mCalendar;
    private int mMonth;
    private int mNbDaysInMonth;
    private int mFirstBox;

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
        ButterKnife.bind(this,view);
        mActivity = getActivity();
        configureViewModel();
        initTextViewList();
        initCalendarDate();
        setDurationListener();
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(mActivity);
        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity, viewModelFactory).get(SharedViewModel.class);
    }

    /**
     * Adds all TextView id in a List to handle them more easily
     */
    private void initTextViewList(){
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
    private void initCalendarDate(){
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mMonth  = mCalendar.get(Calendar.MONTH);
        setupNbDaysInMonth();
        setupFirstBoxValue();
        initDays(mFirstBox,mNbDaysInMonth);
        initFollowingMonthDays(mNbDaysInMonth);
        initPreviousMonthDays(mNbDaysInMonth,mMonth);
    }

    /**
     * Sets a value to mNbDaysInMonth according to the current month
     */
    private void setupNbDaysInMonth(){
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
    private void setupFirstBoxValue(){
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
     * @param firstBox the start box to bind value
     * @param nbDays the number of days for the current month
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
     * @param nbDays the number of days for the current month
     * @param month the value of the current month to know how many days there is in the previous month
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

    private void setDurationListener(){
        mDurationTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @OnClick({R.id.fertility_update_calendar_btn, R.id.fertility_save_btn, R.id.fertility_first_day_txt})
    public void onViewClicked(View item) {
        switch (item.getId()){
            case R.id.fertility_first_day_txt:
                break;
            case R.id.fertility_save_btn:
                break;
            case R.id.fertility_update_calendar_btn:
                break;
        }
    }
}
