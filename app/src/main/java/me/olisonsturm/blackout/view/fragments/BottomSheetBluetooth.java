package me.olisonsturm.blackout.view.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
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

import static android.app.Activity.RESULT_OK;

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

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(mReceiver, filter);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                devices.add(new DeviceInfo(device.getName(), device.getAddress()));
                adapter.notifyDataSetChanged();
                System.out.println("GERÄTE IN DER NÄHE GEFUNDEN");
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_bluetooth_layout, container, false);

        scanButton = view.findViewById(R.id.search_btn);
        deviceRecyclerView = view.findViewById(R.id.bluetoothList);

        layoutManager = new LinearLayoutManager(getContext());
        deviceRecyclerView.setLayoutManager(layoutManager);

        devices = new ArrayList<>();

        adapter = new DeviceListAdapter(devices, this);
        deviceRecyclerView.setAdapter(adapter);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            //finish apk
            getActivity().finish();
        } else if (!bluetoothAdapter.isEnabled()) {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);
        } else if (bluetoothAdapter.isEnabled()) {
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice bluetoothDevice : pairedDevices) {
                    devices.add(new DeviceInfo(bluetoothDevice.getName(), bluetoothDevice.getAddress()));
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Alle gekoppelten Geräte geladen", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Keine gekoppelten Geräte gefunden", Toast.LENGTH_SHORT).show();
            }
        }
        ;

        //Intent Filters to receive only the intents we want
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        //Register the broadcast receiver
        getActivity().registerReceiver(mReceiver, filter);

        scanButton.setOnClickListener(v -> {
            bluetoothAdapter.startDiscovery();
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        bluetoothAdapter.startDiscovery();
        adapter.notifyDataSetChanged();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(getActivity(), "Please Switch on Bluetooth", Toast.LENGTH_SHORT).show();
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon, 1);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Make sure we're not doing discovery anymore
        if (bluetoothAdapter != null) {
            bluetoothAdapter.cancelDiscovery();
        }
        getActivity().unregisterReceiver(mReceiver);
    }

}
