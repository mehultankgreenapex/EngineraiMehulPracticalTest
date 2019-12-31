package com.example.myapplication.webservices;

import com.example.myapplication.model.PostListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface UsersService {


    @GET("search_by_date")
    Observable<PostListResponse> getPostList(@Query("tags") String tags,
                                             @Query("page") String page);


}