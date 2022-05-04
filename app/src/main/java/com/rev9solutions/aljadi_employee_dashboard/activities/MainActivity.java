package com.rev9solutions.aljadi_employee_dashboard.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.LoginRequest;
import com.rev9solutions.aljadi_employee_dashboard.SessionManager.UserSession;
import com.rev9solutions.aljadi_employee_dashboard.fragments.AttendanceRequestFragment;
import com.rev9solutions.aljadi_employee_dashboard.fragments.LeavesFragment;
import com.rev9solutions.aljadi_employee_dashboard.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView userEmail;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new LeavesFragment()).commit();
            navigationView.setCheckedItem(R.id.Leaves);

        }

    }

    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private boolean isConnected(MainActivity loginActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());

    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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

                    }
                }).show();

    }

    private void logoutOperation() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Logout Request")
                .setMessage("Are you sure you want to Logout?")
//                            .setView(R.layout.popup_window)


                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        checkUserExistence();
                    }
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void checkUserExistence() {
        UserSession userSession = new UserSession(getApplicationContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ACCESS_TOKEN != null) {
                    LoginRequest loginRequest = new LoginRequest();
                    String sharedProfileName = "haccount";
                    SharedPreferences preferences = getSharedPreferences(sharedProfileName, Context.MODE_PRIVATE);
                    preferences.edit().remove("access_token").apply();
                    preferences.edit().remove(loginRequest.getEmail()).apply();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 300);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Are you sure you want to Exit?")
//                            .setView(R.layout.popup_window)

                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.super.onBackPressed();
                        }
                    })

                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment temp = null;
        switch (item.getItemId()) {

            case R.id.Leaves:

                Toast.makeText(MainActivity.this, "Leaves Panel is open", Toast.LENGTH_SHORT).show();
                temp = new LeavesFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();

                break;
            case R.id.attendance:
                temp = new AttendanceRequestFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();

                break;
            case R.id.logout:

                try {
                    if (!isConnected(this)) {
                        showCustomDialog();
                    } else {
                        logoutOperation();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}