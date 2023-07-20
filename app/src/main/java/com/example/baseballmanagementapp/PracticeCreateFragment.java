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

import androidx.fragment.app.Fragment;

import com.example.baseballmanagementapp.databinding.FragmentPracticeCreateBinding;
import com.example.baseballmanagementapp.models.Practice;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PracticeCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PracticeCreateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mYear, mMonth, mDay, mHour, mMinute;
    static String teamId;

    public PracticeCreateFragment(String teamId) {
        // Required empty public constructor
        PracticeCreateFragment.teamId = teamId;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PracticeCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PracticeCreateFragment newInstance(String param1, String param2) {
        PracticeCreateFragment fragment = new PracticeCreateFragment(teamId);
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
        FragmentPracticeCreateBinding binding = FragmentPracticeCreateBinding.inflate(inflater, container, false);
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

        binding.submitPracticeBtn.setOnClickListener(view -> {
            UUID gameId =  UUID.randomUUID();
            Log.d("app", teamId);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            Practice practice = new Practice(teamId, binding.etName.getText().toString(), gameId.toString(),
                    binding.etLocation.getText().toString(),
                    binding.etStartTime.getText().toString() + "T" + binding.etStartDate.getText().toString(),
                    binding.etEndTime.getText().toString() + " " + binding.etEndDate.getText().toString());
            ref.child("Team").child(teamId).child("Game").child(gameId.toString()).setValue(practice);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });
        return view2;
    }
}