package com.rev9solutions.aljadi_employee_dashboard.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rev9solutions.aljadi_employee_dashboard.APIIntegration.Controller;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.Data;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.LoginRequest;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.User;
import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.SessionManager.UserSession;
import com.rev9solutions.aljadi_employee_dashboard.response.LoginResponse;
import com.rev9solutions.aljadi_employee_dashboard.response.StartEndTimeResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    TextView startTime;
    User user = new User();
    Data data = new Data();
    int id;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        startTime = (TextView) v.findViewById(R.id.startEndTime);
        //StartEndTimeResponse startEndTimeResponse = new StartEndTimeResponse(user);
        //user = new User(182);

        UserSession userSession = new UserSession(getContext());
        Log.v("msg2", userSession.GetKeyValue("id")) ;
        int id  = Integer.parseInt(userSession.GetKeyValue("id"));
//        userSession.SaveCredentials("token");
        Call<StartEndTimeResponse> call1 = Controller.getInstance().getApi().startEndTime(id);
        call1.enqueue(new Callback<StartEndTimeResponse>() {
            @Override
            public void onResponse(@NonNull Call<StartEndTimeResponse> call, @NonNull Response<StartEndTimeResponse> response) {
                assert response.body() != null;

                if (response.body().getStatusCode() == 200) {
                    Log.v("msg",String.valueOf(response.body().getStartEndTimeModal().getStartTime()) );
                    // Toast.makeText(getContext(), response.body().getStartEndTimeModal().getStartTime(), Toast.LENGTH_LONG).show();
                    //Log.v("msg1",response.body().getStartEndTimeModal().getEndTime())
                    startTime.setText(response.body().getStartEndTimeModal().getStartTime());
                }


            }

            @Override
            public void onFailure(@NonNull Call<StartEndTimeResponse> call, @NonNull Throwable t) {
                Log.v("msg", t.getLocalizedMessage());
                Toast.makeText(getContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
        LoginRequest loginRequest = new LoginRequest();
        Call<LoginResponse> call = Controller.getInstance().getApi().verifyUser(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                assert response.body() != null;
                if (response.body().getStatusCode() == 200) {
                    LoginResponse loginResponse = response.body();
                  //  Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                 //   dialog.dismiss();


                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
               // Toast.makeText(LoginActivity.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // int id = user.getId();


        return v;


    }

}