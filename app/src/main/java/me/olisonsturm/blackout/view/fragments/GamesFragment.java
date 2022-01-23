package me.olisonsturm.blackout.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.transition.AutoTransition;
import androidx.transition.ChangeTransform;
import androidx.transition.TransitionManager;

import me.olisonsturm.blackout.R;

public class GamesFragment extends Fragment {

    CardView card1;
    CardView card2;
    CardView card3;
    CardView card4;

    ImageButton expandBtn1;
    ImageButton expandBtn2;
    ImageButton expandBtn3;
    ImageButton expandBtn4;

    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_spiele, container, false);

        card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        card3 = view.findViewById(R.id.card3);
        card4 = view.findViewById(R.id.card4);

        expandBtn1 = view.findViewById(R.id.btn_game1);
        expandBtn2 = view.findViewById(R.id.btn_game2);
        expandBtn3 = view.findViewById(R.id.btn_game3);
        expandBtn4 = view.findViewById(R.id.btn_game4);

        text1 = view.findViewById(R.id.hiddenTextGame1);
        text2 = view.findViewById(R.id.hiddenTextGame2);
        text3 = view.findViewById(R.id.hiddenTextGame3);
        text4 = view.findViewById(R.id.hiddenTextGame4);

        card1.setOnClickListener(v -> {
            //Transition zu Spiel 1
        });

        card2.setOnClickListener(v -> {
            //Transition zu Spiel 2
        });

        card3.setOnClickListener(v -> {
            //Transition zu Spiel 3
        });

        card4.setOnClickListener(v -> {
            //Transition zu Spiel 4
        });

        expandBtn1.setOnClickListener(v -> {
            if (text1.getVisibility() == View.VISIBLE){
                //schließen
                TransitionManager.beginDelayedTransition(card1, new ChangeTransform());
                text1.setVisibility(View.GONE);
                expandBtn1.setImageResource(R.drawable.ic_expand_more);
            }
            else{
                //öffnen
                TransitionManager.beginDelayedTransition(card1, new AutoTransition());
                text1.setVisibility(View.VISIBLE);
                expandBtn1.setImageResource(R.drawable.ic_expand_less);
            }
        });

        expandBtn2.setOnClickListener(v -> {
            if (text2.getVisibility() == View.VISIBLE){
                //schließen
                TransitionManager.beginDelayedTransition(card2, new ChangeTransform());
                text2.setVisibility(View.GONE);
                expandBtn2.setImageResource(R.drawable.ic_expand_more);
            }
            else{
                //öffnen
                TransitionManager.beginDelayedTransition(card2, new AutoTransition());
                text2.setVisibility(View.VISIBLE);
                expandBtn2.setImageResource(R.drawable.ic_expand_less);
            }
        });

        expandBtn3.setOnClickListener(v -> {
            if (text3.getVisibility() == View.VISIBLE){
                //schließen
                TransitionManager.beginDelayedTransition(card3, new ChangeTransform());
                text3.setVisibility(View.GONE);
                expandBtn3.setImageResource(R.drawable.ic_expand_more);
            }
            else{
                //öffnen
                TransitionManager.beginDelayedTransition(card3, new AutoTransition());
                text3.setVisibility(View.VISIBLE);
                expandBtn3.setImageResource(R.drawable.ic_expand_less);
            }
        });

        expandBtn4.setOnClickListener(v -> {
            if (text4.getVisibility() == View.VISIBLE){
                //schließen
                TransitionManager.beginDelayedTransition(card4, new ChangeTransform());
                text4.setVisibility(View.GONE);
                expandBtn4.setImageResource(R.drawable.ic_expand_more);
            }
            else{
                //öffnen
                TransitionManager.beginDelayedTransition(card4, new AutoTransition());
                text4.setVisibility(View.VISIBLE);
                expandBtn4.setImageResource(R.drawable.ic_expand_less);
            }
        });

        return view;
    }

}
