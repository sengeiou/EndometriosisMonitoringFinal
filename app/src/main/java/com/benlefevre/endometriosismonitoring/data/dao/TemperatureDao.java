package com.benlefevre.endometriosismonitoring.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.benlefevre.endometriosismonitoring.models.Temperature;

import java.util.Date;
import java.util.List;

@Dao
public interface TemperatureDao {

    @Insert
    void insert(Temperature temperature);

    @Query("SELECT * FROM Temperature")
    LiveData<List<Temperature>> getAllTemp();

    @Query("SELECT * FROM Temperature WHERE mDate BETWEEN :begin AND :end")
    LiveData<List<Temperature>> getTempForPeriod(Date begin, Date end);

    @Query("DELETE FROM Temperature")
    void deleteAllTemp();
}
