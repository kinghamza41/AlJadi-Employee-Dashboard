package com.rev9solutions.aljadi_employee_dashboard.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rev9solutions.aljadi_employee_dashboard.R;

public class LoginActivity extends AppCompatActivity {
    EditText t1, t2;
    AppCompatButton saveBtn;
    TextView tv;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        tv = findViewById(R.id.tv);
        saveBtn = findViewById(R.id.savebtn);
        //checkUserExistence();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                //processLogin();
            }
        });
    }
}