package com.rev9solutions.aljadi_employee_dashboard.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.activities.ApplyForLeaveActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    private static final String KEY_CHRONOMETER_ELAPSED_TIME = "chronometerElapsedTime";
    private static final String KEY_CHRONOMETER_STOPPED_TIME = "chronometerStoppedTime";
    long timeWhenStopped;
    private Chronometer chronometer;
    private SharedPreferences prefs;

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
        chronometer = v.findViewById(R.id.chronometer4);
//        datePicker = v.findViewById(R.id.datePickerTest);
//        leaveTypeSpinner = v.findViewById(R.id.spinner12);
//        timePeriodSpinner = v.findViewById(R.id.spinner21);
//        leaveDurationSpinner = v.findViewById(R.id.spinner31);
        timer = new Timer();
        Button startButton = v.findViewById(R.id.btnStart);
        Button stopButton = v.findViewById(R.id.btnStop);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setElapsedTime(-1);
                setStoppedTime(-1);
                chronometer.setBase(SystemClock.elapsedRealtime() );
                chronometer.start();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setElapsedTime(-1);
                setStoppedTime(-1);
                // chronometer.setBase(SystemClock.elapsedRealtime());
                timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
                chronometer.stop();
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        prefs = requireContext().getSharedPreferences("chronometer", MODE_PRIVATE);
        if (prefs.contains(KEY_CHRONOMETER_ELAPSED_TIME)
                && prefs.contains(KEY_CHRONOMETER_STOPPED_TIME)) {
            long chronometerElapsedTime = prefs.getLong(KEY_CHRONOMETER_ELAPSED_TIME, -1);
            long chronometerStoppedTime = prefs.getLong(KEY_CHRONOMETER_STOPPED_TIME, -1);
            long chronometerStoppedTime2 = prefs.getLong(KEY_CHRONOMETER_STOPPED_TIME, 0);
            if (chronometerElapsedTime != -1 && chronometerStoppedTime != -1) {
                long now = System.currentTimeMillis();
                long elapsedTimeFromLastStop = now - chronometerStoppedTime; // Including restart time
                long elapsedRealTime = SystemClock.elapsedRealtime();
                long base = elapsedRealTime - (chronometerElapsedTime + elapsedTimeFromLastStop);
                chronometer.setBase(base);
                chronometer.start();
            }

        }

    }

    @Override
    public void onStop() {
        setElapsedTime(getChronometerTimeMs());
        setStoppedTime(System.currentTimeMillis());
        super.onStop();
    }

    private void setElapsedTime(long elapsedTimeMs) {
        prefs.edit().putLong(KEY_CHRONOMETER_ELAPSED_TIME, elapsedTimeMs).apply();
    }

    private void setStoppedTime(long stoppedTimeMs) {
        prefs.edit().putLong(KEY_CHRONOMETER_STOPPED_TIME, stoppedTimeMs).apply();
    }

    private long getChronometerTimeMs() {
        long chronometerTimeMs = 0;

        // Regex for HH:MM:SS or MM:SS
        String regex = "([0-1]?\\d|2[0-3])(?::([0-5]?\\d))?(?::([0-5]?\\d))?";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chronometer.getText());
        if (matcher.find()) {
            boolean isHHMMSSFormat = matcher.groupCount() == 4;
            if (isHHMMSSFormat) {
                int hour = Integer.valueOf(matcher.group(1));
                int minute = Integer.valueOf(matcher.group(2));
                int second = Integer.valueOf(matcher.group(3));
                chronometerTimeMs = (hour * DateUtils.HOUR_IN_MILLIS)
                        + (minute * DateUtils.MINUTE_IN_MILLIS)
                        + (second * DateUtils.SECOND_IN_MILLIS);
            } else {
                int minute = Integer.valueOf(matcher.group(1));
                int second = Integer.valueOf(matcher.group(2));
                chronometerTimeMs = (minute * DateUtils.MINUTE_IN_MILLIS)
                        + (second * DateUtils.SECOND_IN_MILLIS);
            }
        }

        return chronometerTimeMs;
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
