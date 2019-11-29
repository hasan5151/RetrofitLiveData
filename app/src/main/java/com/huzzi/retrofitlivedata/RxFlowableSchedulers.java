package com.huzzi.retrofitlivedata;

import io.reactivex.FlowableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface RxFlowableSchedulers {

    RxFlowableSchedulers DEFAULT = new RxFlowableSchedulers() {
        @Override
        public <T> FlowableTransformer<T, T> applyFlowableSchedulers() {
            return single -> single
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    RxFlowableSchedulers TEST = new RxFlowableSchedulers() {
        @Override
        public <T> FlowableTransformer<T, T> applyFlowableSchedulers() {
            return single -> single
                    .subscribeOn(Schedulers.trampoline())
                    .observeOn(Schedulers.trampoline());
        }
    };


    <T> FlowableTransformer<T, T> applyFlowableSchedulers();


}
