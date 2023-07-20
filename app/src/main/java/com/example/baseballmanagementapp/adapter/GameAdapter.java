package com.example.baseballmanagementapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseballmanagementapp.R;
import com.example.baseballmanagementapp.models.Game;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder>{
    private final Context context;
    private final ArrayList<Game> list;

    public GameAdapter(Context context, ArrayList<Game> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_card_view, parent, false);
        return new GameAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = list.get(position);
        //holder.teamImg.setImageResource(team.getLogo());
        holder.teamName.setText(game.getYourTeam());
        holder.oopName.setText(game.getOppTeam());
        holder.startTime.setText("Start: " + game.getTimeStart());
        holder.endTime.setText("End: " + game.getTimeEnd());
    }

    @Override
    public int getItemCount() {
        return  list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView teamName;
        private final TextView oopName;
        private final TextView startTime;
        private final TextView endTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.team_name);
            oopName = itemView.findViewById(R.id.oop_name);
            startTime = itemView.findViewById(R.id.start_date_time);
            endTime = itemView.findViewById(R.id.end_date_time);
        }
    }
}
