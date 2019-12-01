package com.huzzi.retrofitlivedata.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.huzzi.retrofitlivedata.ApiInterface;
import com.huzzi.retrofitlivedata.RxFlowableSchedulers;
import com.huzzi.retrofitlivedata.RxSingleSchedulers;
import com.huzzi.retrofitlivedata.constants.Status;
import com.huzzi.retrofitlivedata.model.LoginModel;
import com.huzzi.retrofitlivedata.state.ApiResponse;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class RatingViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private MutableLiveData<ApiResponse> response;
    private MutableLiveData<ApiResponse<List<LoginModel>>> flowableResponse;
    private ApiInterface apiInterface;
    private RxSingleSchedulers rxSingleSchedulers;
    private RxFlowableSchedulers rxFlowableSchedulers;

    public RatingViewModel(ApiInterface apiInterface, RxSingleSchedulers rxSingleSchedulers){
        disposable = new CompositeDisposable();
        response = new MutableLiveData<>();
        this.apiInterface =apiInterface;
        this.rxSingleSchedulers=rxSingleSchedulers;
    }
    public RatingViewModel(ApiInterface apiInterface, RxFlowableSchedulers rxFlowableSchedulers){
        disposable = new CompositeDisposable();
        flowableResponse = new MutableLiveData<>();
        this.apiInterface =apiInterface;
        this.rxFlowableSchedulers=rxFlowableSchedulers;
    }

    public MutableLiveData<ApiResponse> getResponse() {
        return response;
    }

    public MutableLiveData<ApiResponse<List<LoginModel>>> getFlowableResponse() {
        return flowableResponse;
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

    public void getFlowableData(){
         disposable.add(apiInterface.flowableData()
                 .doOnSubscribe(subscription -> flowableResponse.postValue(ApiResponse.LOADING_STATE))
//                 .compose(rxFlowableSchedulers.applyFlowableSchedulers())
                 .compose(rxFlowableSchedulers.applyFlowableSchedulers())
                .subscribe(result->{
                    ApiResponse.SUCCESS_STATE.setData(result);
                    flowableResponse.postValue(ApiResponse.SUCCESS_STATE);
                },error->{
//                    ApiResponse.ERROR_STATE.setError(error);
//                    flowableResponse.postValue(ApiResponse.ERROR_STATE);
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
