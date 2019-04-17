package com.code.cars.controller;

import com.code.cars.model.CarsOnline;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CarsApi {

    @GET("carsonline")
    Call<CarsOnline> getCarsInfo();
}
