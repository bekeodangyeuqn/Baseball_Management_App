package com.example.baseballmanagementapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baseballmanagementapp.databinding.ActivityJoinTeamBinding;
import com.example.baseballmanagementapp.models.Manager;
import com.example.baseballmanagementapp.models.Team;
import com.example.baseballmanagementapp.models.User;
import com.example.baseballmanagementapp.models.UserTeam;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JoinTeamActivity extends AppCompatActivity {
    ActivityJoinTeamBinding binding;
    ProgressDialog pd;
    int checkRole;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityJoinTeamBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        RadioGroup radioGroup = binding.radioGroup;
        String userId =  auth.getUid();

        radioGroup.setOnCheckedChangeListener((radioGroup1, checkedId) -> {
            switch (checkedId) {
                case R.id.select_manager_team:
                    checkRole = 1;
                    break;
                case R.id.select_player_team:
                    checkRole = 2;
                    break;
            }
        });
        FirebaseDatabase database = FirebaseDatabase.
                getInstance("https://baseball-management-app-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference ref = database.getReference();

        pd = new ProgressDialog(JoinTeamActivity.this);
        pd.setTitle("Joining team");
        pd.setMessage("Joining team for you");
        final User[] user = new User[1];
        ref.child("User").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user[0] = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.createSubmitBtn.setOnClickListener(view -> {
            pd.show();
            final Team[] team = new Team[1];
            ref.child("Team").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Team> teams =  new ArrayList<>();
                    if (snapshot.exists()) {
                        for (DataSnapshot findSnapshot : snapshot.getChildren()) {
                            Team team = findSnapshot.getValue(Team.class);
                            teams.add(team);
                        }
                    }
                    for (Team findTeam : teams) {
                        if (findTeam.getTeamId().equals(binding.etTeamId.getText().toString())) {
                            Log.d("app", "Founded team");
                            team[0] = findTeam;
                            if (checkRole == 1) {
                                //Manager manager = (Manager) user[0];
                                ref.child("Team").child(binding.etTeamId.getText().toString()).child("Managers").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        boolean valueExists = false;

                                        for (DataSnapshot findSnapshot : snapshot.getChildren()) {
                                            Manager existingValue = findSnapshot.getValue(Manager.class);
                                            if (existingValue.getUsername().equals(user[0].getUsername())) {
                                                valueExists = true;
                                                break;
                                            }
                                        }

                                        if (valueExists) {
                                            // The value already exists in the database, do not add it
                                            // Handle the case when the value already exists
                                            pd.dismiss();
                                            Toast.makeText(JoinTeamActivity.this, "This manager with this id have already added", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // The value does not exist, add it to the database
                                            // Handle the case when the value does not exist
                                            UserTeam userTeam = new UserTeam();
                                            //userTeam.setId();
                                            userTeam.setTeamId(binding.etTeamId.getText().toString());
                                            userTeam.setUserId(user[0].getUserId());
                                            userTeam.setRole("manager");
                                            userTeam.setStatus("active");
                                            DatabaseReference userTeamRef= ref.child("UserTeam").push();
                                            userTeamRef.setValue(userTeam);
                                            ref.child("Team").child(binding.etTeamId.getText().toString()).child("Managers").child(user[0].getUserId()).setValue(true);
                                            ref.child("User").child(user[0].getUserId()).child("Teams").child(binding.etTeamId.getText().toString()).setValue(true);
                                            pd.dismiss();
                                            Intent intent = new Intent(JoinTeamActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(JoinTeamActivity.this, "Add manager successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            else if (checkRole ==  2) {

                                ref.child("Team").child(binding.etTeamId.getText().toString()).child("Players").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        boolean valueExists = false;
                                        if (snapshot.exists()) {
                                            for (DataSnapshot findSnapshot : snapshot.getChildren()) {
                                                String existingValue = findSnapshot.getKey();
                                                if (existingValue.equals(user[0].getUserId())) {
                                                    valueExists = true;
                                                    break;
                                                }
                                            }

                                            if (valueExists) {
                                                // The value already exists in the database, do not add it
                                                // Handle the case when the value already exists
                                                pd.dismiss();
                                                Toast.makeText(JoinTeamActivity.this, "This player with this id have already added", Toast.LENGTH_SHORT).show();
                                            } else {
                                                // The value does not exist, add it to the database
                                                // Handle the case when the value does not exist
                                                UserTeam userTeam = new UserTeam();
                                                userTeam.setTeamId(binding.etTeamId.getText().toString());
                                                userTeam.setUserId(user[0].getUserId());
                                                userTeam.setRole("player");
                                                userTeam.setStatus("active");
                                                DatabaseReference userTeamRef= ref.child("UserTeam").push();
                                                userTeamRef.setValue(userTeam);
                                                ref.child("Team").child(binding.etTeamId.getText().toString()).child("Players").child(user[0].getUserId()).setValue(true);
                                                ref.child("User").child(user[0].getUserId()).child("Teams").child(binding.etTeamId.getText().toString()).setValue(true);
                                                pd.dismiss();
                                                Intent intent = new Intent(JoinTeamActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(JoinTeamActivity.this, "Add player successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            UserTeam userTeam = new UserTeam();
                                            userTeam.setTeamId(binding.etTeamId.getText().toString());
                                            userTeam.setUserId(user[0].getUserId());
                                            userTeam.setRole("player");
                                            userTeam.setStatus("active");
                                            DatabaseReference userTeamRef= ref.child("UserTeam").push();
                                            userTeamRef.setValue(userTeam);
                                            ref.child("Team").child(binding.etTeamId.getText().toString()).child("Players").child(user[0].getUserId()).setValue(true);
                                            ref.child("User").child(user[0].getUserId()).child("Teams").child(binding.etTeamId.getText().toString()).setValue(true);
                                            pd.dismiss();
                                            Intent intent = new Intent(JoinTeamActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(JoinTeamActivity.this, "Add player successfully", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            break;
                        }
                        pd.dismiss();
                        Toast.makeText(JoinTeamActivity.this, "Not found team with this id", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }
}