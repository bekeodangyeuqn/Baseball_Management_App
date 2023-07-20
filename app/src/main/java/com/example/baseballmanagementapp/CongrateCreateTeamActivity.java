package com.example.baseballmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baseballmanagementapp.databinding.ActivityCongrateCreateTeamBinding;

public class CongrateCreateTeamActivity extends AppCompatActivity {
    ActivityCongrateCreateTeamBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCongrateCreateTeamBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.createEventBtn.setOnClickListener(view -> {
            String teamId = getIntent().getStringExtra("teamId");
            Log.d("app", teamId);
            Intent intent = new Intent(CongrateCreateTeamActivity.this, CreateEventActivity.class);
            intent.putExtra("teamId", teamId);
            startActivity(intent);
        });

        binding.addMembersBtn.setOnClickListener(view -> {
            String teamId = getIntent().getStringExtra("teamId");
            Intent intent = new Intent(CongrateCreateTeamActivity.this, AddMemberActivity.class);
            intent.putExtra("teamId", teamId);
            startActivity(intent);
        });
    }
}