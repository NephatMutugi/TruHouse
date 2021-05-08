package com.nephat.truhouse.retrofitUtil;

import com.nephat.truhouse.models.ApiResponse;
import com.nephat.truhouse.models.FetchAgentHouseResponse;
import com.nephat.truhouse.models.FetchHousesResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {

   @FormUrlEncoded
    @POST("register.php")
    Call<ApiResponse> performUserRegister(@Field("name") String name, @Field("email") String email,
                                        @Field("password") String password);

   @FormUrlEncoded
   @POST("login.php")
    Call<ApiResponse> performUserLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("loginAgent.php")
    Call<ApiResponse> performAgentLogin(@Field("reg_no") String reg_no, @Field("password") String password);


   @FormUrlEncoded
   @POST("updateAgent.php")
   Call<ApiResponse> performAgentUpdate(@Field("reg_no") String reg_no, @Field("phone") String phone, @Field("email") String email,
                                        @Field("locality") String locality, @Field("password") String password);


   @FormUrlEncoded
   @POST("upload.php")
   Call<ApiResponse> uploadImage(@Field("title") String title, @Field("location") String location, @Field("price") String price,
                                 @Field("house_type") String house_type, @Field("contact") String contact,
                                 @Field("description") String description, @Field("image") String image,
                                 @Field("image2") String image2, @Field("image3") String image3,
                                 @Field("registered_agents_fk") String registered_agents_fk, @Field("image_name") String image_name,
                                 @Field("image_name2") String image_name2, @Field("image_name3") String image_name3);


    @GET("fetchUsers.php")
    Call<FetchHousesResponse> fetchHouseInfo();

    @GET("fetchAgentHouses.php")
    Call<FetchAgentHouseResponse> fetchAgentHouseInfo(@Query("registered_agents_fk") String reg_no);


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
