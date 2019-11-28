package com.benlefevre.endometriosismonitoring.data.repositories;

import androidx.lifecycle.LiveData;

import com.benlefevre.endometriosismonitoring.data.dao.SymptomDao;
import com.benlefevre.endometriosismonitoring.models.Symptom;

import java.util.List;

public class SymptomRepository {

    private final SymptomDao mSymptomDao;


    public SymptomRepository(SymptomDao symptomDao) {
        mSymptomDao = symptomDao;
    }

    public void createSymptoms(List<Symptom> symptoms){
        mSymptomDao.insertAll(symptoms);
    }

    public LiveData<List<Symptom>> getAllSymptoms(){return mSymptomDao.getAllSymptoms();}

    public LiveData<List<Symptom>> getPainSymptoms(long painId){return mSymptomDao.getPainSymptoms(painId);}
}
