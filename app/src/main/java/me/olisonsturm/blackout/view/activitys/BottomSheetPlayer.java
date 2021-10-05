package me.olisonsturm.blackout.view.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.model.Player;
import me.olisonsturm.blackout.util.GlobalData;

public class BottomSheetPlayer extends BottomSheetDialogFragment {

    Button addBtn;
    ImageButton genderBtn;
    EditText nickName;
    EditText realName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_player_layout, container, false);

        addBtn = view.findViewById(R.id.playerAdd);
        genderBtn = view.findViewById(R.id.genderButton);
        nickName = view.findViewById(R.id.playerNickName);
        realName = view.findViewById(R.id.playerRealName);

        genderBtn.setImageResource(R.drawable.ic_behindi);

        addBtn.setOnClickListener(v -> {
            String nickNameL = nickName.getText().toString();
            String realNameL = realName.getText().toString();
            int gender = 0;

            if (TextUtils.isEmpty(nickNameL) || TextUtils.isEmpty(realNameL)) {
                Toast.makeText(view.getContext(), "Bitte alle Spielerdaten eingeben", Toast.LENGTH_SHORT).show();
            } else {
                Player player = new Player(nickNameL, realNameL, gender);

                GlobalData data = (GlobalData) view.getContext();

                data.getGlobalPlayerArrayList().add(player);
            }
            dismiss();
        });

        /*
        genderBtn.setOnClickListener(v ->{
            gender = gender + 1;
            if (gender == 3){
                gender = 0;
            }
        });*/


        return view;
    }


}
