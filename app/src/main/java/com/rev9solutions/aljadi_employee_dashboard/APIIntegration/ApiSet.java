package com.rev9solutions.aljadi_employee_dashboard.APIIntegration;


import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.LoginRequest;
import com.rev9solutions.aljadi_employee_dashboard.modal.ApplyForLeave;
import com.rev9solutions.aljadi_employee_dashboard.modal.ArrivalTimeModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.CheckInModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.CheckOutModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.DashboardModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.LeavesModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.StartEndTimeModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.WorkingHoursModal;
import com.rev9solutions.aljadi_employee_dashboard.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiSet {

    @POST("employee/leaves")
    @Headers({"Content-Type: application/json"})
    Call<ApplyForLeave> applyForLeave(@Query("user_id") String str, @Query("leave_type") String str2, @Query("reason_for_leave") String str3, @Query("leave_duration_type") String str4, @Query("half_leave_day") String str5);

    @POST("employee/check/in")
    @Headers({"Content-Type: application/json"})
    Call<CheckInModal> checkInModal(@Query("company_id") String str, @Header("Authorization") String str2);

    @POST("employee/check/out")
    @Headers({"Content-Type: application/json"})
    Call<CheckOutModal> checkOutModal( @Header("Authorization") String str2);

    @GET("employee/dashboard")
    @Headers({"Content-Type: application/json"})
    Call<DashboardModal> dashboardModal(@Header("Authorization") String str);

    @POST("employee/leaves")
    @Headers({"Content-Type: application/json"})
    Call<LeavesModal> leavesModal(@Query("id") String str, @Header("Authorization") String str2);

    @POST("employee/leaves")
    @Headers({"Content-Type: application/json"})
    Call<ApplyForLeave> applyForLeaveModal(@Query("leave_type") String leave_type,
                                           @Query("reason_for_leave") String reason_for_leave,
                                           @Query("leave_duration_type") String leave_duration_type,
                                           @Query("leave_duration") String leave_duration,
                                           @Query("half_leave_day") String half_leave_day,
                                           @Query("full_leave_one_day") String full_leave_one_day,
                                           @Query("start_date") String start_date,
                                           @Query("end_date") String end_date,
                                           @Header("Authorization") String str2);

    @POST("employee/login")
    Call<LoginResponse> verifyUser(@Body LoginRequest loginRequest);

//    @Headers("Content-Type: application/json")
//    @POST("employee/leaves")
//    Call<LeavesResponse> leavesResponse(@Body int id);


//    @Headers("Content-Type: application/json")
//    @POST("employee/apply/for/leave")
//    Call<ApplyLeaveResponse> applyLeavesResponse(@Body int id);

}
 