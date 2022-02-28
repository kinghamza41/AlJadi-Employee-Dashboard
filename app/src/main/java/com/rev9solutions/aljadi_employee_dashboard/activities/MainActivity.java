package com.rev9solutions.aljadi_employee_dashboard.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.rev9solutions.aljadi_employee_dashboard.LoginApiData.LoginRequest;
import com.rev9solutions.aljadi_employee_dashboard.SessionManager.UserSession;
import com.rev9solutions.aljadi_employee_dashboard.fragments.HomeFragment;
import com.rev9solutions.aljadi_employee_dashboard.fragments.LeavesFragment;
import com.rev9solutions.aljadi_employee_dashboard.R;
import com.rev9solutions.aljadi_employee_dashboard.fragments.PayrolFragment;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
         getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
         navigationView.setCheckedItem(R.id.Home);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;

            @SuppressLint("NonConstantResourceId")
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.Home:

                        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
//                                HomeFragment homeFragment = new HomeFragment();
//                                homeFragment.dashboardModalRefresh();
//                                swipeContainer.setRefreshing(false);
                                //  Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                            }
                        });
                        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                                android.R.color.holo_green_light,
                                android.R.color.holo_orange_light,
                                android.R.color.holo_red_light);
                        Toast.makeText(MainActivity.this, "Home Panel is open", Toast.LENGTH_SHORT).show();
                        temp = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();

                        break;

                    case R.id.Leaves:

                        Toast.makeText(MainActivity.this, "Leaves Panel is open", Toast.LENGTH_SHORT).show();
                        temp = new LeavesFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();

                        break;
                    case R.id.payroll:
                        temp = new PayrolFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();

                        break;
                    case R.id.logout:

                        try {
                            logoutOperation();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void logoutOperation() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Logout Request")
                .setMessage("Are you sure you want to Logout?")
//                            .setView(R.layout.popup_window)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        checkUserExistence();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public void leavesTodayCardView(View view) {
    }

    public void presentCardView(View view) {
    }

    public void absentCardView(View view) {
    }

    public void pendingCardView(View view) {
    }

    private void checkUserExistence() {
        UserSession userSession = new UserSession(getApplicationContext());
        String ACCESS_TOKEN = userSession.GetKeyValue("access_token");

        if (ACCESS_TOKEN != null) {
            LoginRequest loginRequest = new LoginRequest();
            String sharedProfileName = "haccount";
            SharedPreferences preferences = getSharedPreferences(sharedProfileName, Context.MODE_PRIVATE);
            preferences.edit().remove("access_token").apply();
            preferences.edit().remove(loginRequest.getEmail()).apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Are you sure you want to Exit?")
//                            .setView(R.layout.popup_window)

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            finish();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
    }
}