package com.example.baseballmanagementapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseballmanagementapp.R;
import com.example.baseballmanagementapp.models.Team;
import com.example.baseballmanagementapp.models.UserTeam;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class UserTeamAdapter extends RecyclerView.Adapter<UserTeamAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<UserTeam> list;
    private OnClickListener onClickListener;

    public UserTeamAdapter(Context context, ArrayList<UserTeam> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserTeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        UserTeam userTeam = list.get(position);
        //holder.teamImg.setImageResource(team.getLogo());
        FirebaseDatabase database = FirebaseDatabase.
                getInstance("https://baseball-management-app-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference TeamRef = database.getReference().child("Team");
        Log.d("app", userTeam.getTeamId());
        final Team[] team = new Team[1];
        TeamRef.child(userTeam.getTeamId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                team[0] = snapshot.getValue(Team.class);
                Log.d("app", team[0].getName());
                holder.teamName.setText(team[0].getName());
                holder.userTeamStatus.setText("Status: " + userTeam.getStatus());
                holder.userTeamRole.setText("Role: " + userTeam.getRole());
                if (!Objects.equals(userTeam.getRole(), "manager") || !Objects.equals(userTeam.getStatus(), "active")){
                    holder.addEventButton.setVisibility(View.GONE);
                    holder.addMemberButton.setVisibility(View.GONE);
                }
                holder.addEventButton.setOnClickListener(view -> {
                    if (onClickListener != null) {
                        onClickListener.onClick(view, position, userTeam);
                    } else {
                        Log.d("app", "Not good");
                    }
                });
                holder.addMemberButton.setOnClickListener(view -> {
                    if (onClickListener != null) {
                        onClickListener.onClick(view, position, userTeam);
                    } else {
                        Log.d("app", "Not good");
                    }
                });
                holder.showTeamDetail.setOnClickListener(view -> {
                    if (onClickListener != null) {
                        onClickListener.onClick(view, position, userTeam);
                    } else {
                        Log.d("app", "Not good");
                    }
                });
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

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(View view, int position, UserTeam model);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView teamImg;
        private final TextView teamName;
        private final Button addEventButton;
        private final Button addMemberButton;
        private final RelativeLayout showTeamDetail;
        private final TextView userTeamRole;
        private final TextView userTeamStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamImg = itemView.findViewById(R.id.team_img);
            teamName = itemView.findViewById(R.id.team_name);
            userTeamRole = itemView.findViewById(R.id.user_team_role);
            userTeamStatus = itemView.findViewById(R.id.user_team_status);
            addEventButton = itemView.findViewById(R.id.add_event_button);
            addMemberButton = itemView.findViewById(R.id.add_member_button);
            showTeamDetail = itemView.findViewById(R.id.show_team_detail);
        }
    }
}
