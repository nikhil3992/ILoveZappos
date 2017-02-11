package com.example.nikhil.zapposapp;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Surya Prakasam on 07-02-2017.
 */

public interface ProductsAPI {

    @GET("/Search?")
    Call<ProductsPage> getProducts(@QueryMap Map<String,String> term);

}
