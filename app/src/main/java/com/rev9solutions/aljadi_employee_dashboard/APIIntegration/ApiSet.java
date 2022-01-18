package com.rev9solutions.aljadi_employee_dashboard.APIIntegration;


import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.LoginRequest;
import com.rev9solutions.aljadi_employee_dashboard.response.LoginResponse;
import com.rev9solutions.aljadi_employee_dashboard.response.StartEndTimeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiSet {

    @POST("employee/login")
    Call<LoginResponse> verifyUser(@Body LoginRequest loginRequest);


    @Headers("Content-Type: application/json")
    @POST("employee/start/end/time")
    Call<StartEndTimeResponse> startEndTime(@Body int id);


//    @Headers("Content-Type: application/json")
//    @POST("employee/leaves")
//    Call<LeavesResponse> leavesResponse(@Body int id);


//    @Headers("Content-Type: application/json")
//    @POST("employee/apply/for/leave")
//    Call<ApplyLeaveResponse> applyLeavesResponse(@Body int id);

}
 