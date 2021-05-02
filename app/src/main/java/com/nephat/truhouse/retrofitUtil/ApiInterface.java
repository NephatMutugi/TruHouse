package com.nephat.truhouse.retrofitUtil;

import com.nephat.truhouse.models.ApiResponse;
import com.nephat.truhouse.models.FetchHousesResponse;
import com.nephat.truhouse.models.UploadModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
   @POST("updateAgent.php")
   Call<ApiResponse> performAgentUpdate();


   @FormUrlEncoded
   @POST("upload.php")
   Call<UploadModel> uploadImage(@Field("title") String title, @Field("location") String location, @Field("price") String price,
                                 @Field("house_type") String house_type, @Field("contact") String contact,
                                 @Field("description") String description, @Field("image") String image);


    @GET("fetchUsers.php")
    Call<FetchHousesResponse> fetchHouseInfo();


/*   @FormUrlEncoded
   @GET("fetchUsers.php")
    Call<FetchHousesResponse> fetchHouseInfo(@Field("image_path") String image_path,
                                             @Field("house_type") String house_type, @Field("location") String location);

                                             */



}












  /* @Multipart
    @POST("upload.php")
    Call<ResponseBody>uploadPhoto(@Part("description") RequestBody description,
                                  @Part("location") RequestBody location,
                                  @Part("price") RequestBody price,
                                  @Part("type") RequestBody type,
                                  @Part("contact") RequestBody contact,
                                  @Part MultipartBody.Part photo
   ); */
