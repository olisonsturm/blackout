package me.olisonsturm.blackout.view.activitys;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.view.Infos.DeviceInfo;

public class BottomSheetPlayer extends BottomSheetDialogFragment {

    Button addBtn;
    EditText nickName;
    EditText realName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_player_layout, container, false);

        addBtn = view.findViewById(R.id.btn_add);
        nickName = view.findViewById(R.id.playerNickName);
        realName = view.findViewById(R.id.playerRealName);


        return view;
    }
}
