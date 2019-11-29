package com.benlefevre.endometriosismonitoring.ui.controllers.fragments;


import android.app.Activity;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.benlefevre.endometriosismonitoring.R;
import com.benlefevre.endometriosismonitoring.injection.Injection;
import com.benlefevre.endometriosismonitoring.injection.ViewModelFactory;
import com.benlefevre.endometriosismonitoring.models.Mood;
import com.benlefevre.endometriosismonitoring.models.Pain;
import com.benlefevre.endometriosismonitoring.models.Symptom;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DashboardFragment extends Fragment {


    @BindView(R.id.dashboard_fab)
    FloatingActionButton mDashboardFab;
    @BindView(R.id.dashboard_pain_chart)
    LineChart mPainChart;
    @BindView(R.id.dashboard_card_pain)
    CardView mCardPain;
    @BindView(R.id.dashboard_symptoms_chart)
    BarChart mSymptomsChart;
    @BindView(R.id.dashboard_card_symptom)
    CardView mCardSymptom;
    @BindView(R.id.dashboard_action_chart)
    BarChart mActionChart;
    @BindView(R.id.dashboard_card_action)
    CardView mCardAction;
    @BindView(R.id.dashboard_sleep_quality_chart)
    LineChart mSleepQualityChart;
    @BindView(R.id.dashboard_card_sleep)
    CardView mCardSleep;
    @BindView(R.id.dashboard_mood_chart)
    PieChart mMoodChart;
    @BindView(R.id.dashboard_card_mood)
    CardView mCardMood;

    private NavController mNavController;
    private Activity mActivity;
    private SharedViewModel mViewModel;
    private SimpleDateFormat mDateFormat;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mActivity = getActivity();
        mDateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        mNavController = Navigation.findNavController(mActivity,R.id.nav_host_fragment);
        configureViewModel();
        initializeChart();
        getUserPainForLast7Days();
    }


    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity, viewModelFactory).get(SharedViewModel.class);
    }

    /**
     * Initialize the empty data text for each chart
     */
    private void initializeChart() {
        mPainChart.setNoDataText(getString(R.string.pain_history));
        mPainChart.setNoDataTextColor(getResources().getColor(R.color.colorSecondary));
        mSymptomsChart.setNoDataText(getString(R.string.symptoms_history));
        mSymptomsChart.setNoDataTextColor(getResources().getColor(R.color.colorSecondary));
        mActionChart.setNoDataText(getString(R.string.action_history));
        mActionChart.setNoDataTextColor(getResources().getColor(R.color.colorSecondary));
        mSleepQualityChart.setNoDataText(getString(R.string.sleep_history));
        mSleepQualityChart.setNoDataTextColor(getResources().getColor(R.color.colorSecondary));
        mMoodChart.setNoDataText(getString(R.string.mood_history));
        mMoodChart.setNoDataTextColor(getResources().getColor(R.color.colorSecondary));
    }

    /**
     * Fetches the user Pain and linked data from locale room db
     */
    private void getUserPainForLast7Days() {
        Calendar calendar = Calendar.getInstance();
        Date end = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR,-7);
        Date begin = calendar.getTime();

        mViewModel.getPainByPeriod(begin,end).observe(getViewLifecycleOwner(), pains -> {
            List<Pain> painList = new ArrayList<>();
            painList.addAll(pains);
            Collections.reverse(painList);
            if (!painList.isEmpty()){
                setupPainChart(painList);
                getPainSymptom(painList);
                getPainMood(painList);
            }
        });
    }

    private void getPainMood(List<Pain> painList) {
//        TODO verifier le bon retour des moods
        List<Mood> moodList = new ArrayList<>();
        for (Pain pain : painList){
            mViewModel.getMoodByPainId(pain.getId()).observe(getViewLifecycleOwner(), mood -> {
                moodList.add(mood);
                if (!moodList.isEmpty())
                    setupMoodChart(moodList);
            });
        }
    }

    private void setupMoodChart(List<Mood> moodList) {

    }

    /**
     * Fetches the saved symptoms in DB for the given pain
     * @param painList the fetched pain in locale DB with viewmodel
     */
    private void getPainSymptom(List<Pain> painList) {
        for (Pain pain : painList){
            mViewModel.getSymptomByPainId(pain.getId()).observe(getViewLifecycleOwner(), symptoms -> {
                List<Symptom> symptomList = new ArrayList<>();
                symptomList.addAll(symptoms);
                if (!symptomList.isEmpty())
                    setupSymptomChart(symptomList);
            });
        }
    }

    /**
     * Setup the bar chart that represented the recurrence of symptoms over the last 7 days
     * @param symptomList the fetched symptoms in locale DB with viewmodel
     */
    private void setupSymptomChart(List<Symptom> symptomList) {
        mSymptomsChart.setDescription(null);
        mSymptomsChart.setTouchEnabled(false);
        mSymptomsChart.setFitBars(true);
        Legend legend = mSymptomsChart.getLegend();
        legend.setWordWrapEnabled(true);

        String burnsName = getString(R.string.burns), crampsName = getString(R.string.cramps), bleedingName = getString(R.string.bleeding), feverName = getString(R.string.fever),
                chillsName = getString(R.string.chills), bloatingName = getString(R.string.bloating) , hotFlushName = getString(R.string.hot_flush), diarrheaName = getString(R.string.diarrhea),
                constipationName = getString(R.string.constipation), nauseaName = getString(R.string.nausea), tiredName = getString(R.string.tired);

        int burns = 0, cramps = 0, bleeding = 0, fever = 0, chills = 0, bloating = 0,
                hotFlush = 0, diarrhea = 0, constipation = 0, nausea = 0, tired = 0;

        for (Symptom symptom : symptomList){
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

        XAxis xAxis = mSymptomsChart.getXAxis();
        xAxis.setEnabled(false);
        YAxis leftAxis = mSymptomsChart.getAxisLeft();
        leftAxis.setGranularity(1);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setAxisMinimum(0);
        YAxis rightAxis = mSymptomsChart.getAxisRight();
        rightAxis.setEnabled(false);

        BarEntry burnsEntry = new BarEntry(0, burns);
        BarDataSet burnsDataSet = new BarDataSet(Collections.singletonList(burnsEntry), burnsName);
        burnsDataSet.setColor(getResources().getColor(R.color.colorSecondary));
        burnsDataSet.setDrawValues(false);

        BarEntry crampsEntry = new BarEntry(1, cramps);
        BarDataSet crampsDataSet = new BarDataSet(Collections.singletonList(crampsEntry), crampsName);
        crampsDataSet.setColor(getResources().getColor(R.color.colorPrimary));
        crampsDataSet.setDrawValues(false);

        BarEntry bleedingEntry = new BarEntry(2, bleeding);
        BarDataSet bleedingDataSet = new BarDataSet(Collections.singletonList(bleedingEntry), bleedingName);
        bleedingDataSet.setColor(getResources().getColor(R.color.colorBackground));
        bleedingDataSet.setDrawValues(false);

        BarEntry feverEntry = new BarEntry(3, fever);
        BarDataSet feverDataSet = new BarDataSet(Collections.singletonList(feverEntry), feverName);
        feverDataSet.setColor(getResources().getColor(R.color.colorPrimaryVariant));
        feverDataSet.setDrawValues(false);

        BarEntry chillsEntry = new BarEntry(4, chills);
        BarDataSet chillsDataSet = new BarDataSet(Collections.singletonList(chillsEntry), chillsName);
        chillsDataSet.setColor(getResources().getColor(R.color.graph1));
        chillsDataSet.setDrawValues(false);

        BarEntry bloatingEntry = new BarEntry(5, bloating);
        BarDataSet bloatingDataSet = new BarDataSet(Collections.singletonList(bloatingEntry), bloatingName);
        bloatingDataSet.setColor(getResources().getColor(R.color.graph2));
        bloatingDataSet.setDrawValues(false);

        BarEntry hotFlushEntry = new BarEntry(6, hotFlush);
        BarDataSet hotFlushDataSet = new BarDataSet(Collections.singletonList(hotFlushEntry), hotFlushName);
        hotFlushDataSet.setColor(getResources().getColor(R.color.graph3));
        hotFlushDataSet.setDrawValues(false);

        BarEntry diarrheaEntry = new BarEntry(7, diarrhea);
        BarDataSet diarrheaDataSet = new BarDataSet(Collections.singletonList(diarrheaEntry), diarrheaName);
        diarrheaDataSet.setColor(getResources().getColor(R.color.graph4));
        diarrheaDataSet.setDrawValues(false);

        BarEntry constipationEntry = new BarEntry(8, constipation);
        BarDataSet constipationDataSet = new BarDataSet(Collections.singletonList(constipationEntry), constipationName);
        constipationDataSet.setColor(getResources().getColor(R.color.graph5));
        constipationDataSet.setDrawValues(false);

        BarEntry nauseaEntry = new BarEntry(9, nausea);
        BarDataSet nauseaDataSet = new BarDataSet(Collections.singletonList(nauseaEntry), nauseaName);
        nauseaDataSet.setColor(getResources().getColor(R.color.graph6));
        nauseaDataSet.setDrawValues(false);

        BarEntry tiredEntry = new BarEntry(10, tired);
        BarDataSet tiredDataSet = new BarDataSet(Collections.singletonList(tiredEntry), tiredName);
        tiredDataSet.setColor(getResources().getColor(R.color.graph7));
        tiredDataSet.setDrawValues(false);


        List<IBarDataSet> dataSet = new ArrayList<>();
        dataSet.add(burnsDataSet);
        dataSet.add(crampsDataSet);
        dataSet.add(bleedingDataSet);
        dataSet.add(feverDataSet);
        dataSet.add(chillsDataSet);
        dataSet.add(bloatingDataSet);
        dataSet.add(hotFlushDataSet);
        dataSet.add(diarrheaDataSet);
        dataSet.add(constipationDataSet);
        dataSet.add(nauseaDataSet);
        dataSet.add(tiredDataSet);
        BarData barData = new BarData(dataSet);
        mSymptomsChart.setData(barData);
        mSymptomsChart.invalidate();
    }

    /**
     * Setup the line chart that represented the pain over the last 7 days
     * @param painList the fetched pain in locale DB with viewmodel
     */
    private void setupPainChart(List<Pain> painList) {
        mPainChart.setDescription(null);
        mPainChart.setDrawBorders(false);
        mPainChart.setTouchEnabled(false);

        List<String> dates = new ArrayList<>();
        int i = 0;
        List<Entry> entries = new ArrayList<>();

//        creates a list with formatted dates to bind them in x axis
        for (Pain pain : painList) {
            entries.add(new Entry(i, pain.getIntensity()));
            dates.add(mDateFormat.format(pain.getDate()));
            i++;
        }

        XAxis xAxis = mPainChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        YAxis leftAxis = mPainChart.getAxisLeft();
        leftAxis.setGranularity(1f);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(10);
        YAxis rightY = mPainChart.getAxisRight();
        rightY.setEnabled(false);

        LineDataSet dataSet = new LineDataSet(entries, getString(R.string.my_pain));
        dataSet.setLineWidth(2);
        dataSet.setColor(getResources().getColor(R.color.colorSecondary));
        dataSet.setCircleColor(getResources().getColor(R.color.colorSecondary));
        dataSet.setDrawValues(false);
        LineData lineData = new LineData(dataSet);
        mPainChart.setData(lineData);
        mPainChart.invalidate();
    }


    @OnClick({R.id.dashboard_fab, R.id.dashboard_card_pain, R.id.dashboard_card_symptom, R.id.dashboard_card_action, R.id.dashboard_card_sleep, R.id.dashboard_card_mood})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dashboard_fab:
                mNavController.navigate(R.id.painFragment);
                break;
            case R.id.dashboard_card_pain:
                break;
            case R.id.dashboard_card_symptom:
                break;
            case R.id.dashboard_card_action:
                break;
            case R.id.dashboard_card_sleep:
                break;
            case R.id.dashboard_card_mood:
                break;
        }
    }
}
