package me.olisonsturm.blackout.view.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.model.Player;
import me.olisonsturm.blackout.model.PlayerViewModel;
import me.olisonsturm.blackout.view.adapter.PlayerListAdapter;

public class PlayerFragment extends Fragment  {

    private PlayerViewModel playerViewModel;

    Button continueBtn;
    FloatingActionButton addBtn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.player_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        PlayerListAdapter adapter = new PlayerListAdapter();
        recyclerView.setAdapter(adapter);


        playerViewModel = ViewModelProviders.of(getContext()).get(PlayerViewModel.class);
        playerViewModel.getAllPlayers().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                adapter.setPlayers(players);
            }
        });




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

}
