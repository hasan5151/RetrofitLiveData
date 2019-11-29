package com.huzzi.retrofitlivedata;

import androidx.lifecycle.LiveData;

import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ApiInterface {
        @GET("/monthlyReport/")
        LiveData<ApiResponse<ResponseBody>> liveData();

        @GET("monthlyReport/")
        Single<Response<ResponseBody>> rxData();

        @GET("monthlyReport/")
        Flowable<Response<ResponseBody>> flowableData();
}
