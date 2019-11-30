package com.huzzi.retrofitlivedata.viewModels;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.huzzi.retrofitlivedata.ApiInterface;
import com.huzzi.retrofitlivedata.RxFlowableSchedulers;
import com.huzzi.retrofitlivedata.RxSingleSchedulers;

public class CustomViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private ApiInterface apiInterface;
    private RxFlowableSchedulers rxFlowableSchedulers;

    public CustomViewModelFactory(ApiInterface apiInterface, RxFlowableSchedulers rxFlowableSchedulers){
        this.apiInterface=apiInterface;
        this.rxFlowableSchedulers= rxFlowableSchedulers;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RatingViewModel(apiInterface,rxFlowableSchedulers);
    }
}
