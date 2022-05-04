package com.rev9solutions.aljadi_employee_dashboard.APIIntegration;


import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.LoginRequest;
import com.rev9solutions.aljadi_employee_dashboard.modal.ApplyForLeave;
import com.rev9solutions.aljadi_employee_dashboard.modal.AttendanceRequestModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.GetCompanyModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.LeavesModal;
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
    Call<LeavesModal> leavesModal(@Query("id") String str, @Header("Authorization") String str2);

    @POST("employee/apply/for/leave")
    @Headers({"Content-Type: application/json"})
    Call<ApplyForLeave> applyForLeaveModal(@Query("leave_type") String leave_type,
                                           @Query("reason_for_leave") String reason_for_leave,
                                           @Query("leave_duration_type") String leave_duration_type,
                                           @Query("start_date") String start_date,
                                           @Query("end_date") String end_date,
                                           @Header("Authorization") String str5);

    @GET("employee/companies")
    @Headers({"Content-Type: application/json"})
    Call<GetCompanyModal> getCompanyModal(@Header("Authorization") String str);

    @POST("employee/store/attendance/request")
    @Headers({"Content-Type: application/json"})
    Call<AttendanceRequestModal> attendanceRequestModal(@Query("company_id") int company_id,
                                                        @Query("shift_start_time") String shift_start_time,
                                                        @Query("shift_end_time") String shift_end_time,
                                                        @Query("date") String date,
                                                        @Query("reason") String reason,
                                                        @Header("Authorization") String str);

    @POST("employee/login")
    Call<LoginResponse> verifyUser(@Body LoginRequest loginRequest);

}
 