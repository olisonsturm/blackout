package me.olisonsturm.blackout.view.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.olisonsturm.blackout.R;

public class SpieleFragment extends Fragment {

    TextView textView;
    EditText editText;
    Button btn;

    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    InputStream inputStream;
    OutputStream outputStream;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_spiele, container, false);

        return view;
    }

}
