package com.huzzi.retrofitlivedata;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.Observer;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.apache.tools.ant.Main;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.annotation.Config;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@Config( sdk=28)
public class ExampleUnitTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        ViewState<Response<ResponseBody>> tes =  new ViewState<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://redpetroleum.herokuapp.com/index.php/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.rxData().compose(RxSingleSchedulers.TEST_SCHEDULER.applySchedulers()).subscribe(res->{
            System.out.println("res."+ res.isSuccessful());
        });

        apiInterface.rxData().subscribe(responseBodyResponse ->
                System.out.println("Sess3"+ responseBodyResponse.isSuccessful()));

        apiInterface.flowableData().compose(RxFlowableSchedulers.TEST.applyFlowableSchedulers()).subscribe(tes::onSuccess,tes::onError);


     }




}