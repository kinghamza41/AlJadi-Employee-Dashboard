package com.rev9solutions.aljadi_employee_dashboard.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rev9solutions.aljadi_employee_dashboard.APIIntegration.Controller;
import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.SessionManager.UserSession;
import com.rev9solutions.aljadi_employee_dashboard.activities.ApplyForLeaveActivity;
import com.rev9solutions.aljadi_employee_dashboard.modal.AttendanceRequestModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.DashboardModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.GetCompanyModal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AttendanceRequestFragment extends Fragment {
    SwipeRefreshLayout swipeRefresh;
    EditText shiftStartTime, shiftEndTime, dateET, reason;
    AppCompatButton submitAttendance;
    Calendar myCalendar = Calendar.getInstance();
    TimePickerDialog timePickerDialog;
    Spinner companySpinner;
    ArrayList<String> company = new ArrayList<>();
    final int year = myCalendar.get(Calendar.YEAR);
    final int month = myCalendar.get(Calendar.MONTH);
    final int day = myCalendar.get(Calendar.DAY_OF_MONTH);


    public AttendanceRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_attendance_request, container, false);
        shiftStartTime = v.findViewById(R.id.shift_start_time);
        shiftEndTime = v.findViewById(R.id.shift_end_time);
        dateET = v.findViewById(R.id.date);
        reason = v.findViewById(R.id.reason);
        companySpinner = v.findViewById(R.id.company_spinner);
        submitAttendance = v.findViewById(R.id.attendance_btn);
        swipeRefresh = v.findViewById(R.id.swipeRefreshAttendance);
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        dateET.setText(dateFormat.format(new Date())); // it will show 16/07/2013
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isConnected(AttendanceRequestFragment.this)) {
                    swipeRefresh.setRefreshing(false);
                    showCustomDialog();
                } else {
                    getCompanyDetails();
                }
                swipeRefresh.setRefreshing(false);
            }
        });

        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        shiftStartTimeDialog();
        shiftEndTimeDialog();
        dateEtDialog();
        if (isConnected(AttendanceRequestFragment.this)) {
            showCustomDialog();
        } else {
            getCompanyDetails();
        }
        submitAttendanceBtn();
        return v;
    }


    private void shiftEndTimeDialog() {
        shiftEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hoursOfDay, int minute) {
                        shiftEndTime.setText(hoursOfDay + ":" + minute);
                    }
                }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);
                timePickerDialog.show();
            }

        });
    }

    private void shiftStartTimeDialog() {
        shiftStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hoursOfDay, int minute) {
                        shiftStartTime.setText(hoursOfDay + ":" + minute);
                    }
                }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);
                timePickerDialog.show();
            }

        });
    }

    private void dateEtDialog() {
        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = year + "/" + month + "/" + day;
                        dateET.setText(date);

                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
    }

    private void getCompanyDetails() {
        UserSession userSession = new UserSession(getContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        Call<GetCompanyModal> getCompanyModalCall = Controller.getInstance().getApi().getCompanyModal("Bearer " + ACCESS_TOKEN);
        getCompanyModalCall.enqueue(new Callback<GetCompanyModal>() {
            @Override
            public void onResponse(@NonNull Call<GetCompanyModal> call, @NonNull Response<GetCompanyModal> response) {

                final ArrayList<Integer> c2 = new ArrayList<>();
//                company.add(0, "Please Select Company");
                for (int i = 0; i < Objects.requireNonNull(response.body()).getData().size(); i++) {
                    Log.v("msg", response.body().getData().get(i).getName());
                    Log.v("msgCom", String.valueOf(response.body().getData().get(i).getPivot().getCompanyId()));
                    //  Toast.makeText(getContext(), String.valueOf(companySpinner.getId()), Toast.LENGTH_SHORT).show();
                    company.add(response.body().getData().get(i).getName());
                    c2.add(response.body().getData().get(i).getPivot().getCompanyId());
                }
                ArrayAdapter<String> spinnerArrayAdapter = null;
                try {
                    spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item, company);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                } catch (NullPointerException ignored) {
                }

                companySpinner.setAdapter(spinnerArrayAdapter);
                companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        userSession.SaveKeyValue("company_id2", String.valueOf(c2.get(position)));

                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Toast.makeText(getContext(), "please select item", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<GetCompanyModal> call, Throwable t) {
              //  Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitAttendanceBtn() {
        submitAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean connection = isNetworkAvailable();
                if (connection) {
                    if (shiftStartTime.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Please Select Start Time", Toast.LENGTH_SHORT).show();
                    } else if (shiftEndTime.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Please Select End Time", Toast.LENGTH_SHORT).show();
                    } else if (dateET.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_SHORT).show();
                    } else if (reason.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Please Select Reason", Toast.LENGTH_SHORT).show();
                    } else {

                        attendanceRequest();
//

                    }
                } else {
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

    private void attendanceRequest() {
        UserSession userSession = new UserSession(getContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        //v   Toast.makeText(getContext(), String.valueOf(company.get(0)), Toast.LENGTH_SHORT).show();

        Call<AttendanceRequestModal> call = Controller.getInstance().getApi().attendanceRequestModal(Integer.parseInt(userSession.GetKeyValue("company_id2")), shiftStartTime.getText().toString(), shiftEndTime.getText().toString(), dateET.getText().toString(), reason.getText().toString(), "Bearer " + ACCESS_TOKEN);
        call.enqueue(new Callback<AttendanceRequestModal>() {
            @Override

            public void onResponse(@NonNull Call<AttendanceRequestModal> call, @NonNull Response<AttendanceRequestModal> response) {

                if (response.body().getStatus().equals("success")) {
                    Toast.makeText(getContext(), "Attendance request send successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Already apply for attendance request on this date", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<AttendanceRequestModal> call, Throwable t) {
               // Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isConnected(AttendanceRequestFragment loginActivity) {
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

}