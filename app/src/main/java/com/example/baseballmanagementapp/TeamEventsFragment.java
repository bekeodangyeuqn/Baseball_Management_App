package com.example.baseballmanagementapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseballmanagementapp.adapter.GameAdapter;
import com.example.baseballmanagementapp.models.Game;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamEventsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static String teamId;
    ArrayList<Game> gameList = new ArrayList<>();

    public TeamEventsFragment() {
        // Required empty public constructor
    }

    public  TeamEventsFragment(String teamId) {
        TeamEventsFragment.teamId = teamId;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamEventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamEventsFragment newInstance(String param1, String param2) {
        TeamEventsFragment fragment = new TeamEventsFragment();
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

        FirebaseDatabase.getInstance().getReference().child("Game").orderByChild("teamId").equalTo(teamId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Game game = dataSnapshot.getValue(Game.class);
                    gameList.add(game);
                    Log.d("app", gameList.get(0).getYourTeam() + " " + gameList.get(0).getOppTeam() + " " + gameList.get(0).getTimeStart());
                }
                GameAdapter gameAdapter = new GameAdapter(getActivity(), gameList);
                //Log.d("app", gameList.get(0).getYourTeam() + " " + gameList.get(0).getOppTeam() + " " + gameList.get(0).getTimeStart());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                RecyclerView gameListView = (RecyclerView) requireView().findViewById(R.id.team_game_list_view);
                gameListView.setLayoutManager(linearLayoutManager);
                gameListView.setAdapter(gameAdapter);
            };

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return inflater.inflate(R.layout.fragment_team_events, container, false);
    }
}