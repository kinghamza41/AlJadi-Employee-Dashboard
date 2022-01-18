package com.rev9solutions.aljadi_employee_dashboard.response;

import com.google.gson.annotations.SerializedName;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.User;
import com.rev9solutions.aljadi_employee_dashboard.modal.StartEndTimeModal;

public class StartEndTimeResponse {
    @SerializedName("statusCode")
    int statusCode;
    String message;
    @SerializedName("data")
    StartEndTimeModal startEndTimeModal;


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StartEndTimeModal getStartEndTimeModal() {
        return startEndTimeModal;
    }

    public void setStartEndTimeModal(StartEndTimeModal startEndTimeModal) {
        this.startEndTimeModal = startEndTimeModal;
    }
}
