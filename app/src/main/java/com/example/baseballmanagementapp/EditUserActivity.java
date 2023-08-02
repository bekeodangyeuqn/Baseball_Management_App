package com.example.baseballmanagementapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baseballmanagementapp.databinding.ActivityEditUserBinding;
import com.example.baseballmanagementapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditUserActivity extends AppCompatActivity {
    ActivityEditUserBinding binding;
    ProgressDialog pd;
    private int mYear, mMonth, mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityEditUserBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        FirebaseDatabase database = FirebaseDatabase.
                getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(EditUserActivity.this);
        pd.setTitle("Edit your info");
        pd.setMessage("We are editing your profile");
        DatabaseReference usersRef = database.getReference().child("User");
        final User[] user = new User[1];
        usersRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user[0] = snapshot.getValue(User.class);
                binding.editFirst.setText(user[0].getFirstname());
                binding.editLast.setText(user[0].getLastname());
                binding.editMail.setText(user[0].getMail());
                binding.editUsername.setText(user[0].getUsername());
                if (user[0].getDob() != null)
                    binding.editDob.setText(user[0].getDob());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.editDob.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(EditUserActivity.this,
                    (DatePickerDialog.OnDateSetListener) (view1, year, monthOfYear, dayOfMonth) -> binding.editDob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        binding.editSubmitBtn.setOnClickListener(view -> {
            pd.show();
            HashMap<String, Object> updatedUserData = new HashMap<>();
            updatedUserData.put("firstname", binding.editFirst.getText().toString());
            updatedUserData.put("lastname", binding.editLast.getText().toString());
            updatedUserData.put("mail", binding.editMail.getText().toString());
            updatedUserData.put("username", binding.editUsername.getText().toString());
            updatedUserData.put("dob", binding.editDob.getText().toString());

            usersRef.child(auth.getUid()).updateChildren(updatedUserData).addOnCompleteListener(task -> {
                pd.dismiss();
                if (task.isSuccessful()) {
                    Intent intent = new Intent(EditUserActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(EditUserActivity.this, "Edit user successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    // Data update failed
                    // You can add your own error handling logic here
                    Toast.makeText(EditUserActivity.this, "Edit user failed!", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}