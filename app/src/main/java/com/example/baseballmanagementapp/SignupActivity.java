package com.example.baseballmanagementapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baseballmanagementapp.databinding.ActivitySignupBinding;
import com.example.baseballmanagementapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    private FirebaseAuth auth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        FirebaseDatabase database = FirebaseDatabase.
                getInstance("https://baseball-management-app-default-rtdb.asia-southeast1.firebasedatabase.app");
        auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(SignupActivity.this);
        pd.setTitle("Creating account");
        pd.setMessage("We are creating account for you");

        binding.alrdAccount.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
            startActivity(intent);
        });
        binding.signupBtn.setOnClickListener(view -> {
            pd.show();
            auth.createUserWithEmailAndPassword(binding.etMail.getText().toString(),
                    binding.etPassword.getText().toString()).addOnCompleteListener(task -> {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                            User user = new User(id, binding.etFirst.getText().toString(), binding.etLast.getText().toString(),binding.etName.getText().toString(), binding.etMail.getText().toString(),
                                    binding.etPassword.getText().toString());
                            Log.d("app", id);
                            DatabaseReference ref = database.getReference();
                            ref.child("User").child(id).setValue(user);
                            //database.getReference("message").setValue("Hello world");
                            Intent intent = new Intent(SignupActivity.this, ChooseParticipateActivity.class);
                            startActivity(intent);
                            Toast.makeText(SignupActivity.this, "Create user successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        });
    }
}