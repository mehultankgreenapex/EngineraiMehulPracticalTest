package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.webservices.UsersService;
import com.example.myapplication.webservices.WsConstants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {


    private static MyApplication mInstance;


    private UsersService usersService;
    private Scheduler scheduler;


    public static MyApplication getmInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

    }

    /**
     * Call when application is close
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mInstance != null) {
            mInstance = null;
        }

    }


    public UsersService getUserService() {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(interceptor);
        okHttpClient.connectTimeout(100, TimeUnit.SECONDS);
        okHttpClient.readTimeout(100, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(100, TimeUnit.SECONDS);
        okHttpClient.retryOnConnectionFailure(true);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WsConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient.build())
                .build();

        return usersService = retrofit.create(UsersService.class);


    }


    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

}