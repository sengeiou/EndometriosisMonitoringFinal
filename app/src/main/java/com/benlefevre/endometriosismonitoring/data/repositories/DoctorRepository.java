package com.benlefevre.endometriosismonitoring.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.benlefevre.endometriosismonitoring.api.CpamService;
import com.benlefevre.endometriosismonitoring.api.RetrofitService;
import com.benlefevre.endometriosismonitoring.models.Result;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorRepository {

    private static DoctorRepository sDoctorRepository;
    private CpamService mCpamService;

//    Singleton Pattern
    public static DoctorRepository getInstance(){
        if (sDoctorRepository == null){
            sDoctorRepository = new DoctorRepository();
        }
        return sDoctorRepository;
    }

    private DoctorRepository(){
        mCpamService = RetrofitService.createService(CpamService.class);
    }

//    Fetch the doctor information with the API and return them into a LiveData
    public MutableLiveData<Result> getDoctor(Map<String, String> map){
        MutableLiveData<Result> resultLiveData = new MutableLiveData<>();
        mCpamService.getDoctors(map).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NotNull Call<Result> call, @NotNull Response<Result> response) {
                if (response.isSuccessful())
                    resultLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<Result> call, @NotNull Throwable t) {
                resultLiveData.setValue(null);
            }
        });
        return resultLiveData;
    }
}
