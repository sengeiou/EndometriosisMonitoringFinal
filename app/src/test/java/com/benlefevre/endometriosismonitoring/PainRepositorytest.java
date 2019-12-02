package com.benlefevre.endometriosismonitoring;

import androidx.lifecycle.MutableLiveData;

import com.benlefevre.endometriosismonitoring.data.dao.PainDao;
import com.benlefevre.endometriosismonitoring.data.repositories.PainRepository;
import com.benlefevre.endometriosismonitoring.models.Pain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.google.common.base.CharMatcher.any;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class PainRepositorytest {

    private PainDao mPainDao = Mockito.mock(PainDao.class);
    private PainRepository mPainRepository = new PainRepository(mPainDao);
    private Date mDate = new Date();

    @Test
    public void painRepositoryGetTest(){
        MutableLiveData<List<Pain>> painsLiveData = new MutableLiveData<>(Arrays.asList(new Pain(mDate,5,"bladder"),new Pain(mDate,10,"head")));
        Mockito.when(mPainDao.getAllPains()).thenReturn(painsLiveData);

        assertEquals(2,mPainRepository.getPain().getValue().size());

        assertEquals(mDate,mPainRepository.getPain().getValue().get(0).getDate());
        assertEquals(5,mPainRepository.getPain().getValue().get(0).getIntensity());
        assertEquals("bladder",mPainRepository.getPain().getValue().get(0).getLocation());

        assertEquals(mDate,mPainRepository.getPain().getValue().get(1).getDate());
        assertEquals(10,mPainRepository.getPain().getValue().get(1).getIntensity());
        assertEquals("head",mPainRepository.getPain().getValue().get(1).getLocation());

        Mockito.when(mPainDao.getPainsByPeriod(mDate,mDate)).thenReturn(painsLiveData);

        assertEquals(painsLiveData,mPainRepository.getPainByPeriod(mDate,mDate));
    }

    @Test
    public void setPainRepositoryInsertTest(){
        Pain pain = new Pain();
        mPainRepository.createPain(pain);
        Mockito.verify(mPainDao).insertPain(pain);
    }
}
