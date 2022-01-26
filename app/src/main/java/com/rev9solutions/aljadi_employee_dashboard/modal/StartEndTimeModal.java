//package com.rev9solutions.aljadi_employee_dashboard.modal;
//
//import com.google.gson.annotations.SerializedName;
//import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.User;
//
//public class StartEndTimeModal {
//
//    @SerializedName("general_shift_start_time")
//    String startTime;
//
//    @SerializedName("general_shift_end_time")
//    int endTime;
//
//    public String getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(String startTime) {
//        this.startTime = startTime;
//    }
//
//    public int getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(int endTime) {
//        this.endTime = endTime;
//    }
//}
package com.rev9solutions.aljadi_employee_dashboard.modal;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class StartEndTimeModal {

    @SerializedName("general_shift_start_time")
    @Expose
    private String generalShiftStartTime;
    @SerializedName("general_shift_end_time")
    @Expose
    private String generalShiftEndTime;

    public String getGeneralShiftStartTime() {
        return generalShiftStartTime;
    }

    public void setGeneralShiftStartTime(String generalShiftStartTime) {
        this.generalShiftStartTime = generalShiftStartTime;
    }

    public String getGeneralShiftEndTime() {
        return generalShiftEndTime;
    }

    public void setGeneralShiftEndTime(String generalShiftEndTime) {
        this.generalShiftEndTime = generalShiftEndTime;
    }

    @SerializedName("statusCode")
    @Expose
    private int statusCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private StartEndTimeModal data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StartEndTimeModal getData() {
        return data;
    }

    public void setData(StartEndTimeModal data) {
        this.data = data;
    }

}
