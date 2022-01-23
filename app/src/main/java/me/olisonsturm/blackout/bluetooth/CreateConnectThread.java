package me.olisonsturm.blackout.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class CreateConnectThread extends Thread {

    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final BluetoothSocket mmSocket;
    private Handler handler;

    private ConnectedThread connectedThread;

    public final static int CONNECTING_STATUS = 1; // used in bluetooth handler to identify message status

    public CreateConnectThread(BluetoothAdapter bluetoothAdapter, String address, Handler handler) {

        this.handler = handler;

            /*
            Use a temporary object that is later assigned to mmSocket
            because mmSocket is final.
             */
        BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
        BluetoothSocket tmp = null;

        try {
            tmp = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            Log.e("Bluetooth", "Socket's create() method failed", e);
        }
        this.mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.cancelDiscovery();
        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();
            Log.e("Bluetooth", "Device connected");
            handler.obtainMessage(CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                mmSocket.close();
                Log.e("Bluetooth", "Cannot connect to device");
                handler.obtainMessage(CONNECTING_STATUS, -1, -1).sendToTarget();
            } catch (IOException closeException) {
                Log.e("Bluetooth", "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        connectedThread = new ConnectedThread(mmSocket, handler);
        connectedThread.run();
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e("Bluetooth", "Could not close the client socket", e);
        }
    }
}
