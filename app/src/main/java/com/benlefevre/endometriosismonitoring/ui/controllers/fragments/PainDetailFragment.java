package com.benlefevre.endometriosismonitoring.ui.controllers.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.injection.Injection;
import com.benlefevre.endometriosismonitoring.injection.ViewModelFactory;
import com.benlefevre.endometriosismonitoring.models.Action;
import com.benlefevre.endometriosismonitoring.models.Mood;
import com.benlefevre.endometriosismonitoring.models.Pain;
import com.benlefevre.endometriosismonitoring.models.Symptom;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
import com.facebook.share.Share;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PainDetailFragment extends Fragment {


    @BindView(R.id.pain_detail_chart)
    LineChart mPainDetailChart;
    @BindView(R.id.chip_week)
    Chip mChipWeek;
    @BindView(R.id.chip_month)
    Chip mChipMonth;
    @BindView(R.id.chip_6month)
    Chip mChip6month;
    @BindView(R.id.chip_year)
    Chip mChipYear;
    @BindView(R.id.pain_detail_date_txt)
    MaterialTextView mDateTxt;
    @BindView(R.id.pain_detail_value)
    MaterialTextView mValue;
    @BindView(R.id.pain_detail_location_txt)
    MaterialTextView mLocationTxt;
    @BindView(R.id.pain_details_symptom_txt)
    MaterialTextView mSymptomTxt;
    @BindView(R.id.pain_details_mood_txt)
    MaterialTextView mMoodTxt;
    @BindView(R.id.pain_details_recycler_view)
    RecyclerView mRecyclerView;

    private Activity mActivity;
    private SharedViewModel mViewModel;
    private int mDuration = 7;
    private Calendar mCalendar;
    private List<Pain> mPainList;
    private List<Action> mActionList;
    private SimpleDateFormat mDateFormat;
    private Date mToday;
    private long mPainSelectedId = 0;

    public PainDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pain_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mActivity = getActivity();
        mToday = new Date();
        mCalendar = Calendar.getInstance();
        mDateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        mActionList = new ArrayList<>();
        configureViewModel();
        setupChipListener();
        getPainAccordingPeriod();
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity, viewModelFactory).get(SharedViewModel.class);
    }

    /**
     * Sets a checked listener on each chip to assign a value to mDuration and calls a method
     * to fetches pain in locale DB.
     */
    private void setupChipListener() {
        mChipWeek.setChecked(true);
        mChipWeek.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 7;
                getPainAccordingPeriod();
            }
        });
        mChipMonth.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 31;
                getPainAccordingPeriod();
            }
        }));
        mChip6month.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 180;
                getPainAccordingPeriod();
            }
        }));
        mChipYear.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 365;
                getPainAccordingPeriod();
            }
        }));
    }

    /**
     * Fetches Pain in locale Db with ViewModel
     */
    private void getPainAccordingPeriod(){
        mPainList = new ArrayList<>();
        mCalendar= Calendar.getInstance();
        mCalendar.add(Calendar.DAY_OF_YEAR, -mDuration);
        mViewModel.getPainByPeriod(mCalendar.getTime(),mToday).observe(getViewLifecycleOwner(), painList -> {
            mPainList.addAll(painList);
            if (!mPainList.isEmpty())
                setupPainChart();
        });
    }

    /**
     * Sets the line chart with the user pain and defines the behavior on user value click
     */
    private void setupPainChart() {
        mPainDetailChart.setDescription(null);
        mPainDetailChart.setDrawBorders(false);
        mPainDetailChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.i("info", "onValueSelected " + e.getData().toString());
                mPainSelectedId = (long) e.getData();
                getAllUserInputForSelectedPain();
            }
            @Override
            public void onNothingSelected() {
            }
        });

        List<String> dates = new ArrayList<>();
        int i = 0;
        List<Entry> entries = new ArrayList<>();

        for (Pain pain : mPainList) {
            Entry entry = new Entry(i, pain.getIntensity());
            entry.setData(pain.getId());
            entries.add(entry);
            dates.add(mDateFormat.format(pain.getDate()));
            i++;
        }

        XAxis xAxis = mPainDetailChart.getXAxis();
        xAxis.setGranularity(5);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        YAxis leftAxis = mPainDetailChart.getAxisLeft();
        leftAxis.setGranularity(1f);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(10);
        YAxis rightY = mPainDetailChart.getAxisRight();
        rightY.setEnabled(false);

        LineDataSet dataSet = new LineDataSet(entries, getString(R.string.my_pain));
        dataSet.setLineWidth(2);
        dataSet.setColor(getResources().getColor(R.color.colorSecondary));
        dataSet.setCircleColor(getResources().getColor(R.color.colorSecondary));
        dataSet.setDrawValues(false);
        LineData lineData = new LineData(dataSet);
        mPainDetailChart.setData(lineData);
        mPainDetailChart.invalidate();
    }

    private void getAllUserInputForSelectedPain(){
        mActionList.clear();
        for (Pain pain : mPainList){
            if (pain.getId() == mPainSelectedId){
                mLocationTxt.setText(pain.getLocation());
                mDateTxt.setText(mDateFormat.format(pain.getDate()));
                mValue.setText(String.valueOf(pain.getIntensity()));
            }
        }
        mViewModel.getMoodByPainId(mPainSelectedId).observe(getViewLifecycleOwner(), mood -> {
            if (mood != null)
                mMoodTxt.setText(mood.getValue());
            else
                mMoodTxt.setText("");
        });

        final String[] symptomListTxt = {""};
        mViewModel.getSymptomByPainId(mPainSelectedId).observe(getViewLifecycleOwner(), symptoms -> {
            for (Symptom symptom : symptoms){
                symptomListTxt[0] += symptom + ", ";
            }
            mSymptomTxt.setText(symptomListTxt[0]);
        });

        mViewModel.getActionByPainId(mPainSelectedId).observe(getViewLifecycleOwner(), actions -> {
            mActionList.addAll(actions);

        });
    }

}
