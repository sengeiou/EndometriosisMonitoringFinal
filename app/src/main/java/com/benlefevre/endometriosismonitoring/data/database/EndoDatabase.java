package com.benlefevre.endometriosismonitoring.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.benlefevre.endometriosismonitoring.data.dao.ActionDao;
import com.benlefevre.endometriosismonitoring.data.dao.MoodDao;
import com.benlefevre.endometriosismonitoring.data.dao.PainDao;
import com.benlefevre.endometriosismonitoring.data.dao.SymptomDao;
import com.benlefevre.endometriosismonitoring.data.dao.TemperatureDao;
import com.benlefevre.endometriosismonitoring.models.Action;
import com.benlefevre.endometriosismonitoring.models.Mood;
import com.benlefevre.endometriosismonitoring.models.Pain;
import com.benlefevre.endometriosismonitoring.models.Symptom;
import com.benlefevre.endometriosismonitoring.models.Temperature;
import com.benlefevre.endometriosismonitoring.utils.Converters;

@Database(entities = {Pain.class, Symptom.class, Action.class, Mood.class, Temperature.class},version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class EndoDatabase extends RoomDatabase {

    private static volatile EndoDatabase INSTANCE;

    public abstract ActionDao mActionDao();
    public abstract MoodDao mMoodDao();
    public abstract PainDao mPainDao();
    public abstract SymptomDao mSymptomDao();
    public abstract TemperatureDao mTemperatureDao();

    public static EndoDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (EndoDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),EndoDatabase.class,
                            "Endo.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
