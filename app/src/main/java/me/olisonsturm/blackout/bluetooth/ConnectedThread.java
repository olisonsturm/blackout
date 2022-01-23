package me.olisonsturm.blackout.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectedThread extends Thread {

    private final BluetoothSocket mmSocket;
    private final Handler handler;
    private final InputStream mmInStream;
    private static OutputStream mmOutStream = null;

    public final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update

    public ConnectedThread(BluetoothSocket socket, Handler handler) {

        this.mmSocket = socket;
        this.handler = handler;

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }
        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        try {
            DataInputStream inStream = new DataInputStream(mmInStream);
            byte[] buffer = new byte[256];
            int bytes = inStream.read(buffer);
            String readMessage = new String(buffer, 0, bytes);
            Log.e("Blackbox", readMessage);
            handler.obtainMessage(MESSAGE_READ, readMessage).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Call this from the main activity to send data to the remote device */
    public static void sendDataToBlackbox(String string) {
        byte[] bytes = string.getBytes(); //converts entered String into bytes
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
            Log.e("Send Error", "Unable to send message", e);
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException ignored) {
        }
    }
}
