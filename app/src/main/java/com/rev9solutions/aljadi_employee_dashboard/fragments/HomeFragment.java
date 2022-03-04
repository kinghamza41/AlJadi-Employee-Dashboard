package com.rev9solutions.aljadi_employee_dashboard.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rev9solutions.aljadi_employee_dashboard.APIIntegration.Controller;
import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.SessionManager.UserSession;
import com.rev9solutions.aljadi_employee_dashboard.activities.LoginActivity;
import com.rev9solutions.aljadi_employee_dashboard.modal.CheckInModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.CheckOutModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.DashboardModal;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    int apiDelayed = 1000;
    TextView arrivalTime, startTime, today_working_hours, total_working_hours, work_started, checkedIN, checkedOUT, endTime, salaryTV;
    AppCompatButton checkIn_btn, checkOut_btn;
    Spinner companyDropdown;
    Handler handler = new Handler();
    View progressOverlay;
    Runnable runnable;
    ProgressBar startEndTimePB, workingHoursPB, checkInOutPB, salaryPB;
    private SwipeRefreshLayout swipeContainer;
    long startTime1 = 0;
    Handler timerHandler = new Handler();

    public HomeFragment() {
        //   this.dashboardModalRefresh();
        // Required empty public constructor
    }

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
        this.salaryTV = v.findViewById(R.id.salaryTV);
        this.salaryPB = v.findViewById(R.id.salaryPB);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dashboardModalRefresh();
//
//            }
//        }, 1000);
        boolean connection = isNetworkAvailable();
        if (connection) {
            Log.v("msg", "success");
           // Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            //dashboardModal2();
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

        }
        if (!isConnected(HomeFragment.this)) {
            showCustomDialog();
        }
        else {
            dashboardModal2();
            checkInBtn();
            checkOutBtn();
        }
        return v;
    }
    private boolean isConnected(HomeFragment loginActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) loginActivity.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());

    }
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //   loginResponse();
                        //  dialog.dismiss();
                    }
                }).show();
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

    public void checkOutBtn() {
        UserSession userSession = new UserSession(getContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        this.checkOut_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                int checkIn = Integer.parseInt(userSession.GetKeyValue("checkIn"));
//                int checkOut = Integer.parseInt(userSession.GetKeyValue("checkOut"));
                int checkIn = 1;
                int checkOut = 0;
                if (checkIn == 1 && checkOut == 0) {
                    checkOut_btn.setVisibility(View.GONE);
                    Controller.getInstance().getApi().checkOutModal("Bearer " + ACCESS_TOKEN).enqueue(new Callback<CheckOutModal>() {


                        @SuppressLint("SetTextI18n")
                        public void onResponse(Call<CheckOutModal> call, Response<CheckOutModal> response) {
                            assert response.body() != null;
                            if (response.body().getStatusCode() == 200) {
                                Log.v("checkIn", response.body().getMessage());
//                                checkInOutPB.setVisibility(View.GONE);
//                                startEndTimePB.setVisibility(View.GONE);
                                work_started.setText("Ended");
                                dashboardModal();
                            }
                        }

                        public void onFailure(Call<CheckOutModal> call, Throwable t) {
                            Toast.makeText(HomeFragment.this.getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
    }

    public void checkInBtn() {
        UserSession userSession = new UserSession(getContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        checkIn_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeFragment.this.getContext(), "clicked", Toast.LENGTH_LONG).show();
                int checkIn = 0, checkOut = 0;
                if (checkIn == 0 && checkOut == 0) {
                    checkIn_btn.setVisibility(View.GONE);
                    checkOut_btn.setVisibility(View.VISIBLE);
                    checkOut_btn.setText("CheckOut");
                    Call<CheckInModal> callCheckIn = Controller.getInstance().getApi().checkInModal(new UserSession(getContext()).GetKeyValue("company_id2"), "Bearer " + ACCESS_TOKEN);
                    callCheckIn.enqueue(new Callback<CheckInModal>() {
                        @Override
                        public void onResponse(Call<CheckInModal> call, Response<CheckInModal> response) {
                            if (response.body() == null) {
                                throw new AssertionError();
                            } else if (response.body().getStatusCode() == 200) {
                                Log.v("checkIn", response.body().getMessage());
//                                checkInOutPB.setVisibility(View.GONE);
//                                startEndTimePB.setVisibility(View.GONE);
                                startTime.setText(response.body().getData().getStartTime());
                                work_started.setText("Started");
                                dashboardModal();
                            }
                        }

                        @Override
                        public void onFailure(Call<CheckInModal> call, Throwable t) {
                            Toast.makeText(HomeFragment.this.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

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

    public void dashboardModalRefresh() {
        // Toast.makeText(getContext(), "swipe clicked", Toast.LENGTH_SHORT).show();

//        UserSession userSession = new UserSession(getContext());
//        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
//            Call<DashboardModal> call = Controller.getInstance().getApi().dashboardModal("Bearer " + ACCESS_TOKEN);
//            call.enqueue(new Callback<DashboardModal>() {
//                @Override
//                public void onResponse(Call<DashboardModal> call, Response<DashboardModal> response) {
//                    int checkIn=1, checkOut=0;
//                    assert response.body() != null;
//                    if (response.body().getStatusCode()==200){
////                        if(checkIn==1&&checkOut==1){
////                            checkInOutPB.setVisibility(View.VISIBLE);
////                            checkOut_btn.setVisibility(View.GONE);
////                            Log.v("check", String.valueOf(response.body().getData().getCheckInCompany()));
////
////                        }
//
////                        if(checkIn==1&&checkOut==0){
////                            today_working_hours.setText(response.body().getData().getTodayWorkingHours());
////                        }
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<DashboardModal> call, Throwable t) {
//                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
    }

    public  void dashboardModal2() {
        UserSession userSession = new UserSession(getContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
            Controller.getInstance().getApi().dashboardModal("Bearer " + ACCESS_TOKEN).enqueue(new Callback<DashboardModal>() {


                public void onResponse(@NonNull Call<DashboardModal> call, Response<DashboardModal> response) {
                    assert response.body() != null;
                    if (response.body().getStatusCode() == 200) {
                        ArrayList<String> company = new ArrayList<>();
                        final ArrayList<Integer> c2 = new ArrayList<>();
                        for (int i = 0; i < response.body().getData().getCompanies().size(); i++) {
                            Log.v("msg", response.body().getData().getCompanies().get(i).getName());
                            company.add(response.body().getData().getCompanies().get(i).getName());
                            c2.add(response.body().getData().getCompanies().get(i).getPivot().getCompanyId());
                        }
                        ArrayAdapter<String> spinnerArrayAdapter= null;
                        try {
                             spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, company);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        }
                        catch (NullPointerException  ignored) {
                        }

                        companyDropdown.setAdapter(spinnerArrayAdapter);
                        companyDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                               // Toast.makeText(HomeFragment.this.getContext(), "clicked " + c2.get(position), Toast.LENGTH_LONG).show();
                                userSession.SaveKeyValue("company_id2", String.valueOf(c2.get(position)));
                                if (c2.get(position) >= 0) {
                                    dashboardModal();
                                    //spinnerArrayAdapter.add(ACCESS_TOKEN);
                                }
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                Toast.makeText(HomeFragment.this.getContext(), "please select item", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                public void onFailure(@NonNull Call<DashboardModal> call, @NonNull Throwable t) {
                    Log.v("dashboardCall", t.getLocalizedMessage());
                }
            });

    }

    public void dashboardModal() {
        UserSession userSession = new UserSession(getContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        try {
            Controller.getInstance().getApi().dashboardModal("Bearer " + ACCESS_TOKEN).enqueue(new Callback<DashboardModal>() {


                @SuppressLint("SetTextI18n")
                public void onResponse(Call<DashboardModal> call, Response<DashboardModal> response) {
                    assert response.body() != null;
                    if (response.body().getStatusCode() == 200) {
                        checkInOutPB.setVisibility(View.GONE);
                        workingHoursPB.setVisibility(View.GONE);
                        startEndTimePB.setVisibility(View.GONE);
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
                            work_started.setText("Started");
                            checkedIN.setText("You checked in " + response.body().getData().getCheckInCompany().getName());
                            startTime.setText(response.body().getData().getStartTime());
                            salaryPB.setVisibility(View.GONE);
                            salaryTV.setText(String.valueOf(response.body().getData().getSalary()));
                           // CountUpTimer.INTERVAL_MS();
//                            Runnable timerRunnable = new Runnable() {
//
//                                @SuppressLint("DefaultLocale")
//                                @Override
//                                public void run() {
//                                    long millis = System.currentTimeMillis() - startTime1;
//                                    int seconds = (int) (millis / 1000);
//                                    int minutes = seconds / 60;
//                                    seconds = seconds % 60;
//
//                                    today_working_hours.setText(String.format("%d:%02d", minutes, seconds));
//
//                                    timerHandler.postDelayed(this, 500);
//                                }
//                            };
                            today_working_hours.setText(response.body().getData().getTodayWorkingHours());


                        }
                        int checkOut1 = 1;
                        if (checkIn1 == response.body().getData().getCheckIn() && checkOut1 == response.body().getData().getCheckOut()) {
                            checkOut_btn.setVisibility(View.GONE);
                            checkInOutPB.setVisibility(View.GONE);
//                            startEndTimePB.setVisibility(View.GONE);
//                            workingHoursPB.setVisibility(View.GONE);
                            work_started.setText("Ended");
                            checkedOUT.setText("You checked out in " + response.body().getData().getCheckInCompany().getName());
                            endTime.setText(response.body().getData().getEndTime());


                            //timerHandler.removeCallbacks(timerRunnable);
                        }
                    }
                }

                public void onFailure(Call<DashboardModal> call, Throwable t) {
                   Log.v("dashboardModalCall", t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getContext(), "sho", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //do your function;
                //   dashboardModalRefresh();
                handler.postDelayed(runnable, apiDelayed);
            }
        }, apiDelayed); // so basically after your getPendingLeaves(), from next time it will be 5 sec repeated
    }

    @Override
    public void onStart() {
        super.onStart();
        dashboardModal2();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible
    }

    public abstract static class CountUpTimer extends CountDownTimer {
        private static final long INTERVAL_MS = 1000;
        private final long duration;

        protected CountUpTimer(long durationMs) {
            super(durationMs, INTERVAL_MS);
            this.duration = durationMs;
        }

        public abstract  void onTick(int second);

        @Override
        public void onTick(long msUntilFinished) {
            int second = (int) ((duration - msUntilFinished) / 1000);
            onTick(second);
        }

        @Override
        public void onFinish() {
            onTick(duration / 1000);
        }
    }
}