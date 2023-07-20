package com.example.baseballmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.baseballmanagementapp.adapter.UserTeamAdapter;
import com.example.baseballmanagementapp.databinding.FragmentTeamListBinding;
import com.example.baseballmanagementapp.models.UserTeam;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public TeamListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamListFragment newInstance(String param1, String param2) {
        TeamListFragment fragment = new TeamListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentTeamListBinding binding = FragmentTeamListBinding.inflate(inflater, container, false);
        View view2 = binding.getRoot();
        String uid = auth.getUid();
        DatabaseReference userTeamRef = FirebaseDatabase.getInstance().getReference().child("UserTeam");
        DatabaseReference teamRef = FirebaseDatabase.getInstance().getReference().child("Team");
        ArrayList<UserTeam> list = new ArrayList<UserTeam>();
        binding.teamsToolbar.inflateMenu(R.menu.menu);
        binding.teamsToolbar.setOnMenuItemClickListener(item -> {
            switch ((item.getItemId())){
                case R.id.settings:
                    Toast.makeText(getActivity(), "Setting click", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.logout:
                    auth.signOut();
                    Intent intent = new Intent(getActivity(), SignupActivity.class);
                    startActivity(intent);
                    return true;
                default:
                    return false;
            }
        });
//        ref.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Log.d("app", dataSnapshot.getValue(Team.class).toString());
//                    Team team = dataSnapshot.getValue(Team.class);
//                    Log.d("app", team.getName());
//                    list.add(team);
//                }
//                TeamAdapter teamAdapter = new TeamAdapter(getActivity(), list);
//                teamAdapter.setOnClickListener((view, position, model) -> {
//                    Intent intent;
//                    switch (view.getId()) {
//                        case R.id.add_member_button:
//                            intent = new Intent(getActivity(), AddMemberActivity.class);
//                            intent.putExtra("teamId", list.get(position).getTeamId());
//                            startActivity(intent);
//                            Log.d("app", list.get(position).getTeamId());
//                            break;
//                        case R.id.add_event_button:
//                            intent = new Intent(getActivity(), CreateEventActivity.class);
//                            intent.putExtra("teamId", list.get(position).getTeamId());
//                            startActivity(intent);
//                            break;
//                    }
////                    Intent intent = new Intent(getActivity(), CreateEventActivity.class);
////                    intent.putExtra("teamId", list.get(position).getTeamId());
////                    startActivity(intent);
//                });
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                binding.listView.setLayoutManager(linearLayoutManager);
//                binding.listView.setAdapter(teamAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        userTeamRef.orderByChild("userId").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userTeamSnapshot : snapshot.getChildren()) {
                    String teamId = userTeamSnapshot.child("teamId").getValue(String.class);
                    Log.d("app", teamId);
                    UserTeam userTeam = userTeamSnapshot.getValue(UserTeam.class);
                    Log.d("app", userTeam.getTeamId());
                    list.add(userTeam);
                }
                UserTeamAdapter teamAdapter = new UserTeamAdapter(getActivity(), list);
                teamAdapter.setOnClickListener((view, position, model) -> {
                    Intent intent;
                    switch (view.getId()) {
                        case R.id.add_member_button:
                            intent = new Intent(getActivity(), AddMemberActivity.class);
                            intent.putExtra("teamId", list.get(position).getTeamId());
                            startActivity(intent);
                            Log.d("app", list.get(position).getTeamId());
                            break;
                        case R.id.add_event_button:
                            intent = new Intent(getActivity(), CreateEventActivity.class);
                            intent.putExtra("teamId", list.get(position).getTeamId());
                            startActivity(intent);
                            break;
                        case R.id.show_team_detail:
                            intent = new Intent(getActivity(), TeamDetailActivity.class);
                            intent.putExtra("teamId", list.get(position).getTeamId());
                            startActivity(intent);
                    }
//                    Intent intent = new Intent(getActivity(), CreateEventActivity.class);
//                    intent.putExtra("teamId", list.get(position).getTeamId());
//                    startActivity(intent);
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                binding.listView.setLayoutManager(linearLayoutManager);
                binding.listView.setAdapter(teamAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view2;
    }
}