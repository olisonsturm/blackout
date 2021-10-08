package me.olisonsturm.blackout.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.model.Player;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerHolder> {
    private List<Player> players = new ArrayList<>();



    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_item, parent, false);
        return new PlayerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerHolder holder, int position) {
        Player currentPlayer = players.get(position);
        holder.textViewNickName.setText(currentPlayer.getNickName());
        holder.textViewRealName.setText(currentPlayer.getRealName());
        switch (currentPlayer.getGender()) {
            case 0:
                holder.genderImageView.setImageResource(R.drawable.male);
            case 1:
                holder.genderImageView.setImageResource(R.drawable.female);
            case 2:
                holder.genderImageView.setImageResource(R.drawable.divers);
        }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    public Player getPlayerAt(int position) {
        return players.get(position);
    }

    class PlayerHolder extends RecyclerView.ViewHolder {
        private TextView textViewNickName;
        private TextView textViewRealName;
        private ImageView genderImageView;

        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
            textViewNickName = itemView.findViewById(R.id.text_view_nickName);
            textViewRealName = itemView.findViewById(R.id.text_view_realName);
            genderImageView = itemView.findViewById(R.id.genderDisplay);
        }
    }
}
