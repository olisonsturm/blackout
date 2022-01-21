package me.olisonsturm.blackout.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.UUID;

public class MessageThread extends Thread {
    private TextView tv;
    private final static String TAG = "MessageThread";
    private final static String MY_UUID = "00001101-0000-1000-8000-00805f9b34fb";
    private BluetoothSocket mSocket = null;
    private String mMessage;


    public MessageThread(BluetoothDevice device, String message) {
        Log.d(TAG, "Trying to send message...");
        this.mMessage = message;
        try {
            UUID uuid = UUID.fromString(MY_UUID);
            mSocket = device.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MessageThread(BluetoothDevice device) {
        try {
            UUID uuid = UUID.fromString(MY_UUID);
            mSocket = device.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMessage(String message) {
        this.mMessage = mMessage;
    }

    private void manageConnectedSocket(BluetoothSocket socket) throws IOException {
        Log.d(TAG, "Connection successful");
        OutputStream os = socket.getOutputStream();
        PrintStream sender = new PrintStream(os);
        sender.print(mMessage);
        Log.d(TAG, "Message sent");

        InputStream is = socket.getInputStream();
        DataInputStream inStream = new DataInputStream(is);
        byte[] buffer = new byte[256];
        int bytes = inStream.read(buffer);
        String readMessage = new String(buffer, 0, bytes);
        Log.d(TAG, "Received: " + readMessage);
    }

    public void run() {
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        try {
            mSocket.connect();
            manageConnectedSocket(mSocket);
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

