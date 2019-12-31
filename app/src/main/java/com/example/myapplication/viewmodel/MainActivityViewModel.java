package com.example.myapplication.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.myapplication.MyApplication;
import com.example.myapplication.webservices.UsersService;

import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class MainActivityViewModel extends Observable {


    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MainActivityNavigator mainActivityNavigator;


    public MainActivityViewModel(@NonNull Context context) {

        mainActivityNavigator = (MainActivityNavigator) context;

    }


    public void getPostList(String tags, String page) {


        MyApplication appController = MyApplication.getmInstance();
        UsersService usersService = appController.getUserService();


        Disposable disposable = usersService.getPostList(tags, page)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postList -> mainActivityNavigator.postListResponse(postList),
                        throwable -> mainActivityNavigator.handleError(throwable));

        compositeDisposable.add(disposable);
    }


    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;

    }

}