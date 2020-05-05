package com.example.mysmartcity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.mysmartcity.Fragment.ComplaintsFragment;
import com.example.mysmartcity.Fragment.DashboardFragment;
import com.example.mysmartcity.Fragment.EventsFragment;
import com.example.mysmartcity.Fragment.ProfileFragment;
import com.example.mysmartcity.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity
{
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        progressBar=findViewById(R.id.progress);
        bottomNavigationView=findViewById(R.id.bottomMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(navItem);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new DashboardFragment()).commit();


    }

    public BottomNavigationView.OnNavigationItemSelectedListener navItem=new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
        {
            progressBar.setVisibility(View.VISIBLE);
            int id=menuItem.getItemId();
            Fragment selectedFragment=null;
            switch (id)
            {
                case R.id.dashboard:
                {
                    selectedFragment=new
                            DashboardFragment();
                    progressBar.setVisibility(View.GONE);
                   // HomeScreen.super.onBackPressed();
                    break;
                }
                case R.id.events:
                {
                    selectedFragment=new EventsFragment();
                    break;
                }
                case R.id.complaints:
                {
                    selectedFragment=new ComplaintsFragment();
                    break;
                }
                case R.id.profile:
                {
                    selectedFragment=new ProfileFragment();
                    break;
                }

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,selectedFragment).commit();
            return true;
        }
    };
}
