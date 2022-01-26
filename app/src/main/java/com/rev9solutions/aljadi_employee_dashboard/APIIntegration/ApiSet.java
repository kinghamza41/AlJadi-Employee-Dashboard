package com.rev9solutions.aljadi_employee_dashboard.APIIntegration;


import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.LoginRequest;
import com.rev9solutions.aljadi_employee_dashboard.modal.ApplyForLeave;
import com.rev9solutions.aljadi_employee_dashboard.modal.ArrivalTimeModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.LeavesModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.StartEndTimeModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.WorkingHoursModal;
import com.rev9solutions.aljadi_employee_dashboard.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiSet {

    @POST("employee/login")
    Call<LoginResponse> verifyUser(@Body LoginRequest loginRequest);


    @Headers("Content-Type: application/json")
    @POST("employee/start/end/time")
    Call<StartEndTimeModal> startEndTime(@Query("id") String id);

    @Headers("Content-Type: application/json")
    @POST("employee/working/hours")
    Call<WorkingHoursModal> workingHoursModal(@Query("id") String id);


    @Headers("Content-Type: application/json")
    @POST("employee/online/offline")
    Call<ArrivalTimeModal> arrivalTimeModal(@Query("id") String id);

    @Headers("Content-Type: application/json")
    @POST("employee/leaves")
    Call<LeavesModal> leavesModal(@Query("id") String id);

    @Headers("Content-Type: application/json")
    @POST("employee/leaves")
    Call<ApplyForLeave> applyForLeave(@Query("user_id") String id,
                                      @Query("leave_type") String leave_type,
                                      @Query("reason_for_leave") String reason_for_leave,
                                      @Query("leave_duration_type") String leave_duration_type,
                                      @Query("half_leave_day") String half_leave_day);

//    @Headers("Content-Type: application/json")
//    @POST("employee/leaves")
//    Call<LeavesResponse> leavesResponse(@Body int id);


//    @Headers("Content-Type: application/json")
//    @POST("employee/apply/for/leave")
//    Call<ApplyLeaveResponse> applyLeavesResponse(@Body int id);

}
 