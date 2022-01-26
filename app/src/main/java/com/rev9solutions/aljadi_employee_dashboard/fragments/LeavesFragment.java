package com.rev9solutions.aljadi_employee_dashboard.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rev9solutions.aljadi_employee_dashboard.APIIntegration.Controller;
import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.SessionManager.UserSession;
import com.rev9solutions.aljadi_employee_dashboard.modal.LeavesModal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeavesFragment extends Fragment {
    TextView total_leaves, pending_leaves, approved_leaves, rejected_leaves, mid;
    ProgressBar total_leaves_pb, pending_leaves_pb, approved_rejected_pb;
    CardView applyForLeave;

    public LeavesFragment() {
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
        View v = inflater.inflate(R.layout.fragment_leaves, container, false);
        total_leaves = v.findViewById(R.id.total_leaves);
        pending_leaves = v.findViewById(R.id.pending_leaves);
        approved_leaves = v.findViewById(R.id.approved_leaves);
        rejected_leaves = v.findViewById(R.id.rejected_leaves);
        mid = v.findViewById(R.id.mid);
        total_leaves_pb = v.findViewById(R.id.total_leaves_pb);
        pending_leaves_pb = v.findViewById(R.id.pending_leaves_pb);
        approved_rejected_pb = v.findViewById(R.id.approved_rejected_pb);
        applyForLeave = v.findViewById(R.id.applyForLeave);

        UserSession userSession = new UserSession(getContext());
        Log.v("msg2", userSession.GetKeyValue("id"));
        String id = userSession.GetKeyValue("id");

        Call<LeavesModal> call4 = Controller.getInstance().getApi().leavesModal(id);
        call4.enqueue(new Callback<LeavesModal>() {
            @Override
            public void onResponse(@NonNull Call<LeavesModal> call, @NonNull Response<LeavesModal> response) {
                assert response.body() != null;
                if (response.body().getStatusCode() == 200) {
                    Log.v("msg", String.valueOf(response.body().getData().getTotalLeaves()));
                    total_leaves_pb.setVisibility(View.GONE);
                    pending_leaves_pb.setVisibility(View.GONE);
                    approved_rejected_pb.setVisibility(View.GONE);
                    total_leaves.setText(String.valueOf(response.body().getData().getTotalLeaves()));
                    pending_leaves.setText(String.valueOf(response.body().getData().getPendingLeaves()));
                    approved_leaves.setText(String.valueOf(response.body().getData().getApprovedLeaves()));
                    mid.setVisibility(View.VISIBLE);
                    rejected_leaves.setText(String.valueOf(response.body().getData().getRejectedLeaves()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<LeavesModal> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        applyForLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.fragment_leaves, null);
                EditText editText;
                AppCompatButton button;
//                editText = dialogView.findViewById(R.id.et_rejection);
//                button = dialogView.findViewById(R.id.reject_btn);
                builder.setTitle("Reason for Rejection");
                builder.setView(dialogView);
                builder.setCancelable(true);
                final AlertDialog dialog = builder.create();
            }
        });


        return v;
    }

    private void applyForLeave() {

    }
}