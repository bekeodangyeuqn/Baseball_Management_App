package com.example.baseballmanagementapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseballmanagementapp.adapter.GameAdapter;
import com.example.baseballmanagementapp.databinding.FragmentEventListBinding;
import com.example.baseballmanagementapp.models.Game;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static String teamId;

    public EventListFragment() {
        // Required empty public constructor
    }

    public EventListFragment(String teamId) {
        // Required empty public constructor
        EventListFragment.teamId = teamId;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventListFragment newInstance(String param1, String param2) {
        EventListFragment fragment = new EventListFragment(teamId);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public class GetEventsFromFirebase extends AsyncTask<Void, Void, Void> {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("Teams");
        ArrayList<Game> gameList = new ArrayList<>();
        ProgressDialog pd;

        //FragmentEventListBinding binding = FragmentEventListBinding.inflate(inflater, container, false);
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(getActivity());
            pd.setMessage("We are loading your events");
            Log.d("app", "Before");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                        Log.d("app", dataSnapshot.getValue(Team.class).toString());
                        String teamId = dataSnapshot.getKey();
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
                                RecyclerView gameListView = (RecyclerView) requireView().findViewById(R.id.game_list_view);
                                gameListView.setLayoutManager(linearLayoutManager);
                                gameListView.setAdapter(gameAdapter);
                            };

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
//                pd.dismiss();
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            pd.dismiss();
            Log.d("app", "After");
            super.onPostExecute(unused);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEventListBinding binding = FragmentEventListBinding.inflate(inflater, container, false);
        View view2 = binding.getRoot();
        binding.eventsToolbar.inflateMenu(R.menu.menu);
        binding.eventsToolbar.setOnMenuItemClickListener(item -> {
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
        new GetEventsFromFirebase().execute();
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        String uid = auth.getUid();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("Teams");
//        ArrayList<Game> gameList = new ArrayList<>();
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Log.d("app", dataSnapshot.getValue(Team.class).toString());
//                    String teamId = dataSnapshot.getKey();
//                    FirebaseDatabase.getInstance().getReference().child("Team").child(teamId).child("Game").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                                Game game = dataSnapshot.getValue(Game.class);
//                                gameList.add(game);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        GameAdapter gameAdapter = new GameAdapter(getActivity(), gameList);
//        Log.d("app", gameList.get(0).getYourTeam() + " " + gameList.get(0).getOppTeam() + " " + gameList.get(0).getTimeStart());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        binding.gameListView.setLayoutManager(linearLayoutManager);
//        binding.gameListView.setAdapter(gameAdapter);
        return view2;
    }
}