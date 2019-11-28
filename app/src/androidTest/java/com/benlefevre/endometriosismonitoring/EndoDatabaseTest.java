package com.benlefevre.endometriosismonitoring;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.benlefevre.endometriosismonitoring.data.database.EndoDatabase;
import com.benlefevre.endometriosismonitoring.models.Pain;
import com.benlefevre.endometriosismonitoring.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class EndoDatabaseTest {

    private EndoDatabase mEndoDatabase;
    private static Date mDate = new Date();

    private static Pain painDemo = new Pain(mDate,5,"Bladder");

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
        mEndoDatabase.mPainDao().insertPain(painDemo);

        List<Pain> painList = LiveDataTestUtil.getValue(mEndoDatabase.mPainDao().getAllPains());
        Pain pain = painList.get(0);
        assertEquals(painDemo.getDate(),pain.getDate());
        assertEquals(painDemo.getIntensity(),pain.getIntensity());
        assertEquals(painDemo.getLocation(),pain.getLocation());
    }

}
