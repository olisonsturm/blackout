package me.olisonsturm.blackout.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
;import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.view.Animations.ProgressBarAnimation;

public class BottomSheetGame extends Fragment {

    ProgressBar progressBar;
    EditText editText;
    Button button;

    int progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.aaa_fragment_game_1, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        editText = view.findViewById(R.id.editText);
        button = view.findViewById(R.id.button);

        progress = 0;


        ProgressBarAnimation mProgressAnimation = new ProgressBarAnimation(progressBar, 3000);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = Integer.parseInt(editText.getText().toString());
                editText.setText("");
                mProgressAnimation.setProgress(progress);
            }
        });

        return view;
    }
}
