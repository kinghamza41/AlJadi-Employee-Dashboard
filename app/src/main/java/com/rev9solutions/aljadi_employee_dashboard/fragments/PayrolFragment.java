package com.rev9solutions.aljadi_employee_dashboard.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rev9solutions.aljadi_employee_dashboard.R;

import java.util.Timer;
import java.util.TimerTask;


public class PayrolFragment extends Fragment {
    TextView timerText;
    Button startStopBtn;
    boolean timeStarted = false;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    Runnable runnable;

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
//        timerText = v.findViewById(R.id.timerText);
//        startStopBtn = v.findViewById(R.id.startStopBtn);
        timer = new Timer();
        startStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timeStarted == false) {
                    timeStarted = true;
                   // startStopBtn.setText("Stop");
                    
                    startTimer();
                } else {
                    timeStarted = false;
                    // startStopBtn.setText("Start");
                    timerTask.cancel();
                }
            }
        });
        return v;

    }

    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {

                time++;
                //timerText.setText(getTimerText());
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);

    }

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
}