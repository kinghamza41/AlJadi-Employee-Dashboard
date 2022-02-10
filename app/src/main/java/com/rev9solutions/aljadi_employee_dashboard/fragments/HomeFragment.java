package com.rev9solutions.aljadi_employee_dashboard.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rev9solutions.aljadi_employee_dashboard.APIIntegration.Controller;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.Data;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.User;
import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.SessionManager.UserSession;
import com.rev9solutions.aljadi_employee_dashboard.modal.ArrivalTimeModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.CheckInModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.CheckOutModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.DashboardModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.LeavesModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.StartEndTimeModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.WorkingHoursModal;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    int apiDelayed = 1000;
    TextView arrivalTime;
    boolean checkIn;
    ProgressBar checkInOutPB;
    AppCompatButton checkIn_btn;
    boolean checkOut;
    AppCompatButton checkOut_btn;
    TextView checkedIN;
    TextView checkedOUT;
    Spinner companyDropdown;
    TextView endTime;
    Handler handler = new Handler();
    View progressOverlay;
    Runnable runnable;
    ProgressBar startEndTimePB;
    TextView startTime;
    TextView today_working_hours;
    TextView total_working_hours;
    TextView work_started;
    TextView workingHours;
    ProgressBar workingHoursPB;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        this.work_started = (TextView) v.findViewById(R.id.work_started);
        this.startTime = (TextView) v.findViewById(R.id.startTime);
        this.endTime = (TextView) v.findViewById(R.id.endTime);
        this.total_working_hours = (TextView) v.findViewById(R.id.total_working_hours);
        this.today_working_hours = (TextView) v.findViewById(R.id.today_working_hours);
        this.checkInOutPB = (ProgressBar) v.findViewById(R.id.checkIn_out_PB);
        this.startEndTimePB = (ProgressBar) v.findViewById(R.id.startEndTimePB);
        this.workingHoursPB = (ProgressBar) v.findViewById(R.id.workingHoursPB);
        this.checkedIN = (TextView) v.findViewById(R.id.checkedIN);
        this.checkedOUT = (TextView) v.findViewById(R.id.checkedOUT);
        this.companyDropdown = (Spinner) v.findViewById(R.id.companyDropdown);
        this.checkIn_btn = (AppCompatButton) v.findViewById(R.id.checkIn_btn);
        this.checkOut_btn = (AppCompatButton) v.findViewById(R.id.check_out_btn);
//        this.ACCESS_TOKEN = new UserSession(getContext()).GetKeyValue("token");

        dashboardModal2();
        checkInBtn();
        checkOutBtn();

        return v;
    }

    public void checkOutBtn() {
        UserSession userSession = new UserSession(getContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        this.checkOut_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                int checkIn = Integer.parseInt(userSession.GetKeyValue("checkIn"));
//                int checkOut = Integer.parseInt(userSession.GetKeyValue("checkOut"));
                int checkIn=1;
                int checkOut=0;
                if (checkIn == 1 && checkOut == 0) {

                    Controller.getInstance().getApi().checkOutModal(userSession.GetKeyValue("company_id2"), "Bearer " + ACCESS_TOKEN).enqueue(new Callback<CheckOutModal>() {


                        public void onResponse(Call<CheckOutModal> call, Response<CheckOutModal> response) {
                            assert response.body() != null;
                            if (response.body().getStatusCode() == 200) {
                                Log.v("checkIn", response.body().getMessage());
                                checkInOutPB.setVisibility(View.GONE);
                                startEndTimePB.setVisibility(View.GONE);
                                work_started.setText("Ended");
                            }
                        }

                        public void onFailure(Call<CheckOutModal> call, Throwable t) {
                            Toast.makeText(HomeFragment.this.getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    dashboardModal();
                }
            }
        });
    }

    public void checkInBtn() {
        UserSession userSession = new UserSession(getContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        checkIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeFragment.this.getContext(), "clicked", Toast.LENGTH_LONG).show();
                int checkIn = 0, checkOut = 0;
                if (checkIn == 0 && checkOut == 0) {
                  checkOut_btn.setText("CheckOut");
                    Call<CheckInModal> callCheckIn = Controller.getInstance().getApi().checkInModal(new UserSession(getContext()).GetKeyValue("company_id2"), "Bearer " + ACCESS_TOKEN);
                    callCheckIn.enqueue(new Callback<CheckInModal>() {
                        @Override
                        public void onResponse(Call<CheckInModal> call, Response<CheckInModal> response) {
                            if (response.body() == null) {
                                throw new AssertionError();
                            } else if (response.body().getStatusCode() == 200) {
                                Log.v("checkIn", response.body().getMessage());
                               checkInOutPB.setVisibility(View.GONE);
                               startEndTimePB.setVisibility(View.GONE);
                               startTime.setText(response.body().getData().getStartTime());
                               work_started.setText("Started");
                            }
                        }

                        @Override
                        public void onFailure(Call<CheckInModal> call, Throwable t) {
                            Toast.makeText(HomeFragment.this.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                    dashboardModal();
                }
            }
        });
    }


    public void companyDropdown() {
        this.companyDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(HomeFragment.this.getContext(), "please select item", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void dashboardModal2() {
         UserSession userSession = new UserSession(getContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        try {
            Controller.getInstance().getApi().dashboardModal("Bearer " + ACCESS_TOKEN).enqueue(new Callback<DashboardModal>() {


                public void onResponse(Call<DashboardModal> call, Response<DashboardModal> response) {
                    if (response.body() == null) {
                        throw new AssertionError();
                    } else if (response.body().getStatusCode() == 200) {
                        ArrayList<String> company = new ArrayList<>();
                        final ArrayList<Integer> c2 = new ArrayList<>();
                        for (int i = 0; i < response.body().getData().getCompanies().size(); i++) {
                            Log.v("msg", response.body().getData().getCompanies().get(i).getName());
                            company.add(response.body().getData().getCompanies().get(i).getName());
                            c2.add(response.body().getData().getCompanies().get(i).getPivot().getCompanyId());
                        }
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(HomeFragment.this.getContext(), R.layout.support_simple_spinner_dropdown_item, company);
                        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        HomeFragment.this.companyDropdown.setAdapter(spinnerArrayAdapter);
                        HomeFragment.this.companyDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                Toast.makeText(HomeFragment.this.getContext(), "clicked " + c2.get(position), Toast.LENGTH_LONG).show();
                                userSession.SaveKeyValue("company_id2", String.valueOf(c2.get(position)));
                                if (c2.get(position) >= 0) {
                                    dashboardModal();
                                }
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                Toast.makeText(HomeFragment.this.getContext(), "please select item", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                public void onFailure(Call<DashboardModal> call, Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardModal() {
        UserSession userSession = new UserSession(getContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        try {
            Controller.getInstance().getApi().dashboardModal("Bearer " + ACCESS_TOKEN).enqueue(new Callback<DashboardModal>() {


                public void onResponse(Call<DashboardModal> call, Response<DashboardModal> response) {
                    if (response.body() == null) {
                        throw new AssertionError();
                    } else if (response.body().getStatusCode() == 200) {
                        total_working_hours.setText(response.body().getData().getTotalWorkingHours());
                        today_working_hours.setText(response.body().getData().getTodayWorkingHours());
                        startTime.setText(response.body().getData().getStartTime());
                        int checkIn = 0;
                        int checkOut = 0;
                        if (checkIn == response.body().getData().getCheckIn() && checkOut == response.body().getData().getCheckOut()) {
                            checkIn_btn.setVisibility(View.VISIBLE);
                            checkIn_btn.setText("CheckIN");
                            total_working_hours.setText(response.body().getData().getTotalWorkingHours());
                        }
                        int checkIn1 = 1;
                        if (checkIn1 == response.body().getData().getCheckIn() && checkOut == response.body().getData().getCheckOut()) {
                            checkIn_btn.setVisibility(View.GONE);
                            checkOut_btn.setVisibility(View.VISIBLE);
                            checkOut_btn.setText("CheckOut");
                            checkInOutPB.setVisibility(View.GONE);
                            workingHoursPB.setVisibility(View.GONE);
                            work_started.setText("Started");
                            startEndTimePB.setVisibility(View.GONE);
                            checkedIN.setText("You checked in " + response.body().getData().getCheckInCompany().getName());
                            startTime.setText(response.body().getData().getStartTime());
                            today_working_hours.setText(response.body().getData().getTodayWorkingHours());
                        }
                        int checkOut1=1;
                        if (checkIn1 == response.body().getData().getCheckIn() && checkOut1 == response.body().getData().getCheckOut()) {
                            checkOut_btn.setVisibility(View.GONE);
                            checkInOutPB.setVisibility(View.GONE);
                            startEndTimePB.setVisibility(View.GONE);
                            workingHoursPB.setVisibility(View.GONE);
                            work_started.setText("Ended");
                            checkedOUT.setText("You checked out in " + response.body().getData().getCheckInCompany().getName());
                            endTime.setText(response.body().getData().getEndTime());
                        }
                    }
                }

                public void onFailure(Call<DashboardModal> call, Throwable t) {
                    Toast.makeText(HomeFragment.this.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void onStart() {
//        super.onStart();
//        UserSession userSession = new UserSession(getContext());
//        int parseInt = Integer.parseInt(userSession.GetKeyValue("checkIn"));
//        int parseInt2 = Integer.parseInt(userSession.GetKeyValue("checkOut"));
//    }

//    public void onResume() {
//        super.onResume();
//        Handler handler2 = this.handler;
//        new Runnable() {
//            public void run() {
//                HomeFragment.this.handler.postDelayed(runnable, (long) apiDelayed);
//            }
//        };
//        //  this.runnable = r1;
//        //   handler2.postDelayed(r1, (long) this.apiDelayed);
//    }

//    public void onPause() {
//        super.onPause();
//        this.handler.removeCallbacks(this.runnable);
//    }
}