/**
 * @file ApiClient.java
 * @brief This class will have all constants related to the networking and retrofit instance
 * @author Shrikant
 * @date 15/04/2018
 */
package com.goalsr.homequarantineTracker.apiservice;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


//    public static final String BASE_URL = "http://35.165.237.181:8080";    //prod
//    public static final String BASE_URL = "http://54.202.114.15:8080/";    //prod
    public static final String BASE_URL = "http://qurantinewatch.azurewebsites.net";    //prod
    public static final String BASE_URL_IMAGE = "http://218.248.32.25/";    //prod
//    public static final String BASE_URL = "http://218.248.32.25";    //prod

    public static final String AMAZON_BUCKET = "yelligo-qa";




    private static Retrofit retrofit = null;
    private static Retrofit retrofitImage = null;
    /**
     * This method returns retrofit client instance
     *
     * @return Retrofit object
     */
    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).addInterceptor(interceptor).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)

                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientImageUpload() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).addInterceptor(interceptor).build();

        if (retrofitImage == null) {
            retrofitImage = new Retrofit.Builder()
                    .baseUrl(BASE_URL_IMAGE)

                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitImage;
    }
}
