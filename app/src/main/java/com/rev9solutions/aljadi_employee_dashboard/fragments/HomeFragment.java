package com.rev9solutions.aljadi_employee_dashboard.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rev9solutions.aljadi_employee_dashboard.APIIntegration.Controller;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.Data;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.User;
import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.SessionManager.UserSession;
import com.rev9solutions.aljadi_employee_dashboard.modal.ArrivalTimeModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.LeavesModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.StartEndTimeModal;
import com.rev9solutions.aljadi_employee_dashboard.modal.WorkingHoursModal;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    TextView startTime, endTime, workingHours, arrivalTime;
    ProgressBar startEndTimePB, workingHoursPB, arrivalTimePB;
    ImageView imgOnline, imgOffline;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        startTime = (TextView) v.findViewById(R.id.startTime);
        endTime = (TextView) v.findViewById(R.id.endTime);
        startEndTimePB = (ProgressBar) v.findViewById(R.id.startEndTimePB);
        workingHoursPB = v.findViewById(R.id.workingHoursPB);
        workingHours = v.findViewById(R.id.workingHours);
        imgOnline = v.findViewById(R.id.imgOnline);
        imgOffline = v.findViewById(R.id.imgOffline);
        arrivalTime = v.findViewById(R.id.arrivalTime);
        arrivalTimePB = v.findViewById(R.id.arrivalTimePB);
        //StartEndTimeResponse startEndTimeResponse = new StartEndTimeResponse(user);
        //user = new User(182);

        UserSession userSession = new UserSession(getContext());
        Log.v("msg2", userSession.GetKeyValue("id"));
        String id = userSession.GetKeyValue("id");
//        userSession.SaveCredentials("token");
        Call<StartEndTimeModal> call1 = Controller.getInstance().getApi().startEndTime(id);
        call1.enqueue(new Callback<StartEndTimeModal>() {
            @Override
            public void onResponse(@NonNull Call<StartEndTimeModal> call, @NonNull Response<StartEndTimeModal> response) {
                assert response.body() != null;

                if (response.body().getStatusCode() == 200) {
                    Log.v("msg", response.body().getData().getGeneralShiftEndTime());
                    startEndTimePB.setVisibility(View.GONE);
                    // Toast.makeText(getContext(), response.body().getStartEndTimeModal().getStartTime(), Toast.LENGTH_LONG).show();
                    //Log.v("msg1",response.body().getStartEndTimeModal().getEndTime())
                    startTime.setText(response.body().getData().getGeneralShiftStartTime());
                    endTime.setText(response.body().getData().getGeneralShiftEndTime());

                }

            }

            @Override
            public void onFailure(@NonNull Call<StartEndTimeModal> call, @NonNull Throwable t) {
                Log.v("msg", t.getLocalizedMessage());
                // Toast.makeText(getContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

        Call<WorkingHoursModal> call = Controller.getInstance().getApi().workingHoursModal(id);
        call.enqueue(new Callback<WorkingHoursModal>() {
            @Override
            public void onResponse(Call<WorkingHoursModal> call, Response<WorkingHoursModal> response) {
                assert response.body() != null;
                if (response.body().getStatusCode() == 200) {
                    String value = String.valueOf(response.body().getData().get(0).getPivot().getWorkingHours());
                    Log.v("msg3", value);
                    workingHoursPB.setVisibility(View.GONE);
                    workingHours.setText(value);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WorkingHoursModal> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<ArrivalTimeModal> call3 = Controller.getInstance().getApi().arrivalTimeModal(id);
        call3.enqueue(new Callback<ArrivalTimeModal>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ArrivalTimeModal> call, @NonNull Response<ArrivalTimeModal> response) {
                assert response.body() != null;
                if (response.body().getStatusCode() == 200) {
                    String offline = "offline";
                    Log.v("msg5", response.body().getData());
                    if (response.body().getData().equals(offline)) {
                        arrivalTimePB.setVisibility(View.GONE);
                        imgOffline.setVisibility(View.VISIBLE);
                        arrivalTime.setText("Offline");
                    } else {
                        arrivalTimePB.setVisibility(View.GONE);
                        imgOnline.setVisibility(View.VISIBLE);
                        arrivalTime.setText("Online");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrivalTimeModal> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });





        return v;


    }

}