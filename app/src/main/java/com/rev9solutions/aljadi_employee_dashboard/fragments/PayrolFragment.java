package com.rev9solutions.aljadi_employee_dashboard.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.activities.ApplyForLeaveActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class PayrolFragment extends Fragment {
    TextView timerText;
    Button startStopBtn;
    boolean timeStarted = false;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    EditText datePicker;
    DatePickerDialog.OnDateSetListener date;
    final Calendar myCalendar = Calendar.getInstance();
    Spinner leaveTypeSpinner, timePeriodSpinner, leaveDurationSpinner;
    String leaveTypeSpinnerValues, timePeriodSpinnerValues, leaveDurationSpinnerValues, reasonForLeave;


    public PayrolFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_payrol, container, false);
//        datePicker = v.findViewById(R.id.datePickerTest);
//        leaveTypeSpinner = v.findViewById(R.id.spinner12);
//        timePeriodSpinner = v.findViewById(R.id.spinner21);
//        leaveDurationSpinner = v.findViewById(R.id.spinner31);
        timer = new Timer();
//        startStopBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (timeStarted == false) {
//                    timeStarted = true;
//                   // startStopBtn.setText("Stop");
//
//                    startTimer();
//                } else {
//                    timeStarted = false;
//                    // startStopBtn.setText("Start");
//                    timerTask.cancel();
//                }
//            }
//        });
//        return v;
//
//    }
//
//    private void startTimer() {
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//
//                time++;
//                //timerText.setText(getTimerText());
//            }
//        };
//        timer.scheduleAtFixedRate(timerTask,0,1000);
//
//    }

//    private String getTimerText() {
//        int rounded = (int) Math.round(time);
//        int seconds = ((rounded % 86400) % 3600) % 60;
//        int minutes = ((rounded % 86400) % 3600) / 60;
//        int hours = ((rounded % 86400) / 3600) ;
//        return formatTime(seconds,minutes,hours);
//    }
//
//    @SuppressLint("DefaultLocale")
//    private String formatTime(int seconds, int minutes, int hours) {
//        return String.format("%02d",hours + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds));
//    }
        //leaveTypeAdapter();
//        timePeriodAdapter();
//        leaveDurationAdapter();
//        startDatePickerDialog();
//        datePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //  Calendar calendar =  myCalendar.add(Calendar.DATE, -1);
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//                datePickerDialog.show();
////                new DatePickerDialog(ApplyForLeaveActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
////                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//
//            }
//        });
//
        return v;
   }
//    private void startDatePickerDialog() {
//        date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                String myFormat = "MM/dd/yy";
//                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, month);
//                myCalendar.set(Calendar.DAY_OF_MONTH, day);
//                updateLabelStart();
//            }
//        };
//    }
//    private void updateLabelStart() {
//        String myFormat = "MM/dd/yy";
//        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
//        datePicker.setText(dateFormat.format(myCalendar.getTime()));
//
//    }
//    private void timePeriodAdapter() {
//        String[] timePeriod = getResources().getStringArray(R.array.timePeriod);
//        //Time Period Adapter
//        ArrayAdapter<String> timePeriodAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item, timePeriod) {
//            public int getCount() {
//                int c = super.getCount();
////                if (timePeriodSpinner.getSelectedItemPosition() < c - 1) {
////                    return c;
////                }
////                return c > 0 ? c - 1 : c;
//                return c;
//            }
//
//            @Override
//            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    return false;
//                } else {
//                    return true;
//                }
//
//            }
//        };
//        timePeriodAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        timePeriodSpinner.setAdapter(timePeriodAdapter);
//
//        //Time Period Spinner
//        timePeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int position, long id) {
//              //  timePeriodSpinnerValues = leaveTypeSpinner.getItemAtPosition(position).toString();
//
//                if (position == 2) {
//                    leaveDurationSpinner.setVisibility(View.VISIBLE);
//                    datePicker.setVisibility(View.GONE);
//                    datePicker.setText("");
//                    // fullLeaveDatePicker.setVisibility(View.VISIBLE);
//                } else if (position == 1) {
//                    leaveDurationSpinner.setVisibility(View.GONE);
//                    datePicker.setVisibility(View.VISIBLE);
//                   // datePicker.setGravity(position);
//                    //endDate.setVisibility(View.GONE);
//
//                } else {
//                    leaveDurationSpinner.setVisibility(View.GONE);
//                    datePicker.setVisibility(View.GONE);
//                    datePicker.setText("");
//                }
//            }
//
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                datePicker.setVisibility(View.GONE);
//                datePicker.setText("");
//                leaveDurationSpinner.setVisibility(View.GONE);
//            }
//        });
//    }
//
//    private void leaveDurationAdapter() {
//        String[] leaveDuration = getResources().getStringArray(R.array.leaveDuration);
//
//        // Leave Duration Adapter
//        ArrayAdapter<String> leaveDurationAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item, leaveDuration) {
//            public int getCount() {
//                int c = super.getCount();
//                return c;
//            }
//
//            @Override
//            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    return false;
//                } else {
//                    return true;
//                }
//            }
//        };
//        leaveDurationAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        leaveDurationSpinner.setAdapter(leaveDurationAdapter);
//        //leaveDurationSpinner.setSelection(leaveDurationSpinner.getFirstVisiblePosition());
//
//        //Leave Duration Spinner
//        leaveDurationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//               // leaveDurationSpinnerValues = leaveTypeSpinner.getItemAtPosition(position).toString();
//
//                if (position == 1) {
//                    datePicker.setVisibility(View.VISIBLE);
//
//                } else if (position == 2) {
//
//                }
//                else {
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//    }
    }
