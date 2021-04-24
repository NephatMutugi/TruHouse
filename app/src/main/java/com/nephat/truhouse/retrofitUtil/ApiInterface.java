package com.nephat.truhouse.retrofitUtil;

import com.nephat.truhouse.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

   @FormUrlEncoded
    @POST("register.php")
    Call<ApiResponse> performUserRegister(@Field("name") String name, @Field("email") String email,
                                        @Field("password") String password);

   @FormUrlEncoded
   @POST("login.php")
    Call<ApiResponse> performUserLogin(@Field("email") String email, @Field("password") String password);

   @FormUrlEncoded
   @POST("fetchUsers.php")
    Call<ApiResponse> fetchUserInfo(@Field("name") String name, @Field("email") String email);

}
