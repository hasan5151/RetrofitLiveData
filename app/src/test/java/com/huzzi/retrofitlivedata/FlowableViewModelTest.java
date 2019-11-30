package com.huzzi.retrofitlivedata;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.huzzi.retrofitlivedata.model.LoginModel;
import com.huzzi.retrofitlivedata.state.ApiResponse;
import com.huzzi.retrofitlivedata.viewModels.RatingViewModel;

import org.checkerframework.checker.units.qual.A;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FlowableViewModelTest {
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
        viewModel = new RatingViewModel(apiClient, RxFlowableSchedulers.TEST);
        viewModel.getFlowableResponse().observeForever(observer);
    }

    @Test
    public void testNull(){
        when(apiClient.rxData()).thenReturn(null);
        assertNotNull(viewModel.getFlowableResponse());
        assertTrue(viewModel.getFlowableResponse().hasObservers());
    }


    @Test
    public void testApiFetchDataSuccess() {
        when(apiClient.flowableData()).thenReturn(Flowable.just(new LoginModel()));
        viewModel.getFlowableData();
        verify(observer).onChanged(ApiResponse.LOADING_STATE);
        verify(observer).onChanged(ApiResponse.SUCCESS_STATE);
        verify(observer,times(1)).onChanged(ApiResponse.LOADING_STATE);
    }

    @Test
    public void testApiFetchDataEmpty() {
        when(apiClient.flowableData()).thenReturn(Flowable.empty());
        viewModel.getFlowableData();
        verify(observer).onChanged(ApiResponse.LOADING_STATE);
//        verify(observer).onChanged(ApiResponse.ERROR_STATE);

    }

    @Test
    public void testApiFetchDataError() {
        when(apiClient.flowableData()).thenReturn(Flowable.error(new Exception()));
        viewModel.getFlowableData();
        verify(observer).onChanged(ApiResponse.LOADING_STATE);
        verify(observer).onChanged(ApiResponse.ERROR_STATE);
    }

    @After
    public void tearDown() {
        apiClient = null;
        viewModel = null;
    }
}
