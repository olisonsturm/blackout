package me.olisonsturm.blackout.view.activitys;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;


import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.bluetooth.CreateConnectThread;
import me.olisonsturm.blackout.bluetooth.SelectDeviceActivity;
import me.olisonsturm.blackout.model.PlayerViewModel;
import me.olisonsturm.blackout.view.fragments.GamesFragment;
import me.olisonsturm.blackout.view.fragments.PlayerFragment;
import me.olisonsturm.blackout.view.fragments.StatisticsFragment;

import static me.olisonsturm.blackout.bluetooth.ConnectedThread.MESSAGE_READ;
import static me.olisonsturm.blackout.bluetooth.CreateConnectThread.CONNECTING_STATUS;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Handler handler;
    private CreateConnectThread createConnectThread;
    private BluetoothAdapter bluetoothAdapter;
    private String deviceName = null;
    private String deviceAddress = null;


    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionMenuItemView bluetoothIcon;

    private DrawerLayout drawer;
    private PlayerViewModel playerViewModel;

    @SuppressLint({"NonConstantResourceId", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.myToolbar);
        navigationView = findViewById(R.id.nav_view);
        bluetoothIcon = (ActionMenuItemView) findViewById(R.id.bluetoothCheck);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If a bluetooth device has been selected from SelectDeviceActivity
        deviceName = getIntent().getStringExtra("deviceName");
        if (deviceName != null) {
            // Get the device address to make BT Connection
            deviceAddress = getIntent().getStringExtra("deviceAddress");
            // Show progree and connection status
            toolbar.setSubtitle("Connecting to " + deviceName + "...");

            /*
            This is the most important piece of code. When "deviceName" is found
            the code will call a new thread to create a bluetooth connection to the
            selected device (see the thread code below)
             */
            createConnectThread = new CreateConnectThread(bluetoothAdapter, deviceAddress, handler);
            createConnectThread.start();
        }

        //check to set bluetooth Icon
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothIcon.setIcon(getDrawable(R.drawable.ic_bluetooth_off));
        } else {
            bluetoothIcon.setIcon(getDrawable(R.drawable.ic_bluetooth_on));
        }

        if (bluetoothAdapter.isDiscovering()) {
            bluetoothIcon.setIcon(getDrawable(R.drawable.ic_bluetooth_discovering));
        }


        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlayerFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_lobby);
        }


        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.bluetoothCheck:
                    if (!bluetoothAdapter.isEnabled()) {
                        bluetoothAdapter.enable();
                        bluetoothIcon.setIcon(getDrawable(R.drawable.ic_bluetooth_on));
                    } else {
                        Intent intent = new Intent(MainActivity.this, SelectDeviceActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.deletePlayers:
                    playerViewModel.deleteAllPlayers();
                    break;
            }
            return false;
        });

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case CONNECTING_STATUS:
                        switch (msg.arg1) {
                            case 1:
                                toolbar.setSubtitle("Connected to " + deviceName);
                                // toolbar banner
                                break;
                            case -1:
                                toolbar.setSubtitle("Device fails to connect");
                                // toolbar banner
                                break;
                        }
                        break;

                    case MESSAGE_READ:
                        String arduinoMsg = msg.obj.toString(); // Read message from Arduino
                        switch (arduinoMsg.toLowerCase()) {
                            case "led is turned on":
                                //textViewInfo.setText("Arduino Message : " + arduinoMsg);
                                break;
                            case "led is turned off":
                                //textViewInfo.setText("Arduino Message : " + arduinoMsg);
                                break;
                        }
                        break;
                }
            }
        };
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_lobby:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlayerFragment()).commit();
                break;

            case R.id.nav_gaming:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GamesFragment()).commit();
                break;

            case R.id.nav_statistics:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StatisticsFragment()).commit();
                break;

            case R.id.nav_settings:
                Toast.makeText(this, "App Settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_appinfo:
                Toast.makeText(this, "Daten über die App", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        // Terminate Bluetooth Connection and close app
        if (createConnectThread != null) {
            createConnectThread.cancel();
        }
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

//    public static void sendDataToBlackbox(String string) {
//        connectedThread.write(string);
//    }
//
//    public static void sendDataToBlackbox(int integer) {
//        connectedThread.write(String.valueOf(integer));
//    }
/*

    */
/* ============================ Thread to Create Bluetooth Connection =================================== *//*

    public static class CreateConnectThread extends Thread {

        public CreateConnectThread(BluetoothAdapter bluetoothAdapter, String address) {
            */
/*
            Use a temporary object that is later assigned to mmSocket
            because mmSocket is final.
             *//*

            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
            BluetoothSocket tmp = null;

            try {
                tmp = bluetoothDevice.createRfcommSocketToServiceRecord(myUUID);

            } catch (IOException e) {
                Log.e("Bluetooth", "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothAdapter.cancelDiscovery();
            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
                Log.e("Status", "Device connected");
                handler.obtainMessage(CONNECTING_STATUS, 1, -1).sendToTarget();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                    Log.e("Status", "Cannot connect to device");
                    handler.obtainMessage(CONNECTING_STATUS, -1, -1).sendToTarget();
                } catch (IOException closeException) {
                    Log.e("Bluetooth", "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            connectedThread = new ConnectedThread(mmSocket);
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

    */
/* =============================== Thread for Data Transfer =========================================== *//*

    public static class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
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
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes = 0; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    */
/*
                    Read from the InputStream from Arduino until termination character is reached.
                    Then send the whole String message to GUI Handler.
                     *//*

                    buffer[bytes] = (byte) mmInStream.read();
                    String readMessage;
                    if (buffer[bytes] == '\n') {
                        readMessage = new String(buffer, 0, bytes);
                        Log.e("Arduino Message", readMessage);
                        handler.obtainMessage(MESSAGE_READ, readMessage).sendToTarget();
                        bytes = 0;
                    } else {
                        bytes++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        */
/* Call this from the main activity to send data to the remote device *//*

        public void write(String input) {
            byte[] bytes = input.getBytes(); //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e("Send Error", "Unable to send message", e);
            }
        }

        */
/* Call this from the main activity to shutdown the connection *//*

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException ignored) {
            }
        }
    }
*/

}


