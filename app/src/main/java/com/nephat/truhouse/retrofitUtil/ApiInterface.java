package com.nephat.truhouse.retrofitUtil;

import com.nephat.truhouse.models.ApiResponse;
import com.nephat.truhouse.models.FetchAgentHouseResponse;
import com.nephat.truhouse.models.FetchAgentListResponse;
import com.nephat.truhouse.models.FetchAgentReviews;
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
   @POST("ratingAgent.php")
   Call<ApiResponse> performAgentRating(@Field("user_id") String user_id, @Field("agent_id") String agent_id,
                                        @Field("agent_reg") String agent_reg, @Field("rating") String rating,
                                        @Field("review") String review);

   @FormUrlEncoded
   @POST("login.php")
    Call<ApiResponse> performUserLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("loginAdmin.php")
    Call<ApiResponse> performAdminLogin(@Field("admin_email") String email, @Field("admin_password") String password);


    @FormUrlEncoded
    @POST("loginAgent.php")
    Call<ApiResponse> performAgentLogin(@Field("reg_no") String reg_no, @Field("password") String password);


   @FormUrlEncoded
   @POST("updateAgent.php")
   Call<ApiResponse> performAgentUpdate(@Field("reg_no") String reg_no, @Field("phone") String phone, @Field("email") String email,
                                        @Field("locality") String locality, @Field("password") String password);

   @FormUrlEncoded
   @POST("deleteHouse.php")
   Call<ApiResponse> performHouseDelete(@Field("id") String id);

    @FormUrlEncoded
    @POST("updateHouses.php")
    Call<ApiResponse> performHouseUpdate(@Field("id") String id, @Field("location") String location, @Field("price") String price,
                                         @Field("house_type") String house_type, @Field("contact") String contact, @Field("description") String description);


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

    @GET("getRating.php")
    Call<ApiResponse> getAvgRating(@Query("agent_reg_no") String agent_reg_no);

    @GET("fetchAgents.php")
    Call<FetchAgentListResponse> fetchAgentsList();

    @GET("fetchRatings.php")
    Call<FetchAgentReviews> fetchReviews(@Query("agent_reg") String agent_reg);


}
