package com.benlefevre.endometriosismonitoring.ui.controllers.fragments;


import android.app.Activity;
import android.os.Bundle;
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
import com.benlefevre.endometriosismonitoring.models.Action;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
import com.benlefevre.endometriosismonitoring.utils.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SleepDetailFragment extends Fragment {


    @BindView(R.id.sleep_detail_chart)
    LineChart mSleepDetailChart;
    @BindView(R.id.sleep_chip_week)
    Chip mSleepChipWeek;
    @BindView(R.id.sleep_chip_month)
    Chip mSleepChipMonth;
    @BindView(R.id.sleep_chip_6month)
    Chip mSleepChip6month;
    @BindView(R.id.sleep_chip_year)
    Chip mSleepChipYear;

    private Activity mActivity;
    private SharedViewModel mViewModel;
    private int mDuration = 7;
    private List<Action> mSleepList;


    public SleepDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sleep_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mActivity = getActivity();
        configureViewModel();
        setupChipListener();
        getSleepAccordingToDuration();
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity, viewModelFactory).get(SharedViewModel.class);
    }

    /**
     * Fetches Actions in locale DB with ViewModel according to user's duration choice.
     */
    private void getSleepAccordingToDuration() {
        mSleepList = new ArrayList<>();
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -mDuration);
        mViewModel.getActionsByPeriod(calendar.getTime(), today).observe(getViewLifecycleOwner(), actions -> {
            mSleepList.addAll(actions);
            setupSleepChart();
        });
    }

    /**
     * Configures a LineChart to see the sleep evolution in time vs pain value history
     */
    private void setupSleepChart() {
        mSleepDetailChart.setDescription(null);
        List<Action> sleepList = new ArrayList<>();
        for (Action action : mSleepList) {
            if (action.getName().equals(getString(R.string.sleep))) {
                sleepList.add(action);
            }
        }

        List<String> dates = new ArrayList<>();
        int i = 0;
        List<Entry> sleepEntries = new ArrayList<>();
        List<Entry> painEntries = new ArrayList<>();

        for (Action action : mSleepList) {
            if (sleepList.contains(action)) {
                sleepEntries.add(new Entry(i, action.getIntensity()));
                painEntries.add(new Entry(i, action.getPainValue()));
            } else {
                sleepEntries.add(new Entry(i, 0));
                painEntries.add(new Entry(i, action.getPainValue()));
            }
            dates.add(Utils.formatDate(action.getDate()));
            i++;
        }

        XAxis xAxis = mSleepDetailChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        YAxis leftAxis = mSleepDetailChart.getAxisLeft();
        leftAxis.setGranularity(1f);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(10);
        YAxis rightY = mSleepDetailChart.getAxisRight();
        rightY.setEnabled(false);

        List<ILineDataSet> dataSet = new ArrayList<>();
        LineDataSet sleepDataSet = new LineDataSet(sleepEntries,getString(R.string.sleep_quality));
        sleepDataSet.setColor(getResources().getColor(R.color.graph2));
        sleepDataSet.setCircleColor(getResources().getColor(R.color.graph2));
        sleepDataSet.setDrawValues(false);
        dataSet.add(sleepDataSet);
        LineDataSet painDataSet = new LineDataSet(painEntries,getString(R.string.my_pain));
        painDataSet.setCircleColor(getResources().getColor(R.color.colorSecondary));
        painDataSet.setColor(getResources().getColor(R.color.colorSecondary));
        painDataSet.setDrawValues(false);
        dataSet.add(painDataSet);

        LineData data = new LineData(dataSet);
        mSleepDetailChart.setData(data);
        mSleepDetailChart.invalidate();
    }

    /**
     * Configures a checked listener on each chip to assign a value to mDuration and calls a method
     * to fetches pain in locale DB.
     */
    private void setupChipListener() {
        mSleepChipWeek.setChecked(true);
        mSleepChipWeek.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 7;
                getSleepAccordingToDuration();
            }
        });
        mSleepChipMonth.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 31;
                getSleepAccordingToDuration();
            }
        }));
        mSleepChip6month.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 180;
                getSleepAccordingToDuration();
            }
        }));
        mSleepChipYear.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 365;
                getSleepAccordingToDuration();
            }
        }));
    }
}
