package com.example.baseballmanagementapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baseballmanagementapp.databinding.ActivityCreateTeamBinding;
import com.example.baseballmanagementapp.models.Manager;
import com.example.baseballmanagementapp.models.Team;
import com.example.baseballmanagementapp.models.UserTeam;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.UUID;

public class CreateTeamActivity extends AppCompatActivity {
    ActivityCreateTeamBinding binding;
    ProgressDialog pd;
//    static Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTeamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase database = FirebaseDatabase.
                getInstance("https://baseball-management-app-default-rtdb.asia-southeast1.firebasedatabase.app");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(CreateTeamActivity.this);
        pd.setTitle("Creating your team");
        pd.setMessage("We are creating team for you");
        final Manager[] manager = new Manager[1];

        binding.createSubmitBtn.setOnClickListener(view -> {
            pd.show();
            UUID uuid = UUID.randomUUID();
            String teamId =String.valueOf(uuid);
            Team team = new Team(binding.etTeamName.getText().toString());
            DatabaseReference ref = database.getReference();
            String uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
            Log.d("app", "Value is: " + uid);
            ref.child("User").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Log.d("app",snapshot.getValue().toString());
                    final Manager manager1 = snapshot.getValue(Manager.class);
                    manager[0] = manager1;
                    Log.d("app", manager[0].getFirstname() + " " + manager[0].getLastname());
                    UserTeam userTeam = new UserTeam();
                    userTeam.setTeamId(teamId);
                    userTeam.setUserId(uid);
                    userTeam.setRole("manager");
                    userTeam.setStatus("active");
                    DatabaseReference userTeamRef= ref.child("UserTeam").push();
                    userTeamRef.setValue(userTeam);
                    ref.child("Team").child(teamId).setValue(team);
                    ref.child("Team").child(teamId).child("Managers").child(uid).setValue(true);
                    ref.child("User").child(uid).child("Teams").child(teamId).setValue(true);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            pd.dismiss();
//            ref.child("Team").child(teamId).setValue(team);
//            //Log.d("app", manager.getFirstname() + " " + manager.getLastname());
//            ref.child("User").child(uid).child("Teams").child(teamId).setValue(team);
            Intent intent = new Intent(CreateTeamActivity.this, CongrateCreateTeamActivity.class);
            intent.putExtra("teamId", teamId);
            startActivity(intent);
        });
    }
}