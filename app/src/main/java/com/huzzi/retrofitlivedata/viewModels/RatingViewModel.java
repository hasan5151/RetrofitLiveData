package com.huzzi.retrofitlivedata.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.huzzi.retrofitlivedata.ApiInterface;
import com.huzzi.retrofitlivedata.RxSingleSchedulers;
import com.huzzi.retrofitlivedata.constants.Status;
import com.huzzi.retrofitlivedata.state.ApiResponse;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class RatingViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private MutableLiveData<ApiResponse> response;
    private ApiInterface apiInterface;
    private RxSingleSchedulers rxSingleSchedulers;

    public RatingViewModel(ApiInterface apiInterface, RxSingleSchedulers rxSingleSchedulers){
        disposable = new CompositeDisposable();
        response = new MutableLiveData<>();
        this.apiInterface =apiInterface;
        this.rxSingleSchedulers=rxSingleSchedulers;
    }

    public MutableLiveData<ApiResponse> getResponse() {
             return response;
    }

    public void getData(){
         disposable.add(apiInterface.rxData()
                 .doOnEvent((responseBodyResponse, throwable) -> response.postValue(ApiResponse.LOADING_STATE))
                .compose(rxSingleSchedulers.applySchedulers())
                .subscribe(result->{
                    ApiResponse.SUCCESS_STATE.setData(result);
                    response.postValue(ApiResponse.SUCCESS_STATE);
                },error->{
                    ApiResponse.ERROR_STATE.setError(error);
                    response.postValue(ApiResponse.ERROR_STATE);
                }));
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
