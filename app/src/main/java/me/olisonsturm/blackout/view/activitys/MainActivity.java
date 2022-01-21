package me.olisonsturm.blackout.view.activitys;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Set;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.model.PlayerViewModel;
import me.olisonsturm.blackout.view.fragments.PlayerFragment;
import me.olisonsturm.blackout.view.fragments.SpieleFragment;
import me.olisonsturm.blackout.view.fragments.StatisticsFragment;

import static me.olisonsturm.blackout.view.fragments.BluetoothChatFragment.REQUEST_CONNECT_DEVICE_SECURE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionMenuItemView bluetoothIcon;

    private DrawerLayout drawer;
    private ProgressDialog progress;
    private PlayerViewModel playerViewModel;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mDevice;

    private void findBlackbox() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                .getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equals("HC-05") || device.getAddress().equalsIgnoreCase("98:D3:31:F9:CA:0A"))
                this.mDevice = device;
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.myToolbar);
        navigationView = findViewById(R.id.nav_view);
        bluetoothIcon = (ActionMenuItemView) findViewById(R.id.bluetoothCheck);

        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //check to set bluetooth Icon
        if (!mBluetoothAdapter.isEnabled()) {
            bluetoothIcon.setIcon(getDrawable(R.drawable.ic_bluetooth_off));
        } else if (mBluetoothAdapter.isDiscovering()) {
            bluetoothIcon.setIcon(getDrawable(R.drawable.ic_bluetooth_discovering));
        } else {
            bluetoothIcon.setIcon(getDrawable(R.drawable.ic_bluetooth_on));
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
                    if (!mBluetoothAdapter.isEnabled()) {
                        mBluetoothAdapter.enable();
                        bluetoothIcon.setIcon(getDrawable(R.drawable.ic_bluetooth_on));
                    } else {
                        if (!mBluetoothAdapter.isDiscovering()) {
                            mBluetoothAdapter.startDiscovery();
                            bluetoothIcon.setIcon(getDrawable(R.drawable.ic_bluetooth_discovering));
                        }
                        // Bluetooth an und am Suchen
                        Intent serverIntent = new Intent(this, DeviceListActivity.class);
                        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                    }
                    break;

                case R.id.deletePlayers:
                    playerViewModel.deleteAllPlayers();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SpieleFragment()).commit();
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


}

