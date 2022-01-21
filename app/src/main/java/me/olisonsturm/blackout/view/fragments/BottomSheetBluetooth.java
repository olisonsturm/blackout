package me.olisonsturm.blackout.view.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Set;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.view.Infos.DeviceInfo;
import me.olisonsturm.blackout.view.adapter.DeviceListAdapter;

public class BottomSheetBluetooth extends BottomSheetDialogFragment implements DeviceListAdapter.OnNoteListener {

    private static final String TAG = "DeviceListActivity";

    ImageButton scanButton;
    RecyclerView deviceRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    BluetoothAdapter bluetoothAdapter;
    ArrayList<DeviceInfo> devices;

    private BottomSheetBluetoothListener mListener;
    private DeviceListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_bluetooth_layout, container, false);

        scanButton = view.findViewById(R.id.search_btn);
        deviceRecyclerView = view.findViewById(R.id.bluetoothList);

        layoutManager = new LinearLayoutManager(getContext());
        deviceRecyclerView.setLayoutManager(layoutManager);

        devices = new ArrayList<>();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();


        adapter = new DeviceListAdapter(devices, this);
        deviceRecyclerView.setAdapter(adapter);




        scanButton.setOnClickListener(v -> {

            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }

            bluetoothAdapter.startDiscovery();
            //v.setVisibility(View.GONE);
        });


        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bluetoothDevice : pairedDevices) {
                devices.add(new DeviceInfo(bluetoothDevice.getName(), bluetoothDevice.getAddress()));
                adapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(getContext(), "Keine gekoppelten Geräte gefunden", Toast.LENGTH_SHORT).show();
        }



        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(mReceiver, filter);

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

/*
    private void addDevice (DeviceInfo device){
        DeviceInfo mDevice = device;
        devices.add(mDevice);
        adapter.notifyDataSetChanged();
    }
*/

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device != null && device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    devices.add(new DeviceInfo(device.getName(), device.getAddress()));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "new Device", Toast.LENGTH_LONG);
                }
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (bluetoothAdapter != null) {
            bluetoothAdapter.cancelDiscovery();
        }

        Toast.makeText(getActivity(), "Destroy Bottomsheet", Toast.LENGTH_LONG);

        // Unregister broadcast listeners
        getActivity().unregisterReceiver(mReceiver);
    }




}
