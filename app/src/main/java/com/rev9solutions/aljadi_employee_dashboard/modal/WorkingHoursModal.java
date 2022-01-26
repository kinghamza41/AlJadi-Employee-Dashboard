package com.rev9solutions.aljadi_employee_dashboard.modal;

import com.rev9solutions.aljadi_employee_dashboard.modal.WorkingHoursModal.Datum;
import com.rev9solutions.aljadi_employee_dashboard.modal.WorkingHoursModal.Pivot;

import java.util.List;

public class WorkingHoursModal {

    private int statusCode;
    private String status;
    private String message;
    private List<Datum> data = null;


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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public static class Datum {
        public int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Pivot pivot;

        public Pivot getPivot() {
            return pivot;
        }

        public void setPivot(Pivot pivot) {
            this.pivot = pivot;
        }
    }

    public static class Pivot {
        private String working_hours;

        public String getWorkingHours() {
            return working_hours;
        }

        public void setWorkingHours(String workingHours) {
            this.working_hours = workingHours;
        }
    }


}