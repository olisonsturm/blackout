package me.olisonsturm.blackout.view.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.transition.AutoTransition;
import androidx.transition.ChangeTransform;
import androidx.transition.TransitionManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.olisonsturm.blackout.R;

public class SpieleFragment extends Fragment {

    CardView card1;
    CardView card2;

    ImageButton btnGame1;
    ImageButton btnGame2;

    TextView text1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_spiele, container, false);


        card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);

        btnGame1 = view.findViewById(R.id.btn_game1);
        btnGame2 = view.findViewById(R.id.btn_game2);

        text1 = view.findViewById(R.id.hiddenTextGame1);



        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Transition zu Spiel 1
            }
        });

        btnGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text1.getVisibility() == View.VISIBLE){
                    //schließen
                    TransitionManager.beginDelayedTransition(card1, new ChangeTransform());
                    text1.setVisibility(View.GONE);
                    btnGame1.setImageResource(R.drawable.ic_expand_more);
                }
                else{
                    //öffnen
                    TransitionManager.beginDelayedTransition(card1, new AutoTransition());
                    text1.setVisibility(View.VISIBLE);
                    btnGame1.setImageResource(R.drawable.ic_expand_less);
                }
            }
        });








        return view;
    }

}
