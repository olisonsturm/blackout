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
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.model.PlayerViewModel;
import me.olisonsturm.blackout.util.BluetoothChatService;
import me.olisonsturm.blackout.view.Infos.ConnectionInfo;
import me.olisonsturm.blackout.view.fragments.BottomSheetBluetooth;
import me.olisonsturm.blackout.view.fragments.SpieleFragment;
import me.olisonsturm.blackout.view.fragments.PlayerFragment;
import me.olisonsturm.blackout.view.fragments.StatisticsFragment;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionMenuItemView bluetoothIcon;

    private DrawerLayout drawer;
    private ProgressDialog progress;
    private PlayerViewModel playerViewModel;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mDevice;

    @SuppressLint({"NonConstantResourceId", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.myToolbar);
        navigationView = findViewById(R.id.nav_view);
        bluetoothIcon = (ActionMenuItemView) findViewById(R.id.bluetoothCheck);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        new ConnectionInfo(false);


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
                        if (!bluetoothAdapter.isDiscovering()) {
                            bluetoothAdapter.startDiscovery();
                            bluetoothIcon.setIcon(getDrawable(R.drawable.ic_bluetooth_discovering));
                        }

                        BottomSheetBluetooth bottomSheetBluetooth = new BottomSheetBluetooth();
                        bottomSheetBluetooth.show(getSupportFragmentManager(), "exampleBottomSheet");
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

