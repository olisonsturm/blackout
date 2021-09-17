package me.olisonsturm.blackout.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import me.olisonsturm.blackout.R;

public class PlayerFragment extends Fragment {

    Button btnAdd;
    EditText eingabe;
    ListView playerListView;
    ArrayList<String> playerList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        return inflater.inflate(R.layout.fragment_player, container, false);

         playerListView = findViewById(R.id.playerListe);
         btnAdd = findViewById(R.id.btn_add);
         eingabe = findViewById(R.id.eingabe);

         btnAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String text = eingabe.getText().toString(); //Try????
                 if (text == null){
                     Toast.makeText(this, "Bitte spielername eingeben", Toast.LENGTH_SHORT).show();
                 } else {
                     playerList.add(text);
                 }

                 ArrayAdapter playerListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, playerList);
                 playerListView.setAdapter(playerListAdapter);
             }
         });








    }

}
