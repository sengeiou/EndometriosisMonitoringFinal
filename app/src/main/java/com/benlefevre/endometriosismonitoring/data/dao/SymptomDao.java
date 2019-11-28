package com.benlefevre.endometriosismonitoring.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.benlefevre.endometriosismonitoring.models.Symptom;

import java.util.List;

@Dao
public interface SymptomDao {

    @Insert
    void insertAll(List<Symptom> symptoms);

    @Query("SELECT * FROM Symptom")
    LiveData<List<Symptom>> getAllSymptoms();

    @Query("SELECT * FROM Symptom WHERE painId = :painId")
    LiveData<List<Symptom>> getPainSymptoms(long painId);
}
