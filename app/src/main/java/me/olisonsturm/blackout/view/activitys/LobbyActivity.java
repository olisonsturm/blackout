package me.olisonsturm.blackout.view.activitys;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.view.Infos.ConnectionInfo;
import me.olisonsturm.blackout.view.fragments.GamingFragment;
import me.olisonsturm.blackout.view.fragments.PlayerFragment;
import me.olisonsturm.blackout.view.fragments.StatisticsFragment;

public class LobbyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomSheetBluetooth.BottomSheetBluetoothListener {

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    Toolbar toolbar;
    NavigationView navigationView;
    //MenuItem bluetoothIcon;
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    InputStream inputStream;
    OutputStream outputStream;
    String deviceName = null;
    String deviceAddress = null;
    private DrawerLayout drawer;
    private ProgressDialog progress;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.myToolbar);
        navigationView = findViewById(R.id.nav_view);
        //bluetoothIcon =  (MenuItem) findViewById(R.id.bluetoothCheck);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        new ConnectionInfo(false);


        //check to set bluetooth Icon
        /*
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothIcon.setIcon(R.drawable.ic_bluetooth_off);
        } else {
            bluetoothIcon.setIcon(R.drawable.ic_bluetooth_on);
        }
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothIcon.setIcon(R.drawable.ic_bluetooth_discovering);
        }*/


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
                        Toast.makeText(LobbyActivity.this, "Aktiviere Bluetooth", Toast.LENGTH_SHORT).show();
                        bluetoothAdapter.enable();
                        //bluetoothIcon.setIcon(R.drawable.ic_bluetooth_on);
                    }

                    if (!bluetoothAdapter.isDiscovering()) {
                        Toast.makeText(LobbyActivity.this, "mache Gerät sichtbar", Toast.LENGTH_SHORT).show();
                        bluetoothAdapter.startDiscovery();
                        //bluetoothIcon.setIcon(R.drawable.ic_bluetooth_discovering);
                    }
                    BottomSheetBluetooth bottomSheetBluetooth = new BottomSheetBluetooth();
                    bottomSheetBluetooth.show(getSupportFragmentManager(), "exampleBottomSheet");
                    break;

                case R.id.more:
                    Toast.makeText(LobbyActivity.this, "More", Toast.LENGTH_SHORT).show();
                    break;
            }


            return false;
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_lobby:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlayerFragment()).commit();
                break;

            case R.id.nav_gaming:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GamingFragment()).commit();
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
    }


    @Override
    public void onItemClicked(String name, String address) {
        deviceName = name;
        deviceAddress = address;

        //Toast.makeText(this, "Device: " + deviceName + ", " + deviceAddress, Toast.LENGTH_LONG).show();
        new ConnectBT().execute();
    }


    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true; // UI thread

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(LobbyActivity.this, "Connecting to " + deviceName, "please wait!!!"); //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) { //while the progress dialog is shown, the connection is done in background
            try {
                if (bluetoothSocket == null || !ConnectionInfo.isConnected()) {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //get the mobile bluetooth device
                    BluetoothDevice dispositivo = bluetoothAdapter.getRemoteDevice(deviceAddress);//connects to the device's address and checks if it's available
                    bluetoothSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID); //create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery(); //stop Discovery
                    bluetoothSocket.connect();//start connection
                    outputStream = bluetoothSocket.getOutputStream();
                    inputStream = bluetoothSocket.getInputStream();
                }
            } catch (IOException e) {
                ConnectSuccess = false; //if the try failed, you can check the exception here
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {  // after doInBackground to check if everything went fine
            super.onPreExecute();

            if (!ConnectSuccess) {
                Toast.makeText(LobbyActivity.this, "Connection Failed. Is it a SPP Bluetooth? Try again!", Toast.LENGTH_LONG).show();
                /*if (bluetoothAdapter.isEnabled()) {
                    bluetoothIcon.setIcon(R.drawable.ic_bluetooth_on);
                }
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothIcon.setIcon(R.drawable.ic_bluetooth_discovering);
                }*/
            } else {
                Toast.makeText(LobbyActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                ConnectionInfo.setConnected(true);
                //bluetoothIcon.setIcon(R.drawable.ic_bluetooth_off);
            }
            progress.dismiss();
        }
    }


}