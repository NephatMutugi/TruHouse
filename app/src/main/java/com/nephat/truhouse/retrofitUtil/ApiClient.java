package com.nephat.truhouse.retrofitUtil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //Home wifi url
    private static final String BASE_URL ="http://192.168.100.2/realEstate/";

    //Phone hotspot Url
    //private static final String BASE_URL ="http://192.168.43.242/realEstate/";

    //Mi-fi url
    //private static final String BASE_URL ="http://192.168.8.100/realEstate/";
    private static ApiClient apiClient;
    private static Retrofit retrofit = null;




    public static Retrofit getApiClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }

    public ApiInterface getApi(){
        return retrofit.create(ApiInterface.class);
    }

}
