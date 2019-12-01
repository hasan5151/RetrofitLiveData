package com.huzzi.retrofitlivedata.state;


import androidx.annotation.Nullable;

import com.huzzi.retrofitlivedata.constants.Status;

public class ApiResponse<T> {

    public Status status;
    public T data;
    public Throwable error;

    public ApiResponse(T data, Status status, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }
    public ApiResponse( Status status, Throwable error) {
        this.status = status;
        this.error = error;
    }

    public static ApiResponse ERROR_STATE = new ApiResponse( Status.ERROR,new Throwable());
    public static ApiResponse LOADING_STATE = new ApiResponse(null, Status.LOADING, null);
    public static ApiResponse SUCCESS_STATE = new ApiResponse(null, Status.SUCCESS, null);


    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public void setError(Object error) {
        this.error = (Throwable) error;
    }
}
