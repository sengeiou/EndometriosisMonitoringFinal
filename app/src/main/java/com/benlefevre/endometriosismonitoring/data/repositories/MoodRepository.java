package com.benlefevre.endometriosismonitoring.data.repositories;

import androidx.lifecycle.LiveData;

import com.benlefevre.endometriosismonitoring.data.dao.MoodDao;
import com.benlefevre.endometriosismonitoring.models.Mood;

import java.util.List;

public class MoodRepository {

    private final MoodDao mMoodDao;

    public MoodRepository(MoodDao moodDao) {
        mMoodDao = moodDao;
    }

    public void createMood(Mood mood){mMoodDao.insert(mood);}

    public LiveData<List<Mood>> getAllMood(){return mMoodDao.getAllMood();}

    public LiveData<Mood> getPainMood(long painId){return mMoodDao.getPainMood(painId);}
}
