package com.rev9solutions.aljadi_employee_dashboard.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckOutModal {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusCode")
    @Expose
    private int statusCode;

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode2) {
        this.statusCode = statusCode2;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public Data getData() {
        return this.data;
    }

    public void setData(Data data2) {
        this.data = data2;
    }

    public class Data {
        @SerializedName("company_id")
        @Expose
        private int companyId;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("employee_name")
        @Expose
        private String employeeName;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("id")
        @Expose

        /* renamed from: id */
        private int f205id;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("user_id")
        @Expose
        private int userId;
        @SerializedName("working_hours")
        @Expose
        private String workingHours;

        public Data() {
        }

        public int getId() {
            return this.f205id;
        }

        public void setId(int id) {
            this.f205id = id;
        }

        public int getCompanyId() {
            return this.companyId;
        }

        public void setCompanyId(int companyId2) {
            this.companyId = companyId2;
        }

        public int getUserId() {
            return this.userId;
        }

        public void setUserId(int userId2) {
            this.userId = userId2;
        }

        public String getCompanyName() {
            return this.companyName;
        }

        public void setCompanyName(String companyName2) {
            this.companyName = companyName2;
        }

        public String getEmployeeName() {
            return this.employeeName;
        }

        public void setEmployeeName(String employeeName2) {
            this.employeeName = employeeName2;
        }

        public String getStartTime() {
            return this.startTime;
        }

        public void setStartTime(String startTime2) {
            this.startTime = startTime2;
        }

        public String getEndTime() {
            return this.endTime;
        }

        public void setEndTime(String endTime2) {
            this.endTime = endTime2;
        }

        public String getWorkingHours() {
            return this.workingHours;
        }

        public void setWorkingHours(String workingHours2) {
            this.workingHours = workingHours2;
        }

        public String getDate() {
            return this.date;
        }

        public void setDate(String date2) {
            this.date = date2;
        }

        public String getCreatedAt() {
            return this.createdAt;
        }

        public void setCreatedAt(String createdAt2) {
            this.createdAt = createdAt2;
        }

        public String getUpdatedAt() {
            return this.updatedAt;
        }

        public void setUpdatedAt(String updatedAt2) {
            this.updatedAt = updatedAt2;
        }
    }
}
