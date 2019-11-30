package com.huzzi.retrofitlivedata;

import androidx.lifecycle.LiveData;

import com.huzzi.retrofitlivedata.model.LoginModel;
import com.huzzi.retrofitlivedata.state.ApiResponse;

import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ApiInterface {
        @GET("/monthlyReport/")
        LiveData<ApiResponse<ResponseBody>> liveData();

        @GET("monthlyReport/")
        Single<LoginModel> rxData();

        @GET("monthlyReport/")
        Flowable<Response<ResponseBody>> flowableData();
}
