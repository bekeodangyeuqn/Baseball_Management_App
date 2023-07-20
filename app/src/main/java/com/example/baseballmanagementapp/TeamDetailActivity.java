package com.example.baseballmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.baseballmanagementapp.databinding.ActivityTeamDetailBinding;
import com.example.baseballmanagementapp.models.Team;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeamDetailActivity extends AppCompatActivity {
    ActivityTeamDetailBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String teamId = getIntent().getStringExtra("teamId");
        binding = ActivityTeamDetailBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.membersListToolbar.inflateMenu(R.menu.menu);
        DatabaseReference teamRef = FirebaseDatabase.getInstance().getReference().child("Team");
        Fragment initFragment = new MemberListFragment(teamId);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, initFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        teamRef.child(teamId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Team team = snapshot.getValue(Team.class);
                binding.membersListToolbar.setTitle(team.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.membersListToolbar.setOnMenuItemClickListener(item -> {
            switch ((item.getItemId())){
                case R.id.settings:
                    Toast.makeText(TeamDetailActivity.this, "Setting click", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.logout:
                    auth.signOut();
                    Intent intent = new Intent(TeamDetailActivity.this, SignupActivity.class);
                    startActivity(intent);
                    return true;
                default:
                    return false;
            }
        });
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;


                switch (tab.getPosition()) {
                    case 0:
                        fragment = new MemberListFragment(teamId);
                        break;
                    case 1:
                        fragment = new TeamEventsFragment(teamId);
                        break;
                    case 2:
                        fragment = new TeamInfoFragment();
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