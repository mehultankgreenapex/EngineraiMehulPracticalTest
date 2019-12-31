package com.example.myapplication.viewmodel;

import com.example.myapplication.model.PostListResponse;

public interface MainActivityNavigator {


    /**
     *  error handling
     * @param throwable
     */
    void handleError(Throwable throwable);


    /**
     *
     * post list response
     *
     * @param postList
     */
    void postListResponse(PostListResponse postList);

}