package com.example.baseballmanagementapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.baseballmanagementapp.databinding.ActivityCreateEventBinding;
import com.google.android.material.tabs.TabLayout;

public class CreateEventActivity extends AppCompatActivity {
    ActivityCreateEventBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCreateEventBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;

                String teamId = getIntent().getStringExtra("teamId");
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new GameCreateFragment(teamId);
                        break;
                    case 1:
                        fragment = new PracticeCreateFragment(teamId);
                        break;
                    case 2:
                        fragment = new NormalEventCreateFragment(teamId);
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}