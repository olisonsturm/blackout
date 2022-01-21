package me.olisonsturm.blackout.view.activitys;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.bluetooth.MessageThread;

public class TestActivity extends AppCompatActivity {

    private final static String TAG = "TestActivity";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mDevice;
    private EditText mMessageET;
    private Button mSendBN;
    private TextView mConsoleTV;

    private void findRaspberry() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                .getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equals("raspberrypi"))
                this.mDevice = device;
        }
    }

    private void initBluetooth() {
        Log.d(TAG, "Checking Bluetooth...");
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "Device does not support Bluetooth");
            mSendBN.setClickable(false);
        } else {
            Log.d(TAG, "Bluetooth supported");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            mSendBN.setClickable(false);
            Log.d(TAG, "Bluetooth not enabled");
        } else {
            Log.d(TAG, "Bluetooth enabled");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mMessageET = findViewById(R.id.message_et);
        mSendBN = findViewById(R.id.send_bn);
        mConsoleTV = findViewById(R.id.console_tv);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        initBluetooth();
        findRaspberry();
        if (mDevice != null) {
            mSendBN.setOnClickListener(view -> {
                String message = mMessageET.getText().toString();
                mMessageET.setText("");
                new MessageThread(mDevice, message).start();
            });
        }
    }

}