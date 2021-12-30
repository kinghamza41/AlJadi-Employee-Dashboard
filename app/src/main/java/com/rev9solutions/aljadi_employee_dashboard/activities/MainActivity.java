package com.rev9solutions.aljadi_employee_dashboard.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.rev9solutions.aljadi_employee_dashboard.fragments.HomeFragment;
import com.rev9solutions.aljadi_employee_dashboard.fragments.LeavesFragment;
import com.rev9solutions.aljadi_employee_dashboard.R;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
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
                        Toast.makeText(MainActivity.this, "Home Panel is open", Toast.LENGTH_SHORT).show();
                        temp = new HomeFragment();

                        break;
                    case R.id.Leaves:
                        Toast.makeText(MainActivity.this, "Leaves Panel is open", Toast.LENGTH_SHORT).show();
                        temp = new LeavesFragment();
                        break;
                    case R.id.logout:
                        // checkUserExistence();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.payroll_configuration,menu);
//        return true;
//    }

    public void leavesTodayCardView(View view) {
    }

    public void presentCardView(View view) {
    }

    public void absentCardView(View view) {
    }

    public void pendingCardView(View view) {
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}