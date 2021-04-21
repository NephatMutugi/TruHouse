package com.nephat.truhouse.retrofitUtil;

import com.nephat.truhouse.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface ApiInterface {

   @POST("register.php")
    Call<ApiResponse> performUserSignIn(@Field("name") String name, @Field("email") String email, @Field("password") String password);


}
