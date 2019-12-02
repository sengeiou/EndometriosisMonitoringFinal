package com.benlefevre.endometriosismonitoring;

import androidx.lifecycle.MutableLiveData;

import com.benlefevre.endometriosismonitoring.data.dao.MoodDao;
import com.benlefevre.endometriosismonitoring.data.repositories.MoodRepository;
import com.benlefevre.endometriosismonitoring.models.Mood;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MoodRepositorytest {

    private MoodDao mMoodDao = Mockito.mock(MoodDao.class);
    private MoodRepository mMoodRepository = new MoodRepository(mMoodDao);


    @Test
    public void moodrepositoryGetTest(){
        MutableLiveData<Mood> moodLiveData = new MutableLiveData<>(new Mood(0,"happy"));
        Mockito.when(mMoodDao.getPainMood(0)).thenReturn(moodLiveData);
        assertEquals(0,mMoodRepository.getPainMood(0).getValue().getId());
        assertEquals("happy",mMoodRepository.getPainMood(0).getValue().getValue());

        MutableLiveData<List<Mood>> moodsLiveData = new MutableLiveData<>(Arrays.asList(new Mood(0,"happy"),new Mood(1,"sad")));
        Mockito.when(mMoodDao.getAllMood()).thenReturn(moodsLiveData);
        assertEquals(2,mMoodRepository.getAllMood().getValue().size());
        assertEquals(0,mMoodRepository.getAllMood().getValue().get(0).getPainId());
        assertEquals("happy",mMoodRepository.getAllMood().getValue().get(0).getValue());
        assertEquals(1,mMoodRepository.getAllMood().getValue().get(1).getPainId());
        assertEquals("sad",mMoodRepository.getAllMood().getValue().get(1).getValue());
    }

    @Test
    public void moodRepositoryInsertTest(){
        Mood mood = new Mood(0,"happy");
        mMoodRepository.createMood(mood);
        Mockito.verify(mMoodDao).insert(mood);
    }
}
