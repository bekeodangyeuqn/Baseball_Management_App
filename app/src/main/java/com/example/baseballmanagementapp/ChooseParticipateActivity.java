package com.example.baseballmanagementapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baseballmanagementapp.databinding.ActivityChooseParticipateBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ChooseParticipateActivity extends AppCompatActivity {
    ActivityChooseParticipateBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseParticipateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.createTeamBtn.setClickable(true); // Enable click events
        binding.createTeamBtn.setFocusable(true); // Enable focus events
        binding.createTeamBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ChooseParticipateActivity.this, CreateTeamActivity.class);
            startActivity(intent);
        });
        binding.joinTeamBtn.setClickable(true);
        binding.joinTeamBtn.setFocusable(true);
        binding.joinTeamBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ChooseParticipateActivity.this, JoinTeamActivity.class);
            startActivity(intent);
        });
        binding.logoutBtn.setOnClickListener(view -> {
            auth.signOut();
            Intent intent = new Intent(ChooseParticipateActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }
}