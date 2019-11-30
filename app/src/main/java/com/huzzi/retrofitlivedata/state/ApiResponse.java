package com.huzzi.retrofitlivedata.state;


import com.huzzi.retrofitlivedata.constants.Status;

public class ApiResponse<T> {

    private Status status;
    private T data;
    private Throwable error;

    private ApiResponse(T data, Status status, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse ERROR_STATE = new ApiResponse(null, Status.ERROR, new Throwable());
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
}
