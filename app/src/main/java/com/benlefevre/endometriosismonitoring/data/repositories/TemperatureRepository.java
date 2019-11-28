package com.benlefevre.endometriosismonitoring.data.repositories;

import androidx.lifecycle.LiveData;

import com.benlefevre.endometriosismonitoring.data.dao.TemperatureDao;
import com.benlefevre.endometriosismonitoring.models.Temperature;

import java.util.Date;
import java.util.List;

public class TemperatureRepository {

    private TemperatureDao mTemperatureDao;

    public TemperatureRepository(TemperatureDao temperatureDao) {
        mTemperatureDao = temperatureDao;
    }

    public void createTemp(Temperature temperature) {
        mTemperatureDao.insert(temperature);
    }

    public LiveData<List<Temperature>> getAllTemp() {
        return mTemperatureDao.getAllTemp();
    }

    public LiveData<List<Temperature>> getTempForPeriod(Date begin, Date end) {
        return mTemperatureDao.getTempForPeriod(begin, end);
    }
}
