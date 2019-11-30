package com.huzzi.retrofitlivedata;

import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.huzzi.retrofitlivedata.model.LoginModel;
import com.huzzi.retrofitlivedata.state.ApiResponse;
import com.huzzi.retrofitlivedata.viewModels.RatingViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class ViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    ApiInterface apiClient;

    private RatingViewModel viewModel;
    private Lifecycle lifecycle;

    @Mock
    Observer<ApiResponse> observer;

    @Mock
    LifecycleOwner lifecycleOwner;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        viewModel = new RatingViewModel(apiClient, RxSingleSchedulers.TEST_SCHEDULER);
        viewModel.getResponse().observeForever(observer);
    }

    @Test
    public void testNull() {
        when(apiClient.rxData()).thenReturn(null);
        assertNotNull(viewModel.getResponse());
        assertTrue(viewModel.getResponse().hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        when(apiClient.rxData()).thenReturn(Single.just(new LoginModel()));
        viewModel.getData();
        verify(observer).onChanged(ApiResponse.LOADING_STATE);
        verify(observer).onChanged(ApiResponse.SUCCESS_STATE);
    }

    @Test
    public void testApiFetchDataEmpty() {
        when(apiClient.rxData()).thenReturn(Flowable.<LoginModel>empty().firstOrError());
        viewModel.getData();
        verify(observer).onChanged(ApiResponse.LOADING_STATE);
        verify(observer).onChanged(ApiResponse.ERROR_STATE);
    }

    @Test
    public void testApiFetchDataError() {
        when(apiClient.rxData()).thenReturn(Single.error(new Exception()));
        viewModel.getData();
        verify(observer).onChanged(ApiResponse.LOADING_STATE);
        verify(observer).onChanged(ApiResponse.ERROR_STATE);
    }

    @After
    public void tearDown() {
        apiClient = null;
        viewModel = null;
    }
}
