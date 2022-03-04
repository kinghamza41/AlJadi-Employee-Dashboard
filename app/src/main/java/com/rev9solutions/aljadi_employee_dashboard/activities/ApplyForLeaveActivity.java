package com.rev9solutions.aljadi_employee_dashboard.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.rev9solutions.aljadi_employee_dashboard.APIIntegration.Controller;
import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.SessionManager.UserSession;
import com.rev9solutions.aljadi_employee_dashboard.modal.ApplyForLeave;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ApplyForLeaveActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    Toolbar toolbar;
    Spinner leaveTypeSpinner, timePeriodSpinner, leaveDurationSpinner;
    EditText datePicker, datePicker2, datePicker3, datePicker4;
    AppCompatButton applyForLeaveBtn;
    DatePickerDialog.OnDateSetListener date;

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
        datePicker = findViewById(R.id.datePicker);
        datePicker2 = findViewById(R.id.datePicker2);
        datePicker3 = findViewById(R.id.datePicker3);
        datePicker4 = findViewById(R.id.datePicker4);
        applyForLeaveBtn = findViewById(R.id.applyForLeaveBtn);

        String[] leaveType = getResources().getStringArray(R.array.leaveType);
        String[] timePeriod = getResources().getStringArray(R.array.timePeriod);
        String[] leaveDuration = getResources().getStringArray(R.array.leaveDuration);

        ArrayAdapter<String> leaveTypeAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, leaveType) {
            public int getCount() {
                int c = super.getCount();
//                if (spinner.getSelectedItemPosition() < c - 1) {
//                    return c;
//                }
//                return c > 0 ? c - 1 : c;
                return c;
            }
        };
        leaveTypeAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        leaveTypeSpinner.setAdapter(leaveTypeAdapter);
        // spinner.setSelection(leaveType.length - 1);


        ArrayAdapter<String> timePeriodAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, timePeriod) {
            public int getCount() {
                int c = super.getCount();
//                if (timePeriodSpinner.getSelectedItemPosition() < c - 1) {
//                    return c;
//                }
//                return c > 0 ? c - 1 : c;
                return c;
            }
        };
        timePeriodAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        timePeriodSpinner.setAdapter(timePeriodAdapter);
        // timePeriodSpinner.setSelection(timePeriod.length - 1);

        timePeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int position, long id) {
//                int selected = timePeriodSpinner.getSelectedItemPosition();
//                if (position==selected){
//                    spinner2.setBackgroundColor(Color.GRAY);
//                }
                if (position == 2) {
                    leaveDurationSpinner.setVisibility(View.VISIBLE);
                    datePicker.setVisibility(View.GONE);
                } else if (position == 1) {
                    leaveDurationSpinner.setVisibility(View.GONE);
                    datePicker.setVisibility(View.VISIBLE);

                } else {
                    leaveDurationSpinner.setVisibility(View.GONE);
                    datePicker.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        ArrayAdapter<String> leaveDurationAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, leaveDuration) {
            public int getCount() {
                int c = super.getCount();
//                if (spinner.getSelectedItemPosition() < c - 1) {
//                    return c;
//                }
//                return c > 0 ? c - 1 : c;
                return c;
            }
        };
        leaveDurationAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        leaveDurationSpinner.setAdapter(leaveDurationAdapter);
        leaveDurationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 1) {
                    datePicker2.setVisibility(View.VISIBLE);
                    datePicker3.setVisibility(View.GONE);
                    datePicker4.setVisibility(View.GONE);
                } else if (position == 2) {
                    datePicker2.setVisibility(View.GONE);
                    datePicker3.setVisibility(View.VISIBLE);
                    datePicker4.setVisibility(View.VISIBLE);
                } else {
                    datePicker2.setVisibility(View.GONE);
                    datePicker3.setVisibility(View.GONE);
                    datePicker4.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        datePickerDialog1();
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ApplyForLeaveActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ApplyForLeaveActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        datePicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ApplyForLeaveActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        datePicker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ApplyForLeaveActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        datePicker4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ApplyForLeaveActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//        datePickerDialog2();
    }

    private void datePickerDialog1() {
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };


    }
//    private void datePickerDialog2() {
//        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, month);
//                myCalendar.set(Calendar.DAY_OF_MONTH, day);
//                updateLabel();
//            }
//        };
//        datePicker2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(ApplyForLeaveActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        datePicker.setText(dateFormat.format(myCalendar.getTime()));
        datePicker2.setText(dateFormat.format(myCalendar.getTime()));
        datePicker3.setText(dateFormat.format(myCalendar.getTime()));
        datePicker4.setText(dateFormat.format(myCalendar.getTime()));

    }

    public void applyForLeaveBtn(View view) {
        UserSession userSession = new UserSession(getApplicationContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
       // Call<ApplyForLeave> call = Controller.getInstance().getApi().applyForLeaveModal();
    }
}