package com.benlefevre.endometriosismonitoring.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.benlefevre.endometriosismonitoring.models.Action;

import java.util.Date;
import java.util.List;

@Dao
public interface ActionDao {

    @Insert
    void insertAll(List<Action> actions);

    @Query("SELECT * FROM Action")
    LiveData<List<Action>> getAllActions();

    @Query("SELECT * FROM Action WHERE painId = :painId")
    LiveData<List<Action>> getPainActions(long painId);

    @Query("SELECT * FROM Action WHERE date BETWEEN :begin AND :end")
    LiveData<List<Action>> getActionsByPeriod(Date begin, Date end);
}
