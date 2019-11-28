package com.benlefevre.endometriosismonitoring.injection;

import android.content.Context;

import com.benlefevre.endometriosismonitoring.data.database.EndoDatabase;
import com.benlefevre.endometriosismonitoring.data.repositories.ActionRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.DoctorRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.FirestoreRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.MoodRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.PainRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.SymptomRepository;
import com.benlefevre.endometriosismonitoring.data.repositories.TemperatureRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    private static ActionRepository provideActionDataSource(Context context){
        EndoDatabase database = EndoDatabase.getInstance(context);
        return new ActionRepository(database.mActionDao());
    }

    private static MoodRepository provideMoodDataSource(Context context){
        EndoDatabase database = EndoDatabase.getInstance(context);
        return new MoodRepository(database.mMoodDao());
    }

    private static PainRepository providePainDataSource(Context context){
        EndoDatabase database = EndoDatabase.getInstance(context);
        return new PainRepository(database.mPainDao());
    }

    private static SymptomRepository provideSymptomDataSource(Context context){
        EndoDatabase database =EndoDatabase.getInstance(context);
        return new SymptomRepository(database.mSymptomDao());
    }

    private static TemperatureRepository provideTemperatureDataSource(Context context){
        EndoDatabase database = EndoDatabase.getInstance(context);
        return new TemperatureRepository(database.mTemperatureDao());
    }

    private static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory providerViewModelFactory(Context context){
        ActionRepository actionRepository = provideActionDataSource(context);
        DoctorRepository doctorRepository = DoctorRepository.getInstance();
        FirestoreRepository firestoreRepository = FirestoreRepository.getInstance();
        MoodRepository moodRepository = provideMoodDataSource(context);
        PainRepository painRepository = providePainDataSource(context);
        SymptomRepository symptomRepository = provideSymptomDataSource(context);
        TemperatureRepository temperatureRepository = provideTemperatureDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(actionRepository,doctorRepository,firestoreRepository,moodRepository,
                painRepository,symptomRepository,temperatureRepository,executor);
    }
}
