package com.rev9solutions.aljadi_employee_dashboard.APIIntegration;



import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.LoginRequest;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiSet {

    @POST("login")
    Call<LoginResponse> verifyUser(@Body LoginRequest loginRequest);

}
 