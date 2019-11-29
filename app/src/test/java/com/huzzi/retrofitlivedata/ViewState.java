package com.huzzi.retrofitlivedata;

import org.reactivestreams.Subscription;

class ViewState<T> {
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
