package com.huzzi.retrofitlivedata.state;

import org.reactivestreams.Subscription;

public class ViewState<T> {
    void onSuccess(T responseBodyResponse) {
        System.out.println("r" + responseBodyResponse);
    }

    private void onLoading() {

    }

    public void onError(Throwable throwable) {
    }

    public void onLoad(Subscription subscription) {
    }


}
