package com.benlefevre.endometriosismonitoring.ui.controllers.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benlefevre.endometriosismonitoring.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FertilityFragment extends Fragment {


    public FertilityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fertility, container, false);
    }

}
