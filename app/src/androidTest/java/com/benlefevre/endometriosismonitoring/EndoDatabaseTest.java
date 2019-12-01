package com.benlefevre.endometriosismonitoring;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.benlefevre.endometriosismonitoring.data.database.EndoDatabase;
import com.benlefevre.endometriosismonitoring.models.Action;
import com.benlefevre.endometriosismonitoring.models.Mood;
import com.benlefevre.endometriosismonitoring.models.Pain;
import com.benlefevre.endometriosismonitoring.models.Symptom;
import com.benlefevre.endometriosismonitoring.models.Temperature;
import com.benlefevre.endometriosismonitoring.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class EndoDatabaseTest {

    private EndoDatabase mEndoDatabase;
    private static Date mDate = new Date();

    private static Pain painDemo = new Pain(mDate,5,"Bladder");
    private static Symptom symptomDemo = new Symptom(0,"Fever");
    private static Mood moodDemo = new Mood(0,"Happy");
    private static Action actionDemo = new Action(0,"Sleep",120,8);
    private static Temperature tempDemo = new Temperature(36.8,mDate);

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb(){
        mEndoDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                EndoDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb(){
        mEndoDatabase.close();
    }

    @Test
    public void insertAndGetPain() throws InterruptedException{
        long row = mEndoDatabase.mPainDao().insertPain(painDemo);
        symptomDemo.setPainId(row);
        moodDemo.setPainId(row);
        actionDemo.setPainId(row);
        mEndoDatabase.mSymptomDao().insertAll(Collections.singletonList(symptomDemo));
        mEndoDatabase.mMoodDao().insert(moodDemo);
        mEndoDatabase.mActionDao().insertAll(Collections.singletonList(actionDemo));
        mEndoDatabase.mTemperatureDao().insert(tempDemo);


        List<Pain> painList = LiveDataTestUtil.getValue(mEndoDatabase.mPainDao().getAllPains());
        Pain pain = painList.get(0);
        assertEquals(painDemo.getDate(),pain.getDate());
        assertEquals(painDemo.getIntensity(),pain.getIntensity());
        assertEquals(painDemo.getLocation(),pain.getLocation());

        List<Symptom> symptomList = LiveDataTestUtil.getValue(mEndoDatabase.mSymptomDao().getPainSymptoms(row));
        Symptom symptom = symptomList.get(0);
        assertEquals(row,symptom.getPainId());
        assertEquals(symptomDemo.getName(),symptom.getName());

        Mood mood = LiveDataTestUtil.getValue(mEndoDatabase.mMoodDao().getPainMood(row));
        assertEquals(row,mood.getPainId());
        assertEquals(moodDemo.getValue(),mood.getValue());

        List<Action> actionList = LiveDataTestUtil.getValue(mEndoDatabase.mActionDao().getPainActions(row));
        Action action = actionList.get(0);
        assertEquals(row,action.getPainId());
        assertEquals(actionDemo.getName(),action.getName());
        assertEquals(actionDemo.getDuration(),action.getDuration());
        assertEquals(actionDemo.getIntensity(),action.getIntensity());

        List<Temperature> temperatureList = LiveDataTestUtil.getValue(mEndoDatabase.mTemperatureDao().getAllTemp());
        Temperature temperature = temperatureList.get(0);
        assertEquals(tempDemo.getValue(),temperature.getValue());
        assertEquals(tempDemo.getDate(),temperature.getDate());
    }

}
