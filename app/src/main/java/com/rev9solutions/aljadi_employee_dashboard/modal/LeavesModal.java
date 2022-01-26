package com.rev9solutions.aljadi_employee_dashboard.modal;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeavesModal {

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
    private Example data;

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

    public Example getData() {
        return data;
    }

    public void setData(Example data) {
        this.data = data;
    }


    public static class Example {


        @SerializedName("total_leaves")
        @Expose
        private int totalLeaves;
        @SerializedName("pending_leaves")
        @Expose
        private int pendingLeaves;
        @SerializedName("approved_leaves")
        @Expose
        private int approvedLeaves;
        @SerializedName("rejected_leaves")
        @Expose
        private int rejectedLeaves;

        public int getTotalLeaves() {
            return totalLeaves;
        }

        public void setTotalLeaves(int totalLeaves) {
            this.totalLeaves = totalLeaves;
        }

        public int getPendingLeaves() {
            return pendingLeaves;
        }

        public void setPendingLeaves(int pendingLeaves) {
            this.pendingLeaves = pendingLeaves;
        }

        public int getApprovedLeaves() {
            return approvedLeaves;
        }

        public void setApprovedLeaves(int approvedLeaves) {
            this.approvedLeaves = approvedLeaves;
        }

        public int getRejectedLeaves() {
            return rejectedLeaves;
        }

        public void setRejectedLeaves(int rejectedLeaves) {
            this.rejectedLeaves = rejectedLeaves;
        }


    }
}