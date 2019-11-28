package com.benlefevre.endometriosismonitoring.data.repositories;

import androidx.lifecycle.LiveData;

import com.benlefevre.endometriosismonitoring.data.dao.PainDao;
import com.benlefevre.endometriosismonitoring.models.Pain;

import java.util.Date;
import java.util.List;

public class PainRepository {

    private final PainDao mPainDao;

    public PainRepository(PainDao painDao) {
        mPainDao = painDao;
    }

    public long createPain(Pain pain) {
        return mPainDao.insertPain(pain);
    }

    public LiveData<List<Pain>> getPain(){
        return mPainDao.getAllPains();
    }

    public LiveData<List<Pain>> getPainByPeriod(Date begin, Date end){return mPainDao.getPainsByPeriod(begin, end);}
}

