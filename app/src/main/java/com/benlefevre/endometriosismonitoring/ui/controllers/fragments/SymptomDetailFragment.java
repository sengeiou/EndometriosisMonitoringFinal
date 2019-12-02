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
import com.benlefevre.endometriosismonitoring.models.Symptom;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
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
        ButterKnife.bind(this,view);
        mActivity = getActivity();
        mDateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        configureViewModel();
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.providerViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity, viewModelFactory).get(SharedViewModel.class);
    }
}
