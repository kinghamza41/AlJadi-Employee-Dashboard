package com.rev9solutions.aljadi_employee_dashboard.APIIntegration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {
    String ACCESS_TOKEN;
    private static final String url = "https://dev.aljadiss.com/api/v1/";

    private static Controller clientObj;

    private static Retrofit retrofit;
    // static Gson gson = new GsonBuilder().setLenient().create();

    Controller() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .client(okHttpClient)
                .build();
    }
    public static synchronized Controller getInstance() {
        if (clientObj == null) {
            clientObj = new Controller();
        }
        return clientObj;
    }
    public ApiSet getApi() {
        return retrofit.create(ApiSet.class);
    }
}
