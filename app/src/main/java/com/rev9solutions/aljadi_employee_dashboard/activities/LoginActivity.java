package com.rev9solutions.aljadi_employee_dashboard.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.rev9solutions.aljadi_employee_dashboard.APIIntegration.Controller;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.LoginRequest;
import com.rev9solutions.aljadi_employee_dashboard.response.LoginResponse;
import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.SessionManager.UserSession;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText t1, t2;
    AppCompatButton saveBtn;
    TextView tv;
    ProgressDialog dialog;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private static final String NOTIFICATION_CHANNEL_ID = "100002";
    UserSession userSession;
    String ACCESS_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        tv = findViewById(R.id.tv);
        saveBtn = findViewById(R.id.savebtn);
        //checkUserExistence();
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//                if (!isConnected(v)) {
//                    showCustomDialog();
//                }
//
//            }
//        });


        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");

//        Pusher pusher = new Pusher("2d100a8c74ff5472530d", options);
//
//        pusher.connect(new ConnectionEventListener() {
//            @Override
//            public void onConnectionStateChange(ConnectionStateChange change) {
//                Log.i("Pusher", "State changed from " + change.getPreviousState() +
//                        " to " + change.getCurrentState());
//            }
//
//            @Override
//            public void onError(String message, String code, Exception e) {
//                Log.i("Pusher", "There was a problem connecting! " +
//                        "\ncode: " + code +
//                        "\nmessage: " + message +
//                        "\nException: " + e
//                );
//            }
//        }, ConnectionState.ALL);
//
//        Channel channel = pusher.subscribe("my-channel");
//
//        channel.bind("my-event", new SubscriptionEventListener() {
//            @Override
//            public void onEvent(PusherEvent event) {
//                String data = event.getData();
//                String nama = "";
//                try {
//                    JSONObject jsonObject = new JSONObject(data);
//                    nama = jsonObject.getString("name");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                mBuilder = new NotificationCompat.Builder(LoginActivity.this);
//                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//                mBuilder.setContentTitle("Al Jadi Notifications Test")
//                        .setContentText(nama)
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
//                mNotificationManager = (NotificationManager) LoginActivity.this
//                        .getSystemService(Context.NOTIFICATION_SERVICE);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    int importance = NotificationManager.IMPORTANCE_HIGH;
//                    NotificationChannel notificationChannel = new NotificationChannel
//                            (NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
//                    notificationChannel.enableLights(true);
//                    notificationChannel.setLightColor(Color.RED);
//                    notificationChannel.enableVibration(true);
//                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//
//                    assert mNotificationManager != null;
//                    mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
//                    mNotificationManager.createNotificationChannel(notificationChannel);
//                }
//                assert mNotificationManager != null;
//                mNotificationManager.notify(0, mBuilder.build());
//            }
//        });
    }

    public void processLogin(View view) {
        if (!isConnected(this)) {
            showCustomDialog();
        } else {
            processLogin();
        }
    }

    public void processLogin() {

        String email = t1.getText().toString();
        String password = t2.getText().toString();

        if (email.isEmpty()) {

            t1.setError("Enter is Required");
            t1.requestFocus();
            //  dialog.cancel();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            t1.setError("Enter a valid Email");
            t1.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            // dialog.cancel();
            t2.setError("Password Required");
            t2.requestFocus();

        }
        //  dialog.cancel();
        loginResponse();

    }

    private void loginResponse() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(t1.getText().toString());
        loginRequest.setPassword(t2.getText().toString());
        Call<LoginResponse> call = Controller.getInstance().getApi().verifyUser(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                assert response.body() != null;
                if (response.body().getStatusCode() == 200) {
                    LoginResponse loginResponse = response.body();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
//                    Log.d("msg", String.valueOf(response.body().getData().getUser().getId()));
                    UserSession userSession = new UserSession(getApplicationContext());
                    userSession.SaveKeyValue("id", String.valueOf(response.body().getData().getUser().getId()));
                    userSession.SaveKeyValue("email", String.valueOf(response.body().getData().getUser().getEmail()));
                    userSession.SaveKeyValue("token", response.body().getData().getToken());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                                UserSession userSession = new UserSession(getApplicationContext());
//                                ACCESS_TOKEN = userSession.GetKeyValue("access_token");
//                                if (ACCESS_TOKEN != null) {
//                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
                            new UserSession(getApplicationContext()).SaveCredentials(loginResponse.getData().getToken());
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }, 300);
                } else if (response.body().getStatusCode() != 200) {
                    LoginResponse loginResponse = response.body();
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
                        //   loginResponse();
                        //  dialog.dismiss();
                    }
                }).show();

    }

    private boolean isConnected(LoginActivity loginActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());

    }


    public static int saveData(int id) {
        return id;
    }

    @Override

    protected void onStart() {
        super.onStart();
        UserSession userSession = new UserSession(getApplicationContext());
        ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        if (ACCESS_TOKEN != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }


}