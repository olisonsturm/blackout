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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.view.activitys.BottomSheetPlayer;

public class PlayerFragment extends Fragment  {

    Button btnAdd;
    EditText input;
    ListView playerListView;

    FloatingActionButton addBtn;

    ArrayList<String> playerList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        btnAdd = view.findViewById(R.id.btn_add);
        input = view.findViewById(R.id.eingabe);
        playerListView = view.findViewById(R.id.playerList);

        addBtn = view.findViewById(R.id.addNewPlayer);

        playerList = new ArrayList<String>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, playerList);
        playerListView.setAdapter(adapter);



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetPlayer bottomSheetPlayer = new BottomSheetPlayer();
                bottomSheetPlayer.show(getParentFragmentManager(), "BottomSheetPlayer");
            }
        });

        btnAdd.setOnClickListener(v -> {
            String name = input.getText().toString();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(view.getContext(), "Bitte Spielername eingeben", Toast.LENGTH_SHORT).show();
            } else {
                playerList.add(name);
                adapter.notifyDataSetChanged();
            }
            input.setText("");
        });

        return view;
    }

}
