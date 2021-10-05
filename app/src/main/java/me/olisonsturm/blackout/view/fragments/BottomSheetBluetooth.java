package me.olisonsturm.blackout.view.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Set;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.view.Infos.ConnectionInfo;
import me.olisonsturm.blackout.view.Infos.DeviceInfo;
import me.olisonsturm.blackout.view.adapter.DeviceListAdapter;

public class BottomSheetBluetooth extends BottomSheetDialogFragment implements DeviceListAdapter.OnNoteListener {

    RecyclerView deviceRecyclerView;
    RecyclerView deviceRecyclerViewSearch;
    RecyclerView.LayoutManager layoutManager;

    BluetoothAdapter bluetoothAdapter;
    ArrayList<DeviceInfo> devices;

    private Set<BluetoothDevice> pairedDevices;
    private ConnectionInfo connectionInfo;
    private BottomSheetBluetoothListener mListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_bluetooth_layout, container, false);

        deviceRecyclerView = view.findViewById(R.id.bluetoothList);
        deviceRecyclerViewSearch = view.findViewById(R.id.bluetoothListSearch);

        layoutManager = new LinearLayoutManager(getContext());
        deviceRecyclerView.setLayoutManager(layoutManager);
        deviceRecyclerView.setHasFixedSize(true);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        devices = new ArrayList<>();

        pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bluetoothDevice : pairedDevices) {
                devices.add(new DeviceInfo(bluetoothDevice.getName(), bluetoothDevice.getAddress()));
            }
        } else {
            Toast.makeText(getContext(), "Keine gekoppelten Geräte gefunden", Toast.LENGTH_SHORT).show();
        }

        DeviceListAdapter adapter = new DeviceListAdapter(devices, this);
        deviceRecyclerView.setAdapter(adapter);

        return view;
    }

    public interface BottomSheetBluetoothListener {
        void onItemClicked(String name, String adress);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetBluetoothListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement BottomSheetListener");
        }
    }

    @Override
    public void onNoteClick(int position) {
        Toast.makeText(getContext(), "Connecting to Device", Toast.LENGTH_SHORT);

        String name = devices.get(position).getName();
        String address = devices.get(position).getAddress();

        mListener.onItemClicked(name, address);
        dismiss();
    }

}
