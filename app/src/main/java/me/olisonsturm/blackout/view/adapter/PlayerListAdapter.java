package me.olisonsturm.blackout.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.model.Player;
import me.olisonsturm.blackout.view.Infos.DeviceInfo;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerHolder>{
    private List<Player> players = new ArrayList<>();


    @NonNull
    @NotNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_item, parent, false);
        return new PlayerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PlayerHolder holder, int position) {
        Player currentPlayer = players.get(position);
        holder.textViewNickName.setText(currentPlayer.getNickName());
        holder.textViewRealName.setText(currentPlayer.getRealName());
        switch (currentPlayer.getGender()){
            case 0: holder.genderImageView.setImageResource(R.drawable.male);
            case 1: holder.genderImageView.setImageResource(R.drawable.female);
            case 2: holder.genderImageView.setImageResource(R.drawable.divers);
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

    class PlayerHolder extends RecyclerView.ViewHolder{
        private TextView textViewNickName;
        private TextView textViewRealName;
        private ImageView genderImageView;

        public PlayerHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewNickName = itemView.findViewById(R.id.text_view_nickName);
            textViewRealName = itemView.findViewById(R.id.text_view_realName);
            genderImageView = itemView.findViewById(R.id.genderDisplay);
        }
    }
}
