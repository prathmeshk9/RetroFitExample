package com.example.admin.retrofitexample;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
 
/**
 * Created by Belal on 11/5/2015.
 */
public interface RegisterAPI {

    @FormUrlEncoded
    @POST("/RetrofitExample/insert.php")
    public void insertUser(
            @Field("name") String name,
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email,
            Callback<Response> callback);

    @GET("/RetrofitExample/getUser.php")
    public void getUser(Callback<Response> callback);

    @Headers( {"Content-Type: application/json","api-SOCIAL-API-KEY-ANDROID:SOCIAL_ANDROID"})
    @POST("/connectory/backend/web/api/V2/mobile/getcontact")
    public void getConatct(
            @Body JsonObject POST_PARAM,
            Callback<Response> callback);

}