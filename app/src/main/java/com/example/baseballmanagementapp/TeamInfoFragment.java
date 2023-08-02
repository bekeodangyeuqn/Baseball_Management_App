package com.example.baseballmanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.baseballmanagementapp.databinding.FragmentTeamInfoBinding;
import com.example.baseballmanagementapp.models.Team;
import com.example.baseballmanagementapp.models.UserTeam;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static String teamId;
    private static String userTeamId;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    public TeamInfoFragment() {
        // Required empty public constructor
    }

    public  TeamInfoFragment(String teamId) {
        TeamInfoFragment.teamId = teamId;
    }

    public TeamInfoFragment(String teamId, String userTeamId) {
        TeamInfoFragment.teamId = teamId;
        TeamInfoFragment.userTeamId = userTeamId;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamInfoFragment newInstance(String param1, String param2) {
        TeamInfoFragment fragment = new TeamInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String uid = auth.getUid();
        FragmentTeamInfoBinding binding = FragmentTeamInfoBinding.inflate(inflater, container, false);
        View view2 = binding.getRoot();
        DatabaseReference teamRef = FirebaseDatabase.getInstance().getReference().child("Team").child(teamId);
        DatabaseReference userTeamRef = FirebaseDatabase.getInstance().getReference().child("UserTeam").child(userTeamId);
        binding.teamEditButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), EditTeamActivity.class);
            intent.putExtra("teamId", teamId);
            intent.putExtra("userTeamId", userTeamId);
            startActivity(intent);
        });
        userTeamRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserTeam userTeam = snapshot.getValue(UserTeam.class);

                if (!Objects.equals(userTeam.getRole(), "manager") || !Objects.equals(userTeam.getStatus(), "active")){
                    binding.teamEditButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        teamRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Team team = snapshot.getValue(Team.class);
                assert team != null;
                TextView teamNameTextView = (TextView) getActivity().findViewById(R.id.teamNameTextView);
                TextView teamCityTextView = (TextView) getActivity().findViewById(R.id.teamCityTextView);
                TextView teamProvinceTextView = (TextView) getActivity().findViewById(R.id.teamProvinceTextView);
                TextView teamCountryTextView = (TextView) getActivity().findViewById(R.id.teamCountryTextView);
                TextView teamYearTextView = (TextView) getActivity().findViewById(R.id.teamYearTextView);
                TextView teamStadiumTextView = (TextView) getActivity().findViewById(R.id.teamStadiumTextView);
                teamNameTextView.setText(team.getName());
                teamCityTextView.setText("City: " + team.getCity());
                teamProvinceTextView.setText("Province: " + team.getProvince());
                teamCountryTextView.setText("Country: " + team.getCountry());
                teamYearTextView.setText("Founded year: " + team.getFoundedYear());
                teamStadiumTextView.setText("Stadium: " + team.getStadium());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view2;
    }
}