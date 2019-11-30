package com.huzzi.retrofitlivedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.huzzi.retrofitlivedata.model.LoginModel;
import com.huzzi.retrofitlivedata.viewModels.CustomViewModelFactory;
import com.huzzi.retrofitlivedata.viewModels.RatingViewModel;

import java.util.List;

import io.reactivex.SingleTransformer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RatingViewModel ratingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://redpetroleum.herokuapp.com/index.php/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                 .build();


        CustomViewModelFactory customViewModelFactory = new CustomViewModelFactory(retrofit.create(ApiInterface.class),RxFlowableSchedulers.DEFAULT);
        ratingViewModel = ViewModelProviders.of(this,customViewModelFactory).get(RatingViewModel.class);

        ratingViewModel.getFlowableData();
        ratingViewModel.getFlowableResponse().observe(this,result->{
            switch (result.getStatus()){
                case ERROR:
                    System.out.println("on Error");
                    result.getError().printStackTrace();
                    break;

                case SUCCESS:
                    System.out.println("on Success");
                    List<LoginModel> loginModels = result.getData();
                    System.out.println("on Success"+loginModels.size());
                    System.out.println("on Success"+loginModels.get(0).getOpId());
                    break;

                case LOADING:
                    System.out.println("on Loading");
                    break;
            }
        });
    }
}
