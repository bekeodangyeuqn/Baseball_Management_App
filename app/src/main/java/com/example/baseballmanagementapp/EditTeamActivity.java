package com.example.baseballmanagementapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baseballmanagementapp.databinding.ActivityEditTeamBinding;
import com.example.baseballmanagementapp.models.Team;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditTeamActivity extends AppCompatActivity {
    ActivityEditTeamBinding binding;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityEditTeamBinding.inflate(getLayoutInflater());
        String teamId = getIntent().getStringExtra("teamId");
        String userTeamId = getIntent().getStringExtra("userTeamId");
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        FirebaseDatabase database = FirebaseDatabase.
                getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(EditTeamActivity.this);
        pd.setTitle("Edit team");
        pd.setMessage("We are editing team");
        DatabaseReference teamsRef = database.getReference().child("Team");
        final Team[] team = new Team[1];
        teamsRef.child(teamId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                team[0] = snapshot.getValue(Team.class);
                binding.editTeamName.setText(team[0].getName());
                if (team[0].getCity() != null)
                    binding.editTeamCity.setText(team[0].getCity());
                if (team[0].getProvince() != null)
                    binding.editTeamProvince.setText(team[0].getProvince());
                if (team[0].getCountry() != null)
                    binding.editTeamCountry.setText(team[0].getCountry());
                if (team[0].getFoundedYear() != null)
                    binding.editTeamYear.setText(team[0].getFoundedYear());
                if (team[0].getStadium() != null)
                    binding.editTeamStadium.setText(team[0].getStadium());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.editSubmitBtn.setOnClickListener(view -> {
            pd.show();
            HashMap<String, Object> updatedTeamData = new HashMap<>();
            updatedTeamData.put("name", binding.editTeamName.getText().toString());
            updatedTeamData.put("city", binding.editTeamCity.getText().toString());
            updatedTeamData.put("province", binding.editTeamProvince.getText().toString());
            updatedTeamData.put("country", binding.editTeamCountry.getText().toString());
            updatedTeamData.put("foundedYear", binding.editTeamYear.getText().toString());
            updatedTeamData.put("stadium", binding.editTeamStadium.getText().toString());

            teamsRef.child(teamId).updateChildren(updatedTeamData).addOnCompleteListener(task -> {
                pd.dismiss();
                if (task.isSuccessful()) {
                    Intent intent = new Intent(EditTeamActivity.this, TeamDetailActivity.class);
                    intent.putExtra("teamId", teamId);
                    intent.putExtra("userTeamId", userTeamId);
                    startActivity(intent);
                    Toast.makeText(EditTeamActivity.this, "Edit user successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    // Data update failed
                    // You can add your own error handling logic here
                    Toast.makeText(EditTeamActivity.this, "Edit user failed!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}