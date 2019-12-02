package com.benlefevre.endometriosismonitoring;

import androidx.lifecycle.MutableLiveData;

import com.benlefevre.endometriosismonitoring.data.dao.SymptomDao;
import com.benlefevre.endometriosismonitoring.data.repositories.SymptomRepository;
import com.benlefevre.endometriosismonitoring.models.Symptom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SymptomRepositorytest {

    private SymptomDao mSymptomDao = Mockito.mock(SymptomDao.class);
    private SymptomRepository mSymptomRepository = new SymptomRepository(mSymptomDao);
    private Date mDate = new Date();

    @Test
    public void symptomRepositoryGetTest(){
        MutableLiveData<List<Symptom>> symptomLiveData = new MutableLiveData<>(Arrays.asList(new Symptom(0,"fever",mDate),new Symptom(1,"cramps",mDate)));
        Mockito.when(mSymptomDao.getAllSymptoms()).thenReturn(symptomLiveData);

        assertEquals(2,mSymptomRepository.getAllSymptoms().getValue().size());

        assertEquals(0,mSymptomRepository.getAllSymptoms().getValue().get(0).getPainId());
        assertEquals("fever",mSymptomRepository.getAllSymptoms().getValue().get(0).getName());
        assertEquals(mDate,mSymptomRepository.getAllSymptoms().getValue().get(0).getDate());

        assertEquals(1,mSymptomRepository.getAllSymptoms().getValue().get(1).getPainId());
        assertEquals("cramps",mSymptomRepository.getAllSymptoms().getValue().get(1).getName());
        assertEquals(mDate,mSymptomRepository.getAllSymptoms().getValue().get(1).getDate());

        Mockito.when(mSymptomDao.getPainSymptoms(0)).thenReturn(symptomLiveData);

        assertEquals(symptomLiveData,mSymptomRepository.getPainSymptoms(0));
    }

    @Test
    public void symptomRepositoryInsertTest(){
        List<Symptom> symptomList = Arrays.asList(new Symptom(0,"bleeding",mDate));
        mSymptomRepository.createSymptoms(symptomList);
        Mockito.verify(mSymptomDao).insertAll(symptomList);
    }
}
