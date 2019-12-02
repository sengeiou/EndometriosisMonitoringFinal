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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.injection.Injection;
import com.benlefevre.endometriosismonitoring.injection.ViewModelFactory;
import com.benlefevre.endometriosismonitoring.models.Symptom;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
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

public class SymptomDetailFragment extends Fragment {


    @BindView(R.id.symptom_detail_rep_chart)
    PieChart mSymptomDetailRepChart;
    @BindView(R.id.symptom_chip_week)
    Chip mSymptomChipWeek;
    @BindView(R.id.symtom_chip_month)
    Chip mSymtomChipMonth;
    @BindView(R.id.symtom_chip_6month)
    Chip mSymtomChip6month;
    @BindView(R.id.symtom_chip_year)
    Chip mSymtomChipYear;
    @BindView(R.id.symptom_detail_time_chart)
    LineChart mSymptomDetailTimeChart;

    private Activity mActivity;
    private SharedViewModel mViewModel;
    private List<Symptom> mSymptomList;
    private SimpleDateFormat mDateFormat;
    private int mDuration = 7;

    public SymptomDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_symptom_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mActivity = getActivity();
        mDateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        configureViewModel();
        setupChipListener();
        getSymptomAccordingToUserChoice();
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity, viewModelFactory).get(SharedViewModel.class);
    }

    /**
     * Fetches Symptoms in locale DB with ViewModel according to user's duration choice.
     */
    private void getSymptomAccordingToUserChoice() {
        mSymptomList = new ArrayList<>();
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -mDuration);
        mViewModel.getSymptomByPeriod(calendar.getTime(), today).observe(getViewLifecycleOwner(), symptoms -> {
            mSymptomList.addAll(symptoms);
            setupDivisionSymptomChart();
        });
    }

    /**
     * Configures a checked listener on each chip to assign a value to mDuration and calls a method
     * to fetches pain in locale DB.
     */
    private void setupChipListener() {
        mSymptomChipWeek.setChecked(true);
        mSymptomChipWeek.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 7;
                getSymptomAccordingToUserChoice();
            }
        });
        mSymtomChipMonth.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 31;
                getSymptomAccordingToUserChoice();
            }
        }));
        mSymtomChip6month.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 180;
                getSymptomAccordingToUserChoice();
            }
        }));
        mSymtomChipYear.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mDuration = 365;
                getSymptomAccordingToUserChoice();
            }
        }));
    }


    /**
     * Configures a LineChart to see the selected symptom evolution in time
     * @param symptomName the selected symptom name
     */
    private void setupSymptomTimeChart(String symptomName){
        List<Symptom> symptomsTimeList = new ArrayList<>();
        for (Symptom symptom : mSymptomList){
            if (symptom.getName().equals(symptomName))
                symptomsTimeList.add(symptom);
        }
        mSymptomDetailTimeChart.setDescription(null);
        List<String> dates = new ArrayList<>();
        int i = 0;
        List<Entry> entries = new ArrayList<>();

        for (Symptom symptoms : mSymptomList){
            if (symptomsTimeList.contains(symptoms))
                entries.add(new Entry(i,1));
            else
                entries.add(new Entry(i,0));
            dates.add(mDateFormat.format(symptoms.getDate()));
            i++;
        }

        XAxis xAxis = mSymptomDetailTimeChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        YAxis leftAxis = mSymptomDetailTimeChart.getAxisLeft();
        leftAxis.setGranularity(1f);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(1.25f);
        leftAxis.setDrawLabels(false);
        YAxis rightY = mSymptomDetailTimeChart.getAxisRight();
        rightY.setEnabled(false);

        LineDataSet dataSet = new LineDataSet(entries, symptomName);
        dataSet.setLineWidth(2);
        if (symptomName.equals(getString(R.string.burns))){
        dataSet.setColor(getResources().getColor(R.color.colorSecondary));
        dataSet.setCircleColor(getResources().getColor(R.color.colorSecondary));
        }
        if (symptomName.equals(getString(R.string.cramps))){
            dataSet.setColor(getResources().getColor(R.color.colorPrimary));
            dataSet.setCircleColor(getResources().getColor(R.color.colorPrimary));
        }
        if (symptomName.equals(getString(R.string.bleeding))){
            dataSet.setColor(getResources().getColor(R.color.colorBackground));
            dataSet.setCircleColor(getResources().getColor(R.color.colorBackground));
        }
        if (symptomName.equals(getString(R.string.fever))){
            dataSet.setColor(getResources().getColor(R.color.colorPrimaryVariant));
            dataSet.setCircleColor(getResources().getColor(R.color.colorPrimaryVariant));
        }
        if (symptomName.equals(getString(R.string.chills))){
            dataSet.setColor(getResources().getColor(R.color.graph1));
            dataSet.setCircleColor(getResources().getColor(R.color.graph1));
        }
        if (symptomName.equals(getString(R.string.bloating))){
            dataSet.setColor(getResources().getColor(R.color.graph2));
            dataSet.setCircleColor(getResources().getColor(R.color.graph2));
        }
        if (symptomName.equals(getString(R.string.hot_flush))){
            dataSet.setColor(getResources().getColor(R.color.graph3));
            dataSet.setCircleColor(getResources().getColor(R.color.graph3));
        }
        if (symptomName.equals(getString(R.string.diarrhea))){
            dataSet.setColor(getResources().getColor(R.color.graph4));
            dataSet.setCircleColor(getResources().getColor(R.color.graph4));
        }
        if (symptomName.equals(getString(R.string.constipation))){
            dataSet.setColor(getResources().getColor(R.color.graph5));
            dataSet.setCircleColor(getResources().getColor(R.color.graph5));
        }
        if (symptomName.equals(getString(R.string.nausea))){
            dataSet.setColor(getResources().getColor(R.color.graph6));
            dataSet.setCircleColor(getResources().getColor(R.color.graph6));
        }
        if (symptomName.equals(getString(R.string.tired))){
            dataSet.setColor(getResources().getColor(R.color.graph7));
            dataSet.setCircleColor(getResources().getColor(R.color.graph7));
        }

        dataSet.setDrawValues(false);
        LineData lineData = new LineData(dataSet);
        mSymptomDetailTimeChart.setData(lineData);
        mSymptomDetailTimeChart.invalidate();
    }

    /**
     * Configures the pie chart with the right value and defines the on value clickListener
     * behavior.
     */
    private void setupDivisionSymptomChart() {
        float[] percents = computeSymptomDivisionPercent();
        mSymptomDetailRepChart.setDescription(null);
        mSymptomDetailRepChart.setDrawEntryLabels(false);
        mSymptomDetailRepChart.setUsePercentValues(true);
        mSymptomDetailRepChart.setDrawHoleEnabled(false);
        mSymptomDetailRepChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                setupSymptomTimeChart(e.getData().toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        Legend legend = mSymptomDetailRepChart.getLegend();
        legend.setWordWrapEnabled(true);

        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colorList = new ArrayList<>();

        if (percents[0] != 0) {
            entries.add(new PieEntry(percents[0], getString(R.string.burns), getString(R.string.burns)));
            colorList.add(getResources().getColor(R.color.colorSecondary));
        }

        if (percents[1] != 0) {
            entries.add(new PieEntry(percents[1], getString(R.string.cramps), getString(R.string.cramps)));
            colorList.add(getResources().getColor(R.color.colorPrimary));
        }

        if (percents[2] != 0) {
            entries.add(new PieEntry(percents[2], getString(R.string.bleeding), getString(R.string.bleeding)));
            colorList.add(getResources().getColor(R.color.colorBackground));
        }

        if (percents[3] != 0) {
            entries.add(new PieEntry(percents[3], getString(R.string.fever), getString(R.string.fever)));
            colorList.add(getResources().getColor(R.color.colorPrimaryVariant));
        }

        if (percents[4] != 0) {
            entries.add(new PieEntry(percents[4], getString(R.string.chills), getString(R.string.chills)));
            colorList.add(getResources().getColor(R.color.graph1));
        }

        if (percents[5] != 0) {
            entries.add(new PieEntry(percents[5], getString(R.string.bloating), getString(R.string.bloating)));
            colorList.add(getResources().getColor(R.color.graph2));
        }

        if (percents[6] != 0) {
            entries.add(new PieEntry(percents[6], getString(R.string.hot_flush), getString(R.string.hot_flush)));
            colorList.add(getResources().getColor(R.color.graph3));
        }

        if (percents[7] != 0) {
            entries.add(new PieEntry(percents[7], getString(R.string.diarrhea), getString(R.string.diarrhea)));
            colorList.add(getResources().getColor(R.color.graph4));
        }

        if (percents[8] != 0) {
            entries.add(new PieEntry(percents[8], getString(R.string.constipation), getString(R.string.constipation)));
            colorList.add(getResources().getColor(R.color.graph5));
        }

        if (percents[9] != 0) {
            entries.add(new PieEntry(percents[9], getString(R.string.nausea), getString(R.string.nausea)));
            colorList.add(getResources().getColor(R.color.graph6));
        }

        if (percents[10] != 0) {
            entries.add(new PieEntry(percents[10], getString(R.string.tired), getString(R.string.tired)));
            colorList.add(getResources().getColor(R.color.graph7));
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(colorList);
        pieDataSet.setValueFormatter(new PercentFormatter());
        pieDataSet.setValueTextColor(getResources().getColor(R.color.colorOnPrimary));
        pieDataSet.setValueTextSize(10);

        PieData data = new PieData(pieDataSet);
        mSymptomDetailRepChart.setData(data);
        mSymptomDetailRepChart.invalidate();
    }

    /**
     * Computes the division in percents of each symptom vs the number of symptoms
     * @return a float[] that contains the percent values
     */
    private float[] computeSymptomDivisionPercent() {
        float burns = 0, cramps = 0, bleeding = 0, fever = 0, chills = 0, bloating = 0,
                hotFlush = 0, diarrhea = 0, constipation = 0, nausea = 0, tired = 0;

        float burnsP = 0, crampsP = 0, bleedingP = 0, feverP = 0, chillsP = 0, bloatingP = 0,
                hotFlushP = 0, diarrheaP = 0, constipationP = 0, nauseaP = 0, tiredP = 0;

        String burnsName = getString(R.string.burns), crampsName = getString(R.string.cramps),
                bleedingName = getString(R.string.bleeding), feverName = getString(R.string.fever),
                chillsName = getString(R.string.chills), bloatingName = getString(R.string.bloating),
                hotFlushName = getString(R.string.hot_flush), diarrheaName = getString(R.string.diarrhea),
                constipationName = getString(R.string.constipation), nauseaName = getString(R.string.nausea),
                tiredName = getString(R.string.tired);

        float size = mSymptomList.size();

        for (Symptom symptom : mSymptomList) {
            String symp = symptom.getName();
            if (symp.equals(burnsName))
                burns++;
            if (symp.equals(crampsName))
                cramps++;
            if (symp.equals(bleedingName))
                bleeding++;
            if (symp.equals(chillsName))
                chills++;
            if (symp.equals(feverName))
                fever++;
            if (symp.equals(bloatingName))
                bloating++;
            if (symp.equals(hotFlushName))
                hotFlush++;
            if (symp.equals(diarrheaName))
                diarrhea++;
            if (symp.equals(constipationName))
                constipation++;
            if (symp.equals(nauseaName))
                nausea++;
            if (symp.equals(tiredName))
                tired++;
        }

        if (size != 0) {
            burnsP = burns / size;
            crampsP = cramps / size;
            bleedingP = bleeding / size;
            chillsP = chills / size;
            feverP = fever / size;
            bloatingP = bloating / size;
            hotFlushP = hotFlush / size;
            diarrheaP = diarrhea / size;
            constipationP = constipation / size;
            nauseaP = nausea / size;
            tiredP = tired / size;
        }

        return new float[]{burnsP, crampsP, bleedingP, feverP, chillsP, bloatingP, hotFlushP, diarrheaP,
                constipationP, nauseaP, tiredP};
    }
}
