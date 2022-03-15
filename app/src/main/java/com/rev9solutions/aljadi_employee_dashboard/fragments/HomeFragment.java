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
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rev9solutions.aljadi_employee_dashboard.APIIntegration.Controller;
import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.SessionManager.UserSession;
import com.rev9solutions.aljadi_employee_dashboard.modal.CheckInModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.CheckOutModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.DashboardModal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    int apiDelayed = 1000;
    TextView arrivalTime, startTime, total_working_hours, work_started, checkedIN, checkedOUT, endTime, salaryTV;
    Chronometer chronometer;
    AppCompatButton checkIn_btn, checkOut_btn;
    Spinner companyDropdown;
    Handler handler = new Handler();
    View progressOverlay;
    Runnable runnable;
    ProgressBar startEndTimePB, workingHoursPB, checkInOutPB, salaryPB;
    private SwipeRefreshLayout swipeContainer;
    long startTime1 = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    Handler timerHandler = new Handler();
    String currentTime;
    SimpleDateFormat format;


    public HomeFragment() {
        //   this.dashboardModalRefresh();
        // Required empty public constructor
    }

    @SuppressLint("SimpleDateFormat")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        View v2 = inflater.inflate(R.layout.activity_main, container, false);
        this.work_started = (TextView) v.findViewById(R.id.work_started);
        this.startTime = (TextView) v.findViewById(R.id.startTime);
        this.endTime = (TextView) v.findViewById(R.id.endTime);
        this.total_working_hours = (TextView) v.findViewById(R.id.total_working_hours);
        this.chronometer = (Chronometer) v.findViewById(R.id.today_working_hours);
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
        swipeRefreshLayout = v.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isConnected(HomeFragment.this)) {
                    showCustomDialog();
                } else {
                    checkedIN.setText("");
                    checkedOUT.setText("");
                    endTime.setText("");
//                    chronometer.setText("00:00");
                    work_started.setText("");
                    startTime.setText("");
                    total_working_hours.setText("");
                    dashboardModal2();
                    dashboardModal();
                }
            }

        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


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
        if (isConnected(HomeFragment.this)) {
            showCustomDialog();
        } else {
            dashboardModal2();
            checkInBtn();
            checkOutBtn();
        }
        UserSession userSession = new UserSession(getContext());
        format = new SimpleDateFormat("hh:mm:ss aa");
        currentTime = format.format(new Date());
        boolean flag = userSession.getFlag();
        if (flag) {
            String sessionManagerCurrentTime = userSession.GetKeyValue("dsf");
            try {
                Date date1 = format.parse(sessionManagerCurrentTime);
                Date date2 = format.parse(currentTime);
                assert date1 != null;
                assert date2 != null;
                long mils = date2.getTime() - date1.getTime();
                chronometer.setBase(SystemClock.elapsedRealtime() - mils);
                chronometer.start();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return v;
    }

    private boolean isConnected(HomeFragment loginActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) loginActivity.requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (wifiConn == null || !wifiConn.isConnected()) && (mobileConn == null || !mobileConn.isConnected());

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
                    }
                }).show();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }


    public void checkInBtn() {

        UserSession userSession = new UserSession(getContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        checkIn_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int checkIn = 0, checkOut = 0;
                if (checkIn == 0 && checkOut == 0) {
                    checkIn_btn.setVisibility(View.GONE);
                    checkOut_btn.setVisibility(View.VISIBLE);
                    checkOut_btn.setText("CheckOut");
                    Call<CheckInModal> callCheckIn = Controller.getInstance().getApi().checkInModal(new UserSession(getContext()).GetKeyValue("company_id2"), "Bearer " + ACCESS_TOKEN);
                    callCheckIn.enqueue(new Callback<CheckInModal>() {
                        @SuppressLint("SimpleDateFormat")
                        @Override
                        public void onResponse(Call<CheckInModal> call, Response<CheckInModal> response) {
                            if (response.body() == null) {
                                throw new AssertionError();
                            } else if (response.body().getStatusCode() == 200) {
                                Log.v("checkIn", response.body().getMessage());
                                chronometer.setVisibility(View.VISIBLE);
                                format = new SimpleDateFormat("hh:mm:ss aa");
                                currentTime = format.format(new Date());
                                boolean flag = userSession.getFlag();
                                if (!flag) {
                                    userSession.SaveKeyValue("dsf", currentTime);
                                    userSession.setFlag(true);
                                    chronometer.setBase(SystemClock.elapsedRealtime());
                                    chronometer.start();
                                } else {
                                    String sessionManagerCurrentTime = userSession.GetKeyValue("dsf");
                                    try {
                                        Date date1 = format.parse(sessionManagerCurrentTime);
                                        Date date2 = format.parse(currentTime);
                                        assert date1 != null;
                                        assert date2 != null;
                                        long mils = date2.getTime() - date1.getTime();
                                        chronometer.setBase(SystemClock.elapsedRealtime() - mils);
                                        chronometer.start();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
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

    public void checkOutBtn() {
        UserSession userSession = new UserSession(getContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        this.checkOut_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
                                work_started.setText("Ended");
                                //  chronometer.setBase(SystemClock.elapsedRealtime());
                                userSession.setFlag(false);
                                chronometer.stop();

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

    public void dashboardModal2() {
        UserSession userSession = new UserSession(getContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        Controller.getInstance().getApi().dashboardModal("Bearer " + ACCESS_TOKEN).enqueue(new Callback<DashboardModal>() {

            public void onResponse(@NonNull Call<DashboardModal> call, @NonNull Response<DashboardModal> response) {
                assert response.body() != null;
                if (response.body().getStatusCode() == 200) {
                    ArrayList<String> company = new ArrayList<>();
                    final ArrayList<Integer> c2 = new ArrayList<>();
                    for (int i = 0; i < response.body().getData().getCompanies().size(); i++) {
                        Log.v("msg", response.body().getData().getCompanies().get(i).getName());
                        company.add(response.body().getData().getCompanies().get(i).getName());
                        c2.add(response.body().getData().getCompanies().get(i).getPivot().getCompanyId());
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = null;
                    try {
                        spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, company);
                        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    } catch (NullPointerException ignored) {
                    }

                    companyDropdown.setAdapter(spinnerArrayAdapter);
                    companyDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
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
                public void onResponse(@NonNull Call<DashboardModal> call, @NonNull Response<DashboardModal> response) {
                    assert response.body() != null;
                    if (response.body().getStatusCode() == 200) {
                        checkInOutPB.setVisibility(View.GONE);
                        workingHoursPB.setVisibility(View.GONE);
                        startEndTimePB.setVisibility(View.GONE);

                        total_working_hours.setText(response.body().getData().getTotalWorkingHours());
                        startTime.setText(response.body().getData().getStartTime());
                        int checkIn = 0;
                        int checkOut = 0;
                        if (checkIn == response.body().getData().getCheckIn() && checkOut == response.body().getData().getCheckOut()) {
                            checkIn_btn.setVisibility(View.VISIBLE);
                            checkIn_btn.setText("CheckIN");
                            salaryPB.setVisibility(View.GONE);
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
                        }
                        int checkOut1 = 1;
                        if (checkIn1 == response.body().getData().getCheckIn() && checkOut1 == response.body().getData().getCheckOut()) {
                            checkOut_btn.setVisibility(View.GONE);
                            checkInOutPB.setVisibility(View.GONE);
                            work_started.setText("Ended");
                            salaryTV.setText(String.valueOf(response.body().getData().getSalary()));
                            salaryPB.setVisibility(View.GONE);
                            checkedOUT.setText("You checked out in " + response.body().getData().getCheckInCompany().getName());
                            endTime.setText(response.body().getData().getEndTime());
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }

                public void onFailure(@NonNull Call<DashboardModal> call, @NonNull Throwable t) {
                    Log.v("dashboardModalCall", t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //   dashboardModalRefresh();
                handler.postDelayed(runnable, apiDelayed);
            }
        }, apiDelayed);
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

        public abstract void onTick(int second);

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