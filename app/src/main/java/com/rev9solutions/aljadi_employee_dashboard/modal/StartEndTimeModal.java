package com.rev9solutions.aljadi_employee_dashboard.modal;

import com.google.gson.annotations.SerializedName;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.User;

public class StartEndTimeModal {

    @SerializedName("general_shift_start_time")
    int startTime;

    @SerializedName("general_shift_end_time")
    int endTime;

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
