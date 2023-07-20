package com.example.baseballmanagementapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseballmanagementapp.R;
import com.example.baseballmanagementapp.models.User;
import com.example.baseballmanagementapp.models.UserTeam;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MemberAdapter  extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{
    private final Context context;
    private final ArrayList<UserTeam> list;
    private MemberAdapter.OnClickListener onClickListener;

    public MemberAdapter(Context context, ArrayList<UserTeam> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        return new MemberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.ViewHolder holder, int position) {
        UserTeam userTeam = list.get(position);
        //holder.teamImg.setImageResource(team.getLogo());
        FirebaseDatabase database = FirebaseDatabase.
                getInstance("https://baseball-management-app-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference userRef = database.getReference().child("User");
        Log.d("app", userTeam.getTeamId());
        final User[] user = new User[1];
        userRef.child(userTeam.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user[0] = snapshot.getValue(User.class);
                //Log.d("app", user[0].getFisName());
                holder.memberName.setText(user[0].getFirstname() + " " + user[0].getLastname());
                holder.memberStatus.setText( userTeam.getStatus());
                holder.memberRole.setText(userTeam.getRole());
                holder.memberMail.setText(user[0].getMail());
                if (user[0].getDob() == null) {
                    holder.memberDob.setText("Unknown");
                } else {
                    holder.memberDob.setText(user[0].getDob().toString());
                }
//                if (!Objects.equals(userTeam.getRole(), "manager") || !Objects.equals(userTeam.getStatus(), "active")){
//                    holder.addEventButton.setVisibility(View.GONE);
//                    holder.addMemberButton.setVisibility(View.GONE);
//                }
//                holder.addEventButton.setOnClickListener(view -> {
//                    if (onClickListener != null) {
//                        onClickListener.onClick(view, position, userTeam);
//                    } else {
//                        Log.d("app", "Not good");
//                    }
//                });
//                holder.addMemberButton.setOnClickListener(view -> {
//                    if (onClickListener != null) {
//                        onClickListener.onClick(view, position, userTeam);
//                    } else {
//                        Log.d("app", "Not good");
//                    }
//                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return  list.size();
    }

    public void setOnClickListener(MemberAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(View view, int position, UserTeam model);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView memberName;
        private final TextView memberMail;
        private final TextView memberDob;
        private final TextView memberRole;
        private final TextView memberStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.member_name);
            memberMail = itemView.findViewById(R.id.member_email);
            memberDob = itemView.findViewById(R.id.member_dob);
            memberRole = itemView.findViewById(R.id.member_role);
            memberStatus = itemView.findViewById(R.id.member_status);
        }
}}
