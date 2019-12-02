package com.benlefevre.endometriosismonitoring.data.repositories;

import androidx.lifecycle.LiveData;

import com.benlefevre.endometriosismonitoring.data.dao.ActionDao;
import com.benlefevre.endometriosismonitoring.models.Action;

import java.util.Date;
import java.util.List;

public class ActionRepository {

    private final ActionDao mActionDao;


    public ActionRepository(ActionDao actionDao) {
        mActionDao = actionDao;
    }

    public void createActions(List<Action> actions){mActionDao.insertAll(actions);}

    public LiveData<List<Action>> getAllActions(){return mActionDao.getAllActions();}

    public LiveData<List<Action>> getPainActions(long painId){return mActionDao.getPainActions(painId);}

    public LiveData<List<Action>> getActionsByPeriod(Date begin, Date end){ return mActionDao.getActionsByPeriod(begin, end);}
}
