package com.benlefevre.endometriosismonitoring.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.benlefevre.endometriosismonitoring.models.Mood;

import java.util.List;

@Dao
public interface MoodDao {

    @Insert
    void insert(Mood mood);

    @Query("SELECT * FROM Mood")
    LiveData<List<Mood>> getAllMood();

    @Query("SELECT * FROM Mood WHERE painId = :painId")
    LiveData<Mood> getPainMood(long painId);
}
