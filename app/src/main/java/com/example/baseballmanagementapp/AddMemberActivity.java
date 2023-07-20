package com.example.baseballmanagementapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baseballmanagementapp.databinding.ActivityAddMemberBinding;
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

public class AddMemberActivity extends AppCompatActivity {
    ActivityAddMemberBinding binding;
    ProgressDialog pd;
    int checkRole;
    String teamId;

    //
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAddMemberBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        teamId = getIntent().getStringExtra("teamId");
        RadioGroup radioGroup = binding.radioGroup;
        radioGroup.setOnCheckedChangeListener((radioGroup1, checkedId) -> {
            switch (checkedId) {
                case R.id.select_manager:
                    checkRole = 1;
                    break;
                case R.id.select_player:
                    checkRole = 2;
                    break;
            }
        });
        FirebaseDatabase database = FirebaseDatabase.
                getInstance("https://baseball-management-app-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference ref = database.getReference();
        final Team[] team = new Team[1];
        ref.child("Team").child(teamId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                team[0] = snapshot.getValue(Team.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseAuth auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(AddMemberActivity.this);
        pd.setTitle("Adding member");
        pd.setMessage("Adding member to your team");
        binding.createSubmitBtn.setOnClickListener(view -> {
            pd.show();

            final User[] user = new User[1];
            ref.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    ArrayList<User> users = new ArrayList<>();
                    if (snapshot.exists()) {
                        for (DataSnapshot findSnapshot : snapshot.getChildren()) {
                            User user = findSnapshot.getValue(User.class);
                            users.add(user);
                        }
                    }
                    Log.d("app", users.get(1).getUsername());
                    for (User findUser : users) {
                        if (findUser.getUsername().equals(binding.etMemberId.getText().toString())) {
                            Log.d("app", "Founded user");
                            user[0] = findUser;
                            Log.d("app",  user[0].getUserId());
                            if (checkRole == 1) {
                                //Manager manager = (Manager) user[0];
                                ref.child("Team").child(teamId).child("Managers").addListenerForSingleValueEvent(new ValueEventListener() {
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
                                            Toast.makeText(AddMemberActivity.this, "This manager with this id have already added", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // The value does not exist, add it to the database
                                            // Handle the case when the value does not exist
                                            UserTeam userTeam = new UserTeam();
                                            userTeam.setTeamId(teamId);
                                            userTeam.setUserId(user[0].getUserId());
                                            userTeam.setRole("manager");
                                            userTeam.setStatus("active");
                                            DatabaseReference userTeamRef= ref.child("UserTeam").push();
                                            userTeamRef.setValue(userTeam);
                                            ref.child("Team").child(teamId).child("Managers").child(user[0].getUserId()).setValue(true);
                                            ref.child("User").child(user[0].getUserId()).child("Teams").child(teamId).setValue(true);
                                            Toast.makeText(AddMemberActivity.this, "Add manager successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            else if (checkRole ==  2) {

                                ref.child("Team").child(teamId).child("Players").addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                Toast.makeText(AddMemberActivity.this, "This player with this id have already added", Toast.LENGTH_SHORT).show();
                                            } else {
                                                // The value does not exist, add it to the database
                                                // Handle the case when the value does not exist
                                                UserTeam userTeam = new UserTeam();
                                                userTeam.setTeamId(teamId);
                                                userTeam.setUserId(user[0].getUserId());
                                                userTeam.setRole("player");
                                                userTeam.setStatus("active");
                                                DatabaseReference userTeamRef= ref.child("UserTeam").push();
                                                userTeamRef.setValue(userTeam);
                                                ref.child("Team").child(teamId).child("Players").child(user[0].getUserId()).setValue(true);
                                                ref.child("User").child(user[0].getUserId()).child("Teams").child(teamId).setValue(true);
                                                Toast.makeText(AddMemberActivity.this, "Add player successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            UserTeam userTeam = new UserTeam();
                                            userTeam.setTeamId(teamId);
                                            userTeam.setUserId(user[0].getUserId());
                                            userTeam.setRole("player");
                                            userTeam.setStatus("active");
                                            DatabaseReference userTeamRef= ref.child("UserTeam").push();
                                            userTeamRef.setValue(userTeam);
                                            ref.child("Team").child(teamId).child("Players").child(user[0].getUserId()).setValue(true);
                                            ref.child("User").child(user[0].getUserId()).child("Teams").child(teamId).setValue(true);
                                            Toast.makeText(AddMemberActivity.this, "Add player successfully", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AddMemberActivity.this, "Not found user with this id", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }
}