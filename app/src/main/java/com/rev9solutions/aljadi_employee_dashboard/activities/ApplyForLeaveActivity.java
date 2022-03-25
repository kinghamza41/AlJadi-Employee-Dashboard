package com.rev9solutions.aljadi_employee_dashboard.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rev9solutions.aljadi_employee_dashboard.APIIntegration.Controller;
import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.SessionManager.UserSession;
import com.rev9solutions.aljadi_employee_dashboard.modal.ApplyForLeave;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyForLeaveActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    Toolbar toolbar;
    Spinner leaveTypeSpinner, timePeriodSpinner, leaveDurationSpinner;
    EditText halfLeaveDatePicker, fullLeaveDatePicker, startDate, endDate, reasonForLeaveET;
    AppCompatButton applyForLeaveBtn;
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog datePickerDialog;
    String leaveTypeSpinnerValues, timePeriodSpinnerValues, leaveDurationSpinnerValues, reasonForLeave;
    final int year = myCalendar.get(Calendar.YEAR);
    final int month = myCalendar.get(Calendar.MONTH);
    final int day = myCalendar.get(Calendar.DAY_OF_MONTH);


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_leave);
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("Apply For Leave");
        setSupportActionBar(toolbar);
        leaveTypeSpinner = findViewById(R.id.spinner1);
        timePeriodSpinner = findViewById(R.id.spinner2);
        leaveDurationSpinner = findViewById(R.id.spinner3);
        startDate = findViewById(R.id.datePicker3);
        endDate = findViewById(R.id.datePicker4);
        applyForLeaveBtn = findViewById(R.id.applyForLeaveBtn);
        reasonForLeaveET = findViewById(R.id.reasonForLeave_ET);

        leaveTypeAdapter();
        timePeriodAdapter();
        leaveDurationAdapter();
        startDate();
        endDate();

    }

    private void startDate() {
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ApplyForLeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = year + "/" + month + "/" + day;
                        startDate.setText(date);

                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
    }

    private void endDate() {
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ApplyForLeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = year + "/" + month + "/" + day;
                        endDate.setText(date);

                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
    }


    private void timePeriodAdapter() {
        String[] timePeriod = getResources().getStringArray(R.array.timePeriod);
        //Time Period Adapter
        ArrayAdapter<String> timePeriodAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, timePeriod) {
            public int getCount() {
                return super.getCount();
            }

            @Override
            public boolean isEnabled(int position) {
                return position != 0;

            }
        };
        timePeriodAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        timePeriodSpinner.setAdapter(timePeriodAdapter);

        //Time Period Spinner
        timePeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int position, long id) {
                timePeriodSpinnerValues = timePeriodSpinner.getItemAtPosition(position).toString().toLowerCase();

                if (position == 2) {
                    leaveDurationSpinner.setVisibility(View.VISIBLE);
                    leaveDurationSpinner.setSelection(0);
                    startDate.setVisibility(View.GONE);
                } else if (position == 1) {
                    leaveDurationSpinner.setVisibility(View.GONE);
                    startDate.setVisibility(View.VISIBLE);
                    startDate.setText("");
                    endDate.setVisibility(View.GONE);
                    endDate.setText("");

                } else {
                    leaveDurationSpinner.setVisibility(View.GONE);
                    startDate.setVisibility(View.GONE);
                    endDate.setVisibility(View.GONE);
                    startDate.setText("");
                    endDate.setText("");
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void leaveDurationAdapter() {
        String[] leaveDuration = getResources().getStringArray(R.array.leaveDuration);

        // Leave Duration Adapter
        ArrayAdapter<String> leaveDurationAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, leaveDuration) {
            public int getCount() {
                return super.getCount();
            }

            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
        };
        leaveDurationAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        leaveDurationSpinner.setAdapter(leaveDurationAdapter);

        //Leave Duration Spinner
        leaveDurationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                leaveDurationSpinnerValues = leaveDurationSpinner.getItemAtPosition(position).toString().toLowerCase();

                if (position == 1) {
                    startDate.setVisibility(View.VISIBLE);
                    endDate.setVisibility(View.GONE);
                    startDate.setText("");
                    endDate.setText("");
                } else if (position == 2) {
                    startDate.setVisibility(View.VISIBLE);
                    startDate.setText("");
                    endDate.setVisibility(View.VISIBLE);
                    endDate.setText("");
                } else {
                    startDate.setVisibility(View.GONE);
                    startDate.setText("");
                    endDate.setVisibility(View.GONE);
                    endDate.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void leaveTypeAdapter() {
        String[] leaveType = getResources().getStringArray(R.array.leaveType);
        // Leave Type Adapter
        ArrayAdapter<String> leaveTypeAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, leaveType) {
            public int getCount() {
                return super.getCount();
            }

            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
        };
        leaveTypeAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        leaveTypeSpinner.setAdapter(leaveTypeAdapter);

        // Leave Type Spinner
        leaveTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                leaveTypeSpinnerValues = leaveTypeSpinner.getItemAtPosition(position).toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }

    public void applyForLeaveBtn(View view) {
        UserSession userSession = new UserSession(getApplicationContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");

        TextView textView = (TextView) leaveTypeSpinner.getSelectedView();
        String leaveTypeSpinner12 = textView.getText().toString();
        TextView textViewTime = (TextView) timePeriodSpinner.getSelectedView();
        String timePeriodSpinner12 = textViewTime.getText().toString();
        TextView textViewLeaveDuration = (TextView) leaveDurationSpinner.getSelectedView();
        String leaveDurationSpinner12 = textViewLeaveDuration.getText().toString();
        String reasonForLeave = reasonForLeaveET.getText().toString();
        String startDatePicker = startDate.getText().toString();
        String endDatePicker = endDate.getText().toString();

        if (leaveTypeSpinner12.equals("Please Select Leave Type")) {
            Toast.makeText(this, "Please select leave type", Toast.LENGTH_SHORT).show();

        } else if (reasonForLeave.isEmpty()) {
            Toast.makeText(this, "Please select reason for leave", Toast.LENGTH_SHORT).show();

        } else if (timePeriodSpinner12.equals("Please Select Option")) {
            Toast.makeText(this, "Please select time period", Toast.LENGTH_SHORT).show();

        } else if (timePeriodSpinner12.equals("Half Leave") && startDatePicker.isEmpty()) {
            Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show();

        } else if (leaveDurationSpinner12.equals("Please Select Leave Duration") && timePeriodSpinner12.equals("Full Leave")) {
            Toast.makeText(this, "Please select leave duration", Toast.LENGTH_SHORT).show();

        } else if (timePeriodSpinner12.equals("Full Leave") && leaveDurationSpinner12.equals("Only One Day") && startDatePicker.isEmpty()) {
            Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show();

        } else if (leaveDurationSpinner12.equals("Multiple Days") && startDatePicker.isEmpty()) {
            Toast.makeText(this, "Please select start date", Toast.LENGTH_SHORT).show();

        } else if (leaveDurationSpinner12.equals("Multiple Days") && endDatePicker.isEmpty()) {
            Toast.makeText(this, "Please select end date", Toast.LENGTH_SHORT).show();

        } else if (leaveDurationSpinner12.equals("Multiple Days") && startDatePicker.equals(endDatePicker)) {
            Toast.makeText(this, "Please select valid start and end date", Toast.LENGTH_SHORT).show();

        } else {
            Call<ApplyForLeave> call = Controller.getInstance().getApi().applyForLeaveModal(leaveTypeSpinnerValues, reasonForLeave, timePeriodSpinnerValues, startDatePicker, endDatePicker, "Bearer " + ACCESS_TOKEN);
            call.enqueue(new Callback<ApplyForLeave>() {
                @Override
                public void onResponse(@NonNull Call<ApplyForLeave> call, @NonNull Response<ApplyForLeave> response) {
                    assert response.body() != null;
                    if (response.body().getStatus().equals("success")) {
                        Toast.makeText(ApplyForLeaveActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        reasonForLeaveET.setText("");
                        startDate.setText("");
                        endDate.setText("");
                        leaveTypeSpinner.setSelection(0);
                        timePeriodSpinner.setSelection(0);
                        leaveDurationSpinner.setSelection(0);
                    } else if (response.body().getStatus().equals("error")) {
                        Toast.makeText(ApplyForLeaveActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ApplyForLeave> call, @NonNull Throwable t) {
                    Toast.makeText(ApplyForLeaveActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        }

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}

