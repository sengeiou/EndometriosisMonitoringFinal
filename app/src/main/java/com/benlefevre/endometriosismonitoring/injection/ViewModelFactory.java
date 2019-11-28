package com.benlefevre.endometriosismonitoring.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.benlefevre.endometriosismonitoring.data.repositories.ActionRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.DoctorRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.FirestoreRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.MoodRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.PainRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.SymptomRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.TemperatureRepository;
import com.benlefevre.endometriosismonitoring.ui.viewmodels.SharedViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ActionRepository mActionRepository;
    private final DoctorRepository mDoctorRepository;
    private final FirestoreRepository mFirestoreRepository;
    private final MoodRepository mMoodRepository;
    private final PainRepository mPainRepository;
    private final SymptomRepository mSymptomRepository;
    private final TemperatureRepository mTemperatureRepository;
    private final Executor mExecutor;

    ViewModelFactory(ActionRepository actionRepository, DoctorRepository doctorRepository,
                     FirestoreRepository firestoreRepository, MoodRepository moodRepository,
                     PainRepository painRepository, SymptomRepository symptomRepository,
                     TemperatureRepository temperatureRepository, Executor executor) {
        mActionRepository = actionRepository;
        mDoctorRepository = doctorRepository;
        mFirestoreRepository = firestoreRepository;
        mMoodRepository = moodRepository;
        mPainRepository = painRepository;
        mSymptomRepository = symptomRepository;
        mTemperatureRepository = temperatureRepository;
        mExecutor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SharedViewModel.class))
        return (T) new SharedViewModel(mActionRepository,mDoctorRepository,mFirestoreRepository,
                mMoodRepository,mPainRepository,mSymptomRepository,mTemperatureRepository,mExecutor);
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
