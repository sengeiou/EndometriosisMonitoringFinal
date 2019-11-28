package com.benlefevre.endometriosismonitoring.api;

import com.benlefevre.endometriosismonitoring.models.Result;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface CpamService {

    @GET("api/records/1.0/search/")
    Call<Result> getDoctors(@QueryMap Map<String, String> map);
}
