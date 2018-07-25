package com.example.after.alittletwo.util;

import android.content.Intent;

import com.example.after.alittletwo.entity.ResponseContent;
import com.example.after.alittletwo.entity.Translation;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by hongjian.chen on 2018/6/12.
 */

public interface PostRequest_Interface {
    @POST("heads/upload")
    @Multipart
    Call<Translation> upload(@Part("uid") RequestBody uid, @Part MultipartBody.Part file);

    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

    @GET("spfile/hottest/p{page}.json")
    Call<ResponseContent> getHottest(@Path("page") Integer page);

    @GET("spfile/newest/p{page}.json")
    Call<ResponseBody> getNewest(@Path("page") Integer page);
}
