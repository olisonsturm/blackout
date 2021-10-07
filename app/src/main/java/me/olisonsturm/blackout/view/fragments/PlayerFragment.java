package me.olisonsturm.blackout.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.model.Player;
import me.olisonsturm.blackout.model.PlayerViewModel;
import me.olisonsturm.blackout.view.adapter.PlayerListAdapter;

public class PlayerFragment extends Fragment {

    Button continueBtn;
    FloatingActionButton addBtn;
    private PlayerViewModel playerViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.player_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        PlayerListAdapter adapter = new PlayerListAdapter();
        recyclerView.setAdapter(adapter);


        playerViewModel = ViewModelProvider.get(PlayerViewModel.class);
        playerViewModel.getAllPlayers().observe(getViewLifecycleOwner(), new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                adapter.setPlayers(players);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                playerViewModel.delete(adapter.getPlayerAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        /*
        adapter.setOnItemClickListener(new PlayerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Player player) {
                Intent intent = new Intent(PlayerFragment.this, )
            }
        });*/


        addBtn = view.findViewById(R.id.addNewPlayer);
        continueBtn = view.findViewById(R.id.startGameButton);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new GamingFragment()).commit();
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetPlayer bottomSheetPlayer = new BottomSheetPlayer();
                bottomSheetPlayer.show(getParentFragmentManager(), "BottomSheetPlayer");
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deletePlayers:
                playerViewModel.deleteAllPlayers();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
