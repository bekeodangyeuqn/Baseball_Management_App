package com.example.baseballmanagementapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.baseballmanagementapp.databinding.FragmentGameCreateBinding;
import com.example.baseballmanagementapp.models.Game;
import com.example.baseballmanagementapp.models.Team;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameCreateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static String teamId;

    public GameCreateFragment(String teamId) {
        // Required empty public constructor
        GameCreateFragment.teamId = teamId;
    }

    public GameCreateFragment(){

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameCreateFragment newInstance(String param1, String param2) {
        GameCreateFragment fragment = new GameCreateFragment(teamId);
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
        FragmentGameCreateBinding binding = FragmentGameCreateBinding.inflate(inflater, container, false);
        View view2 = binding.getRoot();
        binding.etStartDate.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener) (view1, year, monthOfYear, dayOfMonth) -> binding.etStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        binding.etStartTime.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                    (view12, hourOfDay, minute) -> binding.etStartTime.setText(hourOfDay + ":" + minute), mHour, mMinute, false);
            timePickerDialog.show();
        });

        binding.etEndDate.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener) (view1, year, monthOfYear, dayOfMonth) -> binding.etEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        binding.etEndTime.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                    (view13, hourOfDay, minute) -> binding.etEndTime.setText(hourOfDay + ":" + minute), mHour, mMinute, false);
            timePickerDialog.show();
        });

        binding.submitEventBtn.setOnClickListener(view -> {
            UUID gameId =  UUID.randomUUID();
            Log.d("app", teamId);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            final String[] teamName = new String[1];
            final String[] getTeamId = new String[1];
            ref.child("Team").child(teamId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    getTeamId[0] = snapshot.getKey();
                    teamName[0] = snapshot.getValue(Team.class).getName();
                    Log.d("app", teamName[0]);
                    Log.d("app", "TeamId is: " + " " + getTeamId[0]);
                    Game game = new Game(getTeamId[0], binding.etName.getText().toString(), gameId.toString(),
                            binding.etLocation.getText().toString(),
                            binding.etStartTime.getText().toString() + " " + binding.etStartDate.getText().toString(),
                            binding.etEndTime.getText().toString() + " " + binding.etEndDate.getText().toString(),
                            teamName[0],
                            binding.etOoponent.getText().toString());
                    ref.child("Game").child(gameId.toString()).setValue(game);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("teamId", teamId);
            startActivity(intent);
        });
        return view2;
    }
}