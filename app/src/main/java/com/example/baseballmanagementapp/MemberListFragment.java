package com.example.baseballmanagementapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.evrencoskun.tableview.TableView;
import com.example.baseballmanagementapp.adapter.TeamMembersTableAdapter;
import com.example.baseballmanagementapp.databinding.FragmentMemberListBinding;
import com.example.baseballmanagementapp.models.Cell;
import com.example.baseballmanagementapp.models.ColumnHeader;
import com.example.baseballmanagementapp.models.RowHeader;
import com.example.baseballmanagementapp.models.User;
import com.example.baseballmanagementapp.models.UserTeam;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MemberListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemberListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static String teamId;

    private List<RowHeader> mRowHeaderList = new ArrayList<>();
    private List<ColumnHeader> mColumnHeaderList = new ArrayList<>();
    private List<List<Cell>> mCellList = new ArrayList<>();

    DatabaseReference userTeamRef = FirebaseDatabase.getInstance().getReference().child("UserTeam");
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User");
    private ProgressBar progressBar;
    public MemberListFragment() {
        // Required empty public constructor
    }

    public MemberListFragment(String teamId) {
        // Required empty public constructor
        MemberListFragment.teamId = teamId;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MemberListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemberListFragment newInstance(String param1, String param2) {
        MemberListFragment fragment = new MemberListFragment();
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
        FragmentMemberListBinding binding =  FragmentMemberListBinding.inflate(inflater, container, false);
        View view2 = binding.getRoot();
        //String uid = auth.getUid();

        ArrayList<UserTeam> list = new ArrayList<UserTeam>();


        progressBar = binding.progressBar;
        progressBar.setVisibility(View.VISIBLE);

        new FetchDataAsyncTask().execute();
        return view2;
    }
    private class FetchDataAsyncTask extends AsyncTask<Void, Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            userTeamRef.orderByChild("teamId").equalTo(teamId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long countItem = snapshot.getChildrenCount();
                    Log.d("app", "Count item: " + countItem);
                    final long[] i = {0};
                    for (DataSnapshot userTeamSnapshot : snapshot.getChildren()) {
                        UserTeam userTeam = userTeamSnapshot.getValue(UserTeam.class);
                        String userId = userTeamSnapshot.child("userId").getValue(String.class);
                        final User[] user = new User[1];
                        userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                i[0] = i[0] + 1;
                                user[0] = snapshot.getValue(User.class);
                                List<Cell> child = new ArrayList<>();
                                child.add(new Cell(user[0].getFirstname() + " " + user[0].getLastname()));
                                child.add(new Cell(user[0].getMail()));
                                child.add(new Cell(user[0].getDob()));
                                child.add(new Cell(userTeam.getRole()));
                                child.add(new Cell(userTeam.getStatus()));

                                mCellList.add(child);
                                if (i[0] == countItem) {
                                    if (mCellList != null) {
                                        Log.d("app", "Add successfull");
                                        mColumnHeaderList.add(new ColumnHeader("Name"));
                                        mColumnHeaderList.add(new ColumnHeader("Email"));
                                        mColumnHeaderList.add(new ColumnHeader("Date of Birth"));
                                        mColumnHeaderList.add(new ColumnHeader("Role"));
                                        mColumnHeaderList.add(new ColumnHeader("Status"));
                                        TeamMembersTableAdapter adapter = new TeamMembersTableAdapter();
                                        TableView tableView = (TableView) requireView().findViewById(R.id.tableView);
                                        tableView.setAdapter(adapter);
                                        adapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

                                    }
                                }
//                                for (List<Cell> mCell : mCellList) {
//                                    for (Cell item : mCell) {
//                                        if (item.getData() != null)
//                                        Log.d("app", item.getData().toString() + " ");
//                                    }
//                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    //MemberAdapter memberAdapter = new MemberAdapter(getActivity(), list);
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
//                binding.memberListView.setHasFixedSize(true);
//                binding.memberListView.setLayoutManager(linearLayoutManager);
//                binding.memberListView.setAdapter(memberAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            // Return the fetched data

            //return null; // Replace null with the actual fetched data
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            // This method runs on the main UI thread
            // Update your UI with the retrieved data

            // For example:

            // Hide the loading indicator as data retrieval is complete
            progressBar.setVisibility(View.GONE);
        }
    }
}