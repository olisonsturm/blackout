package me.olisonsturm.blackout.view.activitys;

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
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Set;

import me.olisonsturm.blackout.R;

public class BottomSheetBluetooth extends BottomSheetDialogFragment implements DeviceListAdapter.OnNoteListener {


    RecyclerView deviceList;
    RecyclerView.LayoutManager layoutManager;
    BluetoothAdapter bluetoothAdapter;
    ArrayList<DeviceInfo> devicelist;


    private Set<BluetoothDevice> pairedDevices;
    private ConnectionInfo connectionInfo;
    private BottomSheetBluetoothListener mListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_bluetooth_layout, container, false);

        deviceList = v.findViewById(R.id.bluetoothList);
        layoutManager = new LinearLayoutManager(getContext());
        deviceList.setLayoutManager(layoutManager);
        deviceList.setHasFixedSize(true);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        if (bluetoothAdapter.isEnabled() && bluetoothAdapter.isDiscovering()) {
            devicelist = new ArrayList<>();
            pairedDevices = bluetoothAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {
                for (BluetoothDevice bluetoothDevice : pairedDevices) {
                    devicelist.add(new DeviceInfo(bluetoothDevice.getName(), bluetoothDevice.getAddress()));
                }
            } else {
                Toast.makeText(getContext(), "Keine gekoppelten Geräte gefunden", Toast.LENGTH_SHORT);
            }

            DeviceListAdapter adapter = new DeviceListAdapter(devicelist, this);
            deviceList.setAdapter(adapter);
        }
        return v;
    }

    public interface BottomSheetBluetoothListener{
        void onItemClicked(String name, String adress);
    }

    public void onAttach(Context context){
        super.onAttach(context);
        try {
            mListener = (BottomSheetBluetoothListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement BottomSheetListener");
        }
    }

    @Override
    public void onNoteClick(int position) {
        Toast.makeText(getContext(),"Connecting to Device", Toast.LENGTH_SHORT);

        String name = devicelist.get(position).getName();
        String address = devicelist.get(position).getAddress();

        mListener.onItemClicked(name, address);
        dismiss();
    }

}
