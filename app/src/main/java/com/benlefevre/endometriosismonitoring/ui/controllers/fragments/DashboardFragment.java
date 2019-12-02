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
import com.benlefevre.endometriosismonitoring.models.Action;
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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
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
        ButterKnife.bind(this, view);
        mActivity = getActivity();
        mDateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        mNavController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
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
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date begin = calendar.getTime();
        List<Pain> painList = new ArrayList<>();
        mViewModel.getPainByPeriod(begin, end).observe(getViewLifecycleOwner(), pains -> {
            painList.addAll(pains);
//            Collections.reverse(painList);
            if (!painList.isEmpty()) {
                setupPainChart(painList);
                getPainSymptom(painList);
                getPainMood(painList);
                getPainActions(painList);
            }
        });
    }

    /**
     * Fetches the saved symptoms in DB for the given pain
     *
     * @param painList the fetched pain in locale DB with ViewModel
     */
    private void getPainSymptom(List<Pain> painList) {
        List<Symptom> symptomList = new ArrayList<>();
        for (Pain pain : painList) {
            mViewModel.getSymptomByPainId(pain.getId()).observe(getViewLifecycleOwner(), symptoms -> {
                symptomList.addAll(symptoms);
                if (!symptomList.isEmpty())
                    setupSymptomChart(symptomList);
            });
        }
    }

    /**
     * Fetches all saved action that corresponding to a painId
     *
     * @param painList all pain fetched in locale DB for a given period
     */
    private void getPainActions(List<Pain> painList) {
        List<Action> sleepList = new ArrayList<>();
        List<Action> actionList = new ArrayList<>();
        for (Pain pain : painList) {
            mViewModel.getActionByPainId(pain.getId()).observe(getViewLifecycleOwner(), actions -> {
                for (Action action : actions) {
                    if (action.getName().equals(getString(R.string.sleep)))
                        sleepList.add(action);
                    else
                        actionList.add(action);
                }
                setupSleepChart(sleepList, painList);
                setupActionChart(actionList);
            });
        }
    }


    /**
     * Fetches all saved mood that corresponding to a painId
     *
     * @param painList all pain fetched in locale Db for a given period
     */
    private void getPainMood(List<Pain> painList) {
        List<Mood> moodList = new ArrayList<>();
        for (Pain pain : painList) {
            mViewModel.getMoodByPainId(pain.getId()).observe(getViewLifecycleOwner(), mood -> {
                if (mood != null)
                    moodList.add(mood);
                if (!moodList.isEmpty()) {
                    float[] moods = computePercentMood(moodList);
                    setupMoodChart(moods);
                }
            });
        }
    }

    /**
     * Computes the repartition of user's mood of 7 days
     *
     * @param moodList the fetched mood in locale DB with ViewModel
     * @return an array of float that contains the computed percent
     */
    private float[] computePercentMood(List<Mood> moodList) {
        float sad = 0, sick = 0, irritated = 0, happy = 0, veryHappy = 0;
        float moodSize = moodList.size();
        float sadPercent = 0, sickPercent = 0, irritatedPercent = 0, happyPercent = 0,
                veryHappyPercent = 0;
        for (Mood mood : moodList) {
            if (mood.getValue().equals(getString(R.string.sad)))
                sad++;
            if (mood.getValue().equals(getString(R.string.sick)))
                sick++;
            if (mood.getValue().equals(getString(R.string.irritated)))
                irritated++;
            if (mood.getValue().equals(getString(R.string.happy)))
                happy++;
            if (mood.getValue().equals(getString(R.string.very_happy)))
                veryHappy++;
        }
        if (moodSize != 0) {
            sadPercent = sad / moodSize;
            sickPercent = sick / moodSize;
            irritatedPercent = irritated / moodSize;
            happyPercent = happy / moodSize;
            veryHappyPercent = veryHappy / moodSize;
        }
        return new float[]{sadPercent, sickPercent, irritatedPercent, happyPercent, veryHappyPercent};
    }

    /**
     * Setup the pie chart that represented the mood repartition for over last 7 days
     *
     * @param moodList the fetched mood in locale DB with ViewModel
     */
    private void setupMoodChart(float[] moodList) {
        mMoodChart.setDescription(null);
        mMoodChart.setUsePercentValues(true);
        mMoodChart.setDrawEntryLabels(false);
        mMoodChart.setDrawHoleEnabled(false);
        mMoodChart.setTouchEnabled(false);

        Legend legend = mMoodChart.getLegend();
        legend.setWordWrapEnabled(true);

        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colorList = new ArrayList<>();

        if (moodList[0] != 0) {
            entries.add(new PieEntry(moodList[0], getString(R.string.sad)));
            colorList.add(getResources().getColor(R.color.graph6));
        }
        if (moodList[1] != 0) {
            entries.add(new PieEntry(moodList[1], getString(R.string.sick)));
            colorList.add(getResources().getColor(R.color.graph3));
        }
        if (moodList[2] != 0) {
            entries.add(new PieEntry(moodList[2], getString(R.string.irritated)));
            colorList.add(getResources().getColor(R.color.graph7));
        }
        if (moodList[3] != 0) {
            entries.add(new PieEntry(moodList[3], getString(R.string.happy)));
            colorList.add(getResources().getColor(R.color.graph2));
        }
        if (moodList[4] != 0) {
            entries.add(new PieEntry(moodList[4], getString(R.string.very_happy)));
            colorList.add(getResources().getColor(R.color.graph1));
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(colorList);
        pieDataSet.setValueFormatter(new PercentFormatter());
        pieDataSet.setValueTextColor(getResources().getColor(R.color.colorOnPrimary));
        pieDataSet.setValueTextSize(10);

        PieData moodData = new PieData(pieDataSet);
        mMoodChart.setData(moodData);
        mMoodChart.invalidate();
    }

    /**
     * Setup the multiBar chart that represented all practiced activities by user for the given pain
     *
     * @param actionList the fetched pain in locale DB with ViewModel
     */
    private void setupActionChart(List<Action> actionList) {
        mActionChart.setDescription(null);
        mActionChart.setTouchEnabled(false);
        mActionChart.setFitBars(true);
        Legend legend = mActionChart.getLegend();
        legend.setWordWrapEnabled(true);

        XAxis xAxis = mActionChart.getXAxis();
        xAxis.setEnabled(false);

        YAxis leftAxis = mActionChart.getAxisLeft();
        leftAxis.setAxisMinimum(0);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(20);
        YAxis rightAxis = mActionChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0);

        String stressName = getString(R.string.stress);
        String relaxationName = getString(R.string.relaxation);
        String sexName = getString(R.string.sex);
        String[] sports = getResources().getStringArray(R.array.sport);
        String sexDurationName = getString(R.string.sex_duration);
        String nbSexName = getString(R.string.nb_sex);
        String stressIntensityName = getString(R.string.stress_intensity);
        String nbStressName = getString(R.string.stress_exposure);
        String relaxationDurationName = getString(R.string.relax_duration);
        String nbRelaxationName = getString(R.string.nb_relax);
        String sportDurationName = getString(R.string.sport_duration);
        String sportIntensityName = getString(R.string.sport_intensity);
        String nbSportName = getString(R.string.nb_sport);

        int sportIntensity = 0, sportDuration = 0, nbSport = 0;
        int stressIntensity = 0, nbStress = 0;
        int relaxationDuration = 0, nbRelaxation = 0;
        int sexDuration = 0, nbSex = 0;

        for (Action action : actionList) {
            if (action.getName().equals(stressName)) {
                stressIntensity += action.getIntensity();
                nbStress++;
            }
            if (action.getName().equals(relaxationName)) {
                relaxationDuration += action.getDuration();
                nbRelaxation++;
            }
            if (action.getName().equals(sexName)) {
                sexDuration += action.getDuration();
                nbSex++;
            }
            for (String s : sports) {
                if (action.getName().equals(s)) {
                    sportIntensity += action.getIntensity();
                    sportDuration += action.getDuration();
                    nbSport++;
                }
            }
        }

        if (nbStress != 0)
            stressIntensity = stressIntensity / nbStress;
        if (nbRelaxation != 0)
            relaxationDuration = relaxationDuration / nbRelaxation;
        if (nbSex != 0)
            sexDuration = sexDuration / nbSex;
        if (nbSport != 0) {
            sportIntensity = sportIntensity / nbSport;
            sportDuration = sportDuration / nbSport;
        }

        int indexCounter = -1;
        List<IBarDataSet> barDataSet = new ArrayList<>();

        if (nbSex != 0) {
            BarEntry sexDurationEntry = new BarEntry(++indexCounter, sexDuration);
            BarEntry nbSexEntry = new BarEntry(++indexCounter, nbSex);
            BarDataSet sexDurationDataSet = new BarDataSet(Collections.singletonList(sexDurationEntry), sexDurationName);
            sexDurationDataSet.setColor(getResources().getColor(R.color.graph1));
            sexDurationDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            sexDurationDataSet.setValueTextSize(10);
            BarDataSet nbSexDataSet = new BarDataSet(Collections.singletonList(nbSexEntry), nbSexName);
            nbSexDataSet.setColor(getResources().getColor(R.color.graph2));
            nbSexDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            nbSexDataSet.setValueTextSize(10);
            barDataSet.add(sexDurationDataSet);
            barDataSet.add(nbSexDataSet);
        }

        if (nbRelaxation != 0) {
            BarEntry relaxationDurationEntry = new BarEntry(++indexCounter, relaxationDuration);
            BarEntry nbRelaxationEntry = new BarEntry(++indexCounter, nbRelaxation);
            BarDataSet relaxationDataSet = new BarDataSet(Collections.singletonList(relaxationDurationEntry), relaxationDurationName);
            relaxationDataSet.setColor(getResources().getColor(R.color.graph5));
            relaxationDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            relaxationDataSet.setValueTextSize(10);
            BarDataSet nbRelaxationDataSet = new BarDataSet(Collections.singletonList(nbRelaxationEntry), nbRelaxationName);
            nbRelaxationDataSet.setColor(getResources().getColor(R.color.graph6));
            nbRelaxationDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            nbRelaxationDataSet.setValueTextSize(10);
            barDataSet.add(relaxationDataSet);
            barDataSet.add(nbRelaxationDataSet);
        }

        if (nbSport != 0) {
            BarEntry sportDurationEntry = new BarEntry(++indexCounter, sportDuration);
            BarEntry sportIntensityEntry = new BarEntry(++indexCounter, sportIntensity);
            BarEntry nbSportEntry = new BarEntry(++indexCounter, nbSport);
            BarDataSet sportDurationDataSet = new BarDataSet(Collections.singletonList(sportDurationEntry), sportDurationName);
            sportDurationDataSet.setColor(getResources().getColor(R.color.graph7));
            sportDurationDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            sportDurationDataSet.setValueTextSize(10);
            BarDataSet sportIntensityDataSet = new BarDataSet(Collections.singletonList(sportIntensityEntry), sportIntensityName);
            sportIntensityDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            sportIntensityDataSet.setValueTextSize(10);
            sportIntensityDataSet.setColor(getResources().getColor(R.color.colorSecondary));
            BarDataSet nbSportDataSet = new BarDataSet(Collections.singletonList(nbSportEntry), nbSportName);
            nbSportDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            nbSportDataSet.setValueTextSize(10);
            nbSportDataSet.setColor(getResources().getColor(R.color.colorPrimary));
            barDataSet.add(sportDurationDataSet);
            barDataSet.add(sportIntensityDataSet);
            barDataSet.add(nbSportDataSet);
        }


        if (nbStress != 0) {
            BarEntry stressIntensityEntry = new BarEntry(++indexCounter, stressIntensity);
            BarEntry nbStressEntry = new BarEntry(++indexCounter, nbStress);
            BarDataSet stressDataSet = new BarDataSet(Collections.singletonList(stressIntensityEntry), stressIntensityName);
            stressDataSet.setColor(getResources().getColor(R.color.graph3));
            stressDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            stressDataSet.setValueTextSize(10);
            BarDataSet nbStressDataSet = new BarDataSet(Collections.singletonList(nbStressEntry), nbStressName);
            nbStressDataSet.setColor(getResources().getColor(R.color.graph4));
            nbStressDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            nbStressDataSet.setValueTextSize(10);
            barDataSet.add(stressDataSet);
            barDataSet.add(nbStressDataSet);
        }

        if (!barDataSet.isEmpty()) {
            BarData barData = new BarData(barDataSet);
            mActionChart.setData(barData);
            mActionChart.invalidate();
        }
    }

    /**
     * Setup the line chart that represented the sleep quality over last 7 days
     *
     * @param sleepList the fetched action that monitoring the sleep quality
     * @param painList  the fetched pain in locale DB for a given period
     */
    private void setupSleepChart(List<Action> sleepList, List<Pain> painList) {
        mSleepQualityChart.setDescription(null);
        mSleepQualityChart.setTouchEnabled(false);

        List<String> dates = new ArrayList<>();
        int i = 0;
        List<Entry> entries = new ArrayList<>();

        for (Action action : sleepList) {
            entries.add(new Entry(i, action.getIntensity()));
            for (Pain pain : painList) {
                if (pain.getId() == action.getPainId()) {
                    dates.add(mDateFormat.format(pain.getDate()));
                }
            }
            i++;
        }

        XAxis xAxis = mSleepQualityChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        YAxis leftAxis = mSleepQualityChart.getAxisLeft();
        leftAxis.setGranularity(1);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(10);

        YAxis rightAxis = mSleepQualityChart.getAxisRight();
        rightAxis.setEnabled(false);

        LineDataSet dataSet = new LineDataSet(entries, getString(R.string.sleep_quality));
        dataSet.setLineWidth(2);
        dataSet.setColor(getResources().getColor(R.color.colorSecondary));
        dataSet.setCircleColor(getResources().getColor(R.color.colorSecondary));
        dataSet.setDrawValues(false);

        LineData lineData = new LineData(dataSet);
        mSleepQualityChart.setData(lineData);
        mSleepQualityChart.invalidate();
    }

    /**
     * Setup the bar chart that represented the recurrence of symptoms over the last 7 days
     *
     * @param symptomList the fetched symptoms in locale DB with ViewModel
     */
    private void setupSymptomChart(List<Symptom> symptomList) {
        mSymptomsChart.setDescription(null);
        mSymptomsChart.setTouchEnabled(false);
        mSymptomsChart.setFitBars(true);
        Legend legend = mSymptomsChart.getLegend();
        legend.setWordWrapEnabled(true);

        String burnsName = getString(R.string.burns), crampsName = getString(R.string.cramps), bleedingName = getString(R.string.bleeding), feverName = getString(R.string.fever),
                chillsName = getString(R.string.chills), bloatingName = getString(R.string.bloating), hotFlushName = getString(R.string.hot_flush), diarrheaName = getString(R.string.diarrhea),
                constipationName = getString(R.string.constipation), nauseaName = getString(R.string.nausea), tiredName = getString(R.string.tired);

        int burns = 0, cramps = 0, bleeding = 0, fever = 0, chills = 0, bloating = 0,
                hotFlush = 0, diarrhea = 0, constipation = 0, nausea = 0, tired = 0;

        for (Symptom symptom : symptomList) {
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

        List<IBarDataSet> dataSet = new ArrayList<>();
        int indexDataSet = 0;

        if (burns != 0) {
            BarEntry burnsEntry = new BarEntry(indexDataSet, burns);
            BarDataSet burnsDataSet = new BarDataSet(Collections.singletonList(burnsEntry), burnsName);
            burnsDataSet.setColor(getResources().getColor(R.color.colorSecondary));
            burnsDataSet.setDrawValues(false);
            dataSet.add(burnsDataSet);
            indexDataSet++;
        }

        if (cramps != 0) {
            BarEntry crampsEntry = new BarEntry(indexDataSet, cramps);
            BarDataSet crampsDataSet = new BarDataSet(Collections.singletonList(crampsEntry), crampsName);
            crampsDataSet.setColor(getResources().getColor(R.color.colorPrimary));
            crampsDataSet.setDrawValues(false);
            dataSet.add(crampsDataSet);
            indexDataSet++;
        }

        if (bleeding != 0) {
            BarEntry bleedingEntry = new BarEntry(indexDataSet, bleeding);
            BarDataSet bleedingDataSet = new BarDataSet(Collections.singletonList(bleedingEntry), bleedingName);
            bleedingDataSet.setColor(getResources().getColor(R.color.colorBackground));
            bleedingDataSet.setDrawValues(false);
            dataSet.add(bleedingDataSet);
            indexDataSet++;
        }

        if (fever != 0) {
            BarEntry feverEntry = new BarEntry(indexDataSet, fever);
            BarDataSet feverDataSet = new BarDataSet(Collections.singletonList(feverEntry), feverName);
            feverDataSet.setColor(getResources().getColor(R.color.colorPrimaryVariant));
            feverDataSet.setDrawValues(false);
            dataSet.add(feverDataSet);
            indexDataSet++;
        }

        if (chills != 0) {
            BarEntry chillsEntry = new BarEntry(indexDataSet, chills);
            BarDataSet chillsDataSet = new BarDataSet(Collections.singletonList(chillsEntry), chillsName);
            chillsDataSet.setColor(getResources().getColor(R.color.graph1));
            chillsDataSet.setDrawValues(false);
            dataSet.add(chillsDataSet);
            indexDataSet++;
        }

        if (bloating != 0) {
            BarEntry bloatingEntry = new BarEntry(indexDataSet, bloating);
            BarDataSet bloatingDataSet = new BarDataSet(Collections.singletonList(bloatingEntry), bloatingName);
            bloatingDataSet.setColor(getResources().getColor(R.color.graph2));
            bloatingDataSet.setDrawValues(false);
            dataSet.add(bloatingDataSet);
            indexDataSet++;
        }

        if (hotFlush != 0) {
            BarEntry hotFlushEntry = new BarEntry(indexDataSet, hotFlush);
            BarDataSet hotFlushDataSet = new BarDataSet(Collections.singletonList(hotFlushEntry), hotFlushName);
            hotFlushDataSet.setColor(getResources().getColor(R.color.graph3));
            hotFlushDataSet.setDrawValues(false);
            dataSet.add(hotFlushDataSet);
            indexDataSet++;
        }

        if (diarrhea != 0) {
            BarEntry diarrheaEntry = new BarEntry(indexDataSet, diarrhea);
            BarDataSet diarrheaDataSet = new BarDataSet(Collections.singletonList(diarrheaEntry), diarrheaName);
            diarrheaDataSet.setColor(getResources().getColor(R.color.graph4));
            diarrheaDataSet.setDrawValues(false);
            dataSet.add(diarrheaDataSet);
            indexDataSet++;
        }

        if (constipation != 0) {
            BarEntry constipationEntry = new BarEntry(indexDataSet, constipation);
            BarDataSet constipationDataSet = new BarDataSet(Collections.singletonList(constipationEntry), constipationName);
            constipationDataSet.setColor(getResources().getColor(R.color.graph5));
            constipationDataSet.setDrawValues(false);
            dataSet.add(constipationDataSet);
            indexDataSet++;
        }

        if (nausea != 0) {
            BarEntry nauseaEntry = new BarEntry(indexDataSet, nausea);
            BarDataSet nauseaDataSet = new BarDataSet(Collections.singletonList(nauseaEntry), nauseaName);
            nauseaDataSet.setColor(getResources().getColor(R.color.graph6));
            nauseaDataSet.setDrawValues(false);
            dataSet.add(nauseaDataSet);
            indexDataSet++;
        }

        if (tired != 0) {
            BarEntry tiredEntry = new BarEntry(indexDataSet, tired);
            BarDataSet tiredDataSet = new BarDataSet(Collections.singletonList(tiredEntry), tiredName);
            tiredDataSet.setColor(getResources().getColor(R.color.graph7));
            tiredDataSet.setDrawValues(false);
            dataSet.add(tiredDataSet);
        }

        BarData barData = new BarData(dataSet);
        mSymptomsChart.setData(barData);
        mSymptomsChart.invalidate();
    }

    /**
     * Setup the line chart that represented the pain over the last 7 days
     *
     * @param painList the fetched pain in locale DB with ViewModel
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
                mNavController.navigate(R.id.painDetailFragment);
                break;
            case R.id.dashboard_card_symptom:
                mNavController.navigate(R.id.symptomDetailFragment);
                break;
            case R.id.dashboard_card_action:
                mNavController.navigate(R.id.actionDetailFragment);
                break;
            case R.id.dashboard_card_sleep:
                break;
            case R.id.dashboard_card_mood:
                break;
        }
    }
}
