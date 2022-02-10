package com.rev9solutions.aljadi_employee_dashboard.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardModal {
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

    public class CheckInCompany {
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("id")
        @Expose

        /* renamed from: id */
        private int f206id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("users")
        @Expose
        private List<User> users = null;

        public CheckInCompany() {
        }

        public int getId() {
            return this.f206id;
        }

        public void setId(int id) {
            this.f206id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name2) {
            this.name = name2;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email2) {
            this.email = email2;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address2) {
            this.address = address2;
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

        public List<User> getUsers() {
            return this.users;
        }

        public void setUsers(List<User> users2) {
            this.users = users2;
        }
    }

    public class Company {
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("id")
        @Expose

        /* renamed from: id */
        private int f207id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("pivot")
        @Expose
        private Pivot pivot;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Company() {
        }

        public int getId() {
            return this.f207id;
        }

        public void setId(int id) {
            this.f207id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name2) {
            this.name = name2;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email2) {
            this.email = email2;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address2) {
            this.address = address2;
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

        public Pivot getPivot() {
            return this.pivot;
        }

        public void setPivot(Pivot pivot2) {
            this.pivot = pivot2;
        }
    }

    public class Data {
        @SerializedName("check_in")
        @Expose
        private int checkIn;
        @SerializedName("checkIn_company")
        @Expose
        private CheckInCompany checkInCompany;
        @SerializedName("check_out")
        @Expose
        private int checkOut;
        @SerializedName("companies")
        @Expose
        private List<Company> companies = null;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("today_working_hours")
        @Expose
        private String todayWorkingHours;
        @SerializedName("total_working_hours")
        @Expose
        private String totalWorkingHours;

        public Data() {
        }

        public List<Company> getCompanies() {
            return this.companies;
        }

        public void setCompanies(List<Company> companies2) {
            this.companies = companies2;
        }

        public CheckInCompany getCheckInCompany() {
            return this.checkInCompany;
        }

        public void setCheckInCompany(CheckInCompany checkInCompany2) {
            this.checkInCompany = checkInCompany2;
        }

        public int getCheckIn() {
            return this.checkIn;
        }

        public void setCheckIn(int checkIn2) {
            this.checkIn = checkIn2;
        }

        public int getCheckOut() {
            return this.checkOut;
        }

        public void setCheckOut(int checkOut2) {
            this.checkOut = checkOut2;
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

        public String getTotalWorkingHours() {
            return this.totalWorkingHours;
        }

        public void setTotalWorkingHours(String totalWorkingHours2) {
            this.totalWorkingHours = totalWorkingHours2;
        }

        public String getTodayWorkingHours() {
            return this.todayWorkingHours;
        }

        public void setTodayWorkingHours(String todayWorkingHours2) {
            this.todayWorkingHours = todayWorkingHours2;
        }
    }

    public class Example {
        public Example() {
        }
    }

    public static class Pivot {
        @SerializedName("company_id")
        @Expose
        private int companyId;
        @SerializedName("salary")
        @Expose
        private int salary;
        @SerializedName("shift_end_time")
        @Expose
        private String shiftEndTime;
        @SerializedName("shift_start_time")
        @Expose
        private String shiftStartTime;
        @SerializedName("user_id")
        @Expose
        private int userId;
        @SerializedName("working_hours")
        @Expose
        private String workingHours;

        public int getUserId() {
            return this.userId;
        }

        public void setUserId(int userId2) {
            this.userId = userId2;
        }

        public int getCompanyId() {
            return this.companyId;
        }

        public void setCompanyId(int companyId2) {
            this.companyId = companyId2;
        }

        public String getShiftStartTime() {
            return this.shiftStartTime;
        }

        public void setShiftStartTime(String shiftStartTime2) {
            this.shiftStartTime = shiftStartTime2;
        }

        public String getShiftEndTime() {
            return this.shiftEndTime;
        }

        public void setShiftEndTime(String shiftEndTime2) {
            this.shiftEndTime = shiftEndTime2;
        }

        public String getWorkingHours() {
            return this.workingHours;
        }

        public void setWorkingHours(String workingHours2) {
            this.workingHours = workingHours2;
        }

        public int getSalary() {
            return this.salary;
        }

        public void setSalary(int salary2) {
            this.salary = salary2;
        }
    }

    public class Pivot__1 {
        @SerializedName("company_id")
        @Expose
        private int companyId;
        @SerializedName("salary")
        @Expose
        private int salary;
        @SerializedName("shift_end_time")
        @Expose
        private String shiftEndTime;
        @SerializedName("shift_start_time")
        @Expose
        private String shiftStartTime;
        @SerializedName("user_id")
        @Expose
        private int userId;
        @SerializedName("working_hours")
        @Expose
        private String workingHours;

        public Pivot__1() {
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

        public String getShiftStartTime() {
            return this.shiftStartTime;
        }

        public void setShiftStartTime(String shiftStartTime2) {
            this.shiftStartTime = shiftStartTime2;
        }

        public String getShiftEndTime() {
            return this.shiftEndTime;
        }

        public void setShiftEndTime(String shiftEndTime2) {
            this.shiftEndTime = shiftEndTime2;
        }

        public String getWorkingHours() {
            return this.workingHours;
        }

        public void setWorkingHours(String workingHours2) {
            this.workingHours = workingHours2;
        }

        public int getSalary() {
            return this.salary;
        }

        public void setSalary(int salary2) {
            this.salary = salary2;
        }
    }

    public class User {
        @SerializedName("allowed_overtime")
        @Expose
        private String allowedOvertime;
        @SerializedName("annual_leaves")
        @Expose
        private int annualLeaves;
        @SerializedName("birth_date")
        @Expose
        private String birthDate;
        @SerializedName("casual_leaves")
        @Expose
        private int casualLeaves;
        @SerializedName("compensatory_leaves")
        @Expose
        private int compensatoryLeaves;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("email_verified_at")
        @Expose
        private Object emailVerifiedAt;
        @SerializedName("employee_id")
        @Expose
        private String employeeId;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("general_salary")
        @Expose
        private int generalSalary;
        @SerializedName("general_shift_end_time")
        @Expose
        private String generalShiftEndTime;
        @SerializedName("general_shift_start_time")
        @Expose
        private String generalShiftStartTime;
        @SerializedName("hijri_iqama_expiry_date")
        @Expose
        private String hijriIqamaExpiryDate;
        @SerializedName("id")
        @Expose

        /* renamed from: id */
        private int f208id;
        @SerializedName("iqama_expiry_date")
        @Expose
        private String iqamaExpiryDate;
        @SerializedName("iqama_issue_date")
        @Expose
        private String iqamaIssueDate;
        @SerializedName("iqama_number")
        @Expose
        private String iqamaNumber;
        @SerializedName("joining_date")
        @Expose
        private String joiningDate;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("nationality")
        @Expose
        private String nationality;
        @SerializedName("notification_status")
        @Expose
        private int notificationStatus;
        @SerializedName("outside_the_kingdom")
        @Expose
        private int outsideTheKingdom;
        @SerializedName("passport_expiry_date")
        @Expose
        private String passportExpiryDate;
        @SerializedName("passport_number")
        @Expose
        private String passportNumber;
        @SerializedName("pivot")
        @Expose
        private Pivot__1 pivot;
        @SerializedName("profile_picture")
        @Expose
        private String profilePicture;
        @SerializedName("role_id")
        @Expose
        private int roleId;
        @SerializedName("sick_leaves")
        @Expose
        private int sickLeaves;
        @SerializedName("status")
        @Expose
        private int status;
        @SerializedName("total_leaves")
        @Expose
        private int totalLeaves;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public User() {
        }

        public int getId() {
            return this.f208id;
        }

        public void setId(int id) {
            this.f208id = id;
        }

        public int getRoleId() {
            return this.roleId;
        }

        public void setRoleId(int roleId2) {
            this.roleId = roleId2;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status2) {
            this.status = status2;
        }

        public String getEmployeeId() {
            return this.employeeId;
        }

        public void setEmployeeId(String employeeId2) {
            this.employeeId = employeeId2;
        }

        public String getIqamaNumber() {
            return this.iqamaNumber;
        }

        public void setIqamaNumber(String iqamaNumber2) {
            this.iqamaNumber = iqamaNumber2;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name2) {
            this.name = name2;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email2) {
            this.email = email2;
        }

        public String getGender() {
            return this.gender;
        }

        public void setGender(String gender2) {
            this.gender = gender2;
        }

        public String getNationality() {
            return this.nationality;
        }

        public void setNationality(String nationality2) {
            this.nationality = nationality2;
        }

        public String getDesignation() {
            return this.designation;
        }

        public void setDesignation(String designation2) {
            this.designation = designation2;
        }

        public String getPassportNumber() {
            return this.passportNumber;
        }

        public void setPassportNumber(String passportNumber2) {
            this.passportNumber = passportNumber2;
        }

        public String getPassportExpiryDate() {
            return this.passportExpiryDate;
        }

        public void setPassportExpiryDate(String passportExpiryDate2) {
            this.passportExpiryDate = passportExpiryDate2;
        }

        public String getIqamaIssueDate() {
            return this.iqamaIssueDate;
        }

        public void setIqamaIssueDate(String iqamaIssueDate2) {
            this.iqamaIssueDate = iqamaIssueDate2;
        }

        public String getIqamaExpiryDate() {
            return this.iqamaExpiryDate;
        }

        public void setIqamaExpiryDate(String iqamaExpiryDate2) {
            this.iqamaExpiryDate = iqamaExpiryDate2;
        }

        public String getBirthDate() {
            return this.birthDate;
        }

        public void setBirthDate(String birthDate2) {
            this.birthDate = birthDate2;
        }

        public int getOutsideTheKingdom() {
            return this.outsideTheKingdom;
        }

        public void setOutsideTheKingdom(int outsideTheKingdom2) {
            this.outsideTheKingdom = outsideTheKingdom2;
        }

        public String getHijriIqamaExpiryDate() {
            return this.hijriIqamaExpiryDate;
        }

        public void setHijriIqamaExpiryDate(String hijriIqamaExpiryDate2) {
            this.hijriIqamaExpiryDate = hijriIqamaExpiryDate2;
        }

        public Object getEmailVerifiedAt() {
            return this.emailVerifiedAt;
        }

        public void setEmailVerifiedAt(Object emailVerifiedAt2) {
            this.emailVerifiedAt = emailVerifiedAt2;
        }

        public int getGeneralSalary() {
            return this.generalSalary;
        }

        public void setGeneralSalary(int generalSalary2) {
            this.generalSalary = generalSalary2;
        }

        public String getGeneralShiftStartTime() {
            return this.generalShiftStartTime;
        }

        public void setGeneralShiftStartTime(String generalShiftStartTime2) {
            this.generalShiftStartTime = generalShiftStartTime2;
        }

        public String getGeneralShiftEndTime() {
            return this.generalShiftEndTime;
        }

        public void setGeneralShiftEndTime(String generalShiftEndTime2) {
            this.generalShiftEndTime = generalShiftEndTime2;
        }

        public String getAllowedOvertime() {
            return this.allowedOvertime;
        }

        public void setAllowedOvertime(String allowedOvertime2) {
            this.allowedOvertime = allowedOvertime2;
        }

        public int getSickLeaves() {
            return this.sickLeaves;
        }

        public void setSickLeaves(int sickLeaves2) {
            this.sickLeaves = sickLeaves2;
        }

        public int getCompensatoryLeaves() {
            return this.compensatoryLeaves;
        }

        public void setCompensatoryLeaves(int compensatoryLeaves2) {
            this.compensatoryLeaves = compensatoryLeaves2;
        }

        public int getAnnualLeaves() {
            return this.annualLeaves;
        }

        public void setAnnualLeaves(int annualLeaves2) {
            this.annualLeaves = annualLeaves2;
        }

        public int getCasualLeaves() {
            return this.casualLeaves;
        }

        public void setCasualLeaves(int casualLeaves2) {
            this.casualLeaves = casualLeaves2;
        }

        public int getTotalLeaves() {
            return this.totalLeaves;
        }

        public void setTotalLeaves(int totalLeaves2) {
            this.totalLeaves = totalLeaves2;
        }

        public String getJoiningDate() {
            return this.joiningDate;
        }

        public void setJoiningDate(String joiningDate2) {
            this.joiningDate = joiningDate2;
        }

        public String getProfilePicture() {
            return this.profilePicture;
        }

        public void setProfilePicture(String profilePicture2) {
            this.profilePicture = profilePicture2;
        }

        public Object getDeletedAt() {
            return this.deletedAt;
        }

        public void setDeletedAt(Object deletedAt2) {
            this.deletedAt = deletedAt2;
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

        public int getNotificationStatus() {
            return this.notificationStatus;
        }

        public void setNotificationStatus(int notificationStatus2) {
            this.notificationStatus = notificationStatus2;
        }

        public Pivot__1 getPivot() {
            return this.pivot;
        }

        public void setPivot(Pivot__1 pivot2) {
            this.pivot = pivot2;
        }
    }
}