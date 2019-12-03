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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActionDetailFragment extends Fragment {


    @BindView(R.id.action_detail_rep_chart)
    PieChart mActionDetailRepChart;
    @BindView(R.id.action_chip_week)
    Chip mActionChipWeek;
    @BindView(R.id.action_chip_month)
    Chip mActionChipMonth;
    @BindView(R.id.action_chip_6month)
    Chip mActionChip6month;
    @BindView(R.id.action_chip_year)
    Chip mActionChipYear;
    @BindView(R.id.action_detail_time_chart)
    LineChart mActionDetailTimeChart;

    private Activity mActivity;
    private SharedViewModel mViewModel;
    private int mDuration = 7;
    private List<Action> mActionList;

    public ActionDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_action_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mActivity = getActivity();
        configureViewModel();
        setupChipListener();
        getActionAccordingToDuration();
        mActionDetailTimeChart.setNoDataText("");
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity, viewModelFactory).get(SharedViewModel.class);
    }


    /**
     * Fetches Actions in locale DB with ViewModel according to user's duration choice.
     */
    private void getActionAccordingToDuration() {
        mActionList = new ArrayList<>();
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -mDuration);
        mViewModel.getActionsByPeriod(calendar.getTime(), today).observe(getViewLifecycleOwner(), actions -> {
            mActionList.addAll(actions);
            setupActionDivisionChart();
        });
    }

    /**
     * Configures the pie chart with the right value and defines the on value clickListener
     * behavior.
     */
    private void setupActionDivisionChart() {
        float[] percents = computeActionDivisionPercents();
        mActionDetailRepChart.setDescription(null);
        mActionDetailRepChart.setDrawHoleEnabled(false);
        mActionDetailRepChart.setUsePercentValues(true);
        mActionDetailRepChart.setDrawEntryLabels(false);
        mActionDetailRepChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                setupActionTimeChart(e.getData().toString());
            }

            @Override
            public void onNothingSelected() {
            }
        });

        Legend legend = mActionDetailRepChart.getLegend();
        legend.setWordWrapEnabled(true);

        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colorList = new ArrayList<>();

        if (percents[0] != 0) {
            entries.add(new PieEntry(percents[0], getString(R.string.sex), getString(R.string.sex)));
            colorList.add(getResources().getColor(R.color.graph1));
        }

        if (percents[1] != 0) {
            entries.add(new PieEntry(percents[1], getString(R.string.relaxation), getString(R.string.relaxation)));
            colorList.add(getResources().getColor(R.color.graph6));
        }

        if (percents[2] != 0) {
            entries.add(new PieEntry(percents[2], getString(R.string.sport), getString(R.string.sport)));
            colorList.add(getResources().getColor(R.color.graph7));
        }

        if (percents[3] != 0) {
            entries.add(new PieEntry(percents[3], getString(R.string.stress), getString(R.string.stress)));
            colorList.add(getResources().getColor(R.color.graph3));
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(colorList);
        pieDataSet.setValueFormatter(new PercentFormatter());
        pieDataSet.setValueTextColor(getResources().getColor(R.color.colorOnPrimary));
        pieDataSet.setValueTextSize(10);

        PieData data = new PieData(pieDataSet);
        mActionDetailRepChart.setData(data);
        mActionDetailRepChart.invalidate();
    }

    /**
     * Configures a LineChart to see the selected action evolution in time
     *
     * @param actionName the selected action name
     */
    private void setupActionTimeChart(String actionName) {
        String[] sportNameList = getResources().getStringArray(R.array.sport);
        mActionDetailTimeChart.setDescription(null);
        List<Action> actionTimeList = new ArrayList<>();
        for (Action action : mActionList) {
            if (!actionName.equals(getString(R.string.sport))) {
                if (action.getName().equals(actionName))
                    actionTimeList.add(action);
            } else {
                for (String s : sportNameList) {
                    if (action.getName().equals(s))
                        actionTimeList.add(action);
                }
            }
        }

        List<String> dates = new ArrayList<>();
        int i = 0;
        List<Entry> actionEntries = new ArrayList<>();
        List<Entry> painEntries = new ArrayList<>();

        for (Action action : mActionList) {
            if (actionTimeList.contains(action)) {
                actionEntries.add(new Entry(i, 4.5f));
                painEntries.add(new Entry(i, action.getPainValue()));
            } else {
                actionEntries.add(new Entry(i, 0));
                painEntries.add(new Entry(i, action.getPainValue()));
            }
            dates.add(Utils.formatDate(action.getDate()));
            i++;
        }

        XAxis xAxis = mActionDetailTimeChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        YAxis leftAxis = mActionDetailTimeChart.getAxisLeft();
        leftAxis.setGranularity(1f);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(10);
        YAxis rightY = mActionDetailTimeChart.getAxisRight();
        rightY.setEnabled(false);

        List<ILineDataSet> dataSet = new ArrayList<>();

        LineDataSet actionsDataSet = new LineDataSet(actionEntries, actionName);
        actionsDataSet.setDrawValues(false);
        actionsDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        if (actionName.equals(getString(R.string.sex))) {
            actionsDataSet.setCircleColor(getResources().getColor(R.color.graph1));
            actionsDataSet.setColor(getResources().getColor(R.color.graph1));
        }
        if (actionName.equals(getString(R.string.relaxation))) {
            actionsDataSet.setColor(getResources().getColor(R.color.graph6));
            actionsDataSet.setCircleColor(getResources().getColor(R.color.graph6));
        }
        if (actionName.equals(getString(R.string.stress))) {
            actionsDataSet.setCircleColor(getResources().getColor(R.color.graph3));
            actionsDataSet.setColor(getResources().getColor(R.color.graph3));
        }
        if (actionName.equals(getString(R.string.sport))) {
            actionsDataSet.setColor(getResources().getColor(R.color.graph7));
            actionsDataSet.setCircleColor(getResources().getColor(R.color.graph7));
        }
        dataSet.add(actionsDataSet);

        LineDataSet painDataSet = new LineDataSet(painEntries, getString(R.string.pain_value));
        painDataSet.setDrawValues(false);
        painDataSet.setColor(getResources().getColor(R.color.colorSecondary));
        painDataSet.setCircleColor(getResources().getColor(R.color.colorSecondary));
        painDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.add(painDataSet);

        LineData data = new LineData(dataSet);
        mActionDetailTimeChart.setData(data);
        mActionDetailTimeChart.invalidate();
    }

    /**
     * Computes the division in percents of each action (excepting sleep) vs the number of actions
     *
     * @return a float[] that contains the percent values
     */
    private float[] computeActionDivisionPercents() {
        String[] sportNameList = getResources().getStringArray(R.array.sport);
        float sex = 0, sport = 0, relaxation = 0, stress = 0;
        float sexP = 0, sportP = 0, relaxtionP = 0, stressP = 0;

        float size = 0;
        for (Action action : mActionList) {
            if (!action.getName().equals(getString(R.string.sleep)))
                size++;
            if (action.getName().equals(getString(R.string.sex)))
                sex++;
            if (action.getName().equals(getString(R.string.relaxation)))
                relaxation++;
            if (action.getName().equals(getString(R.string.stress)))
                stress++;
            for (String s : sportNameList) {
                if (action.getName().equals(s))
                    sport++;
            }
        }

        if (size != 0) {
            sexP = sex / size;
            relaxtionP = relaxation / size;
            sportP = sport / size;
            stressP = stress / size;
        }
        return new float[]{sexP, relaxtionP, sportP, stressP};
    }

    /**
     * Configures a checked listener on each chip to assign a value to mDuration and calls a method
     * to fetches pain in locale DB.
     */
    private void setupChipListener() {
        mActionChipWeek.setChecked(true);
        mActionChipWeek.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 7;
                getActionAccordingToDuration();
            }
        });
        mActionChipMonth.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 31;
                getActionAccordingToDuration();
            }
        }));
        mActionChip6month.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 180;
                getActionAccordingToDuration();
            }
        }));
        mActionChipYear.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 365;
                getActionAccordingToDuration();
            }
        }));
    }
}
