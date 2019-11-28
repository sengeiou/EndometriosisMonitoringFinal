package com.benlefevre.endometriosismonitoring.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.benlefevre.endometriosismonitoring.models.Pain;

import java.util.Date;
import java.util.List;

@Dao
public interface PainDao {

    @Query("SELECT * FROM Pain")
    LiveData<List<Pain>> getAllPains();

    @Query("SELECT * FROM Pain WHERE date BETWEEN :begin AND :end ORDER BY id DESC")
    LiveData<List<Pain>> get7LastPain(Date begin, Date end);

    @Query("SELECT * FROM Pain WHERE date BETWEEN :dateBegin AND :dateEnd")
    LiveData<List<Pain>> getPainsByDate(long dateBegin, long dateEnd);

    @Insert
    long insertPain(Pain pain);

    @Update
    int updatePain(Pain pain);
}
