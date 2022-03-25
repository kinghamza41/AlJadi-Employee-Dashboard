package com.rev9solutions.aljadi_employee_dashboard.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
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
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.LoginRequest;
import com.rev9solutions.aljadi_employee_dashboard.SessionManager.UserSession;
import com.rev9solutions.aljadi_employee_dashboard.fragments.AttendanceRequestFragment;
import com.rev9solutions.aljadi_employee_dashboard.fragments.HomeFragment;
import com.rev9solutions.aljadi_employee_dashboard.fragments.LeavesFragment;
import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.fragments.PayrolFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView userEmail;
    FusedLocationProviderClient fusedLocationProviderClient;
    UserSession userSession = new UserSession(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        userEmail = findViewById(R.id.uEmail);
        String uEmail = userSession.GetKeyValue("email");
//        userEmail.setText("dsfdsf");
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.Home);

//            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    HomeFragment homeFragment = new HomeFragment();
//                    homeFragment.dashboardModal2();
//                    Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
//                    swipeContainer.setRefreshing(false);
//                }
//
//            });
//
//            swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                    android.R.color.holo_green_light,
//                    android.R.color.holo_orange_light,
//                    android.R.color.holo_red_light);
//        }

        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, "android.permission.ACCESS_FINE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(MainActivity.this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            MainActivity.this.getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 100);
        }
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] + grantResults[1] == 0) {
            getCurrentLocation();
        } else {
            Toast.makeText(getApplicationContext(), "Permission denied.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        UserSession userSession = new UserSession(getApplicationContext());
                        userSession.SaveKeyValue("latitude", String.valueOf(location.getLatitude()));
                        userSession.SaveKeyValue("longitude", String.valueOf(location.getLongitude()));
                        Log.v("lat", String.valueOf(location.getLatitude()));
                        Log.v("long", String.valueOf(location.getLongitude()));
//                        MainActivity.this.tvLatitude.setText(String.valueOf(location.getLatitude()));
//                        MainActivity.this.tvLongitude.setText(String.valueOf(location.getLongitude()));

                    } else {
                        // When location result is null
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                Location location1 = locationResult.getLastLocation();
                                Log.v("lat1", String.valueOf(location1.getLatitude()));
                                Log.v("long1", String.valueOf(location1.getLongitude()));
                            }
                        };
                        //Request Location Update
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });
        } else {
            // When location service is not enabled
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
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
            case R.id.Home:
                Toast.makeText(MainActivity.this, "Home Panel is open", Toast.LENGTH_SHORT).show();
                temp = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();

                break;

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