package me.olisonsturm.blackout.view.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.model.Player;
import me.olisonsturm.blackout.model.PlayerViewModel;

public class BottomSheetPlayer extends BottomSheetDialogFragment {

    Button addBtn;
    ImageView genderBtn;
    EditText nickName;
    EditText realName;
    EditText editTextPriority;
    int gender = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_player_layout, container, false);

        addBtn = view.findViewById(R.id.playerAdd);
        genderBtn = view.findViewById(R.id.genderButton);
        nickName = view.findViewById(R.id.playerNickName);
        realName = view.findViewById(R.id.playerRealName);
        editTextPriority = view.findViewById(R.id.eTPriority);

        genderBtn.setImageResource(R.drawable.male);

        addBtn.setOnClickListener(v -> {
            String nickNameL = nickName.getText().toString();
            String realNameL = realName.getText().toString();
            int priority = Integer.parseInt(editTextPriority.getText().toString());

            if (TextUtils.isEmpty(nickNameL) || TextUtils.isEmpty(realNameL)) {
                Toast.makeText(view.getContext(), "Bitte alle Spielerdaten eingeben", Toast.LENGTH_SHORT).show();
            } else {
                /*
                Player player = new Player(nickNameL,realNameL,gender,priority);
                PlayerViewModel.insert(player);
                dismiss();*/
            }
        });


        genderBtn.setOnClickListener(v -> {
            gender = gender + 1;
            if (gender == 3) {
                gender = 0;
            }
            switch (gender) {
                case 0:
                    genderBtn.setImageResource(R.drawable.male);
                    break;
                case 1:
                    genderBtn.setImageResource(R.drawable.female);
                    break;
                case 2:
                    genderBtn.setImageResource(R.drawable.divers);
                    break;
            }
        });


        return view;
    }


}
