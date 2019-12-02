package com.benlefevre.endometriosismonitoring;

import androidx.lifecycle.MutableLiveData;

import com.benlefevre.endometriosismonitoring.data.dao.ActionDao;
import com.benlefevre.endometriosismonitoring.data.repositories.ActionRepository;
import com.benlefevre.endometriosismonitoring.models.Action;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(JUnit4.class)
public class ActionRepositoryTest {

    private ActionDao mActionDao = Mockito.mock(ActionDao.class);
    private ActionRepository mActionRepository = new ActionRepository(mActionDao);
    private Date mDate = new Date();

    @Test
    public void actionRepositoryGetTest(){
        MutableLiveData<List<Action>> actionLiveData = new MutableLiveData<>(Collections.singletonList(new Action(0,"Sleep",60,5,5,mDate)));
        Mockito.when(mActionDao.getAllActions()).thenReturn(actionLiveData);
        assertEquals(actionLiveData,mActionRepository.getAllActions());
        assertEquals(0,mActionRepository.getAllActions().getValue().get(0).getPainId());
        assertEquals("Sleep",mActionRepository.getAllActions().getValue().get(0).getName());
        assertEquals(60,mActionRepository.getAllActions().getValue().get(0).getDuration());
        assertEquals(5,mActionRepository.getAllActions().getValue().get(0).getIntensity());
        assertEquals(5,mActionRepository.getAllActions().getValue().get(0).getPainValue());
        assertEquals(mDate,mActionRepository.getAllActions().getValue().get(0).getDate());

        MutableLiveData<List<Action>> actionPainLiveData = new MutableLiveData<>(Collections.singletonList(new Action(1,"Running",30,2,1,mDate)));
        Mockito.when(mActionDao.getPainActions(1)).thenReturn(actionPainLiveData);
        assertEquals(actionPainLiveData,mActionRepository.getPainActions(1));
        assertEquals(1,mActionRepository.getPainActions(1).getValue().get(0).getPainId());
        assertEquals("Running",mActionRepository.getPainActions(1).getValue().get(0).getName());
        assertEquals(30,mActionRepository.getPainActions(1).getValue().get(0).getDuration());
        assertEquals(2,mActionRepository.getPainActions(1).getValue().get(0).getIntensity());
        assertEquals(1,mActionRepository.getPainActions(1).getValue().get(0).getPainValue());
        assertEquals(mDate,mActionRepository.getPainActions(1).getValue().get(0).getDate());
    }

    @Test
    public void actionRepositoryInsertTest(){
        List<Action> actions = Arrays.asList(new Action());
        mActionRepository.createActions(actions);
        Mockito.verify(mActionDao).insertAll(actions);
    }
}
