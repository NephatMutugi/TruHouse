package com.nephat.truhouse.retrofitUtil;

import com.nephat.truhouse.models.ApiResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


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

   @Multipart
    @POST("upload.php")
    Call<ResponseBody>uploadPhoto(@Part("description") RequestBody description,
                                  @Part("location") RequestBody location,
                                  @Part("price") RequestBody price,
                                  @Part("type") RequestBody type,
                                  @Part("contact") RequestBody contact,
                                  @Part MultipartBody.Part photo
   );

}
