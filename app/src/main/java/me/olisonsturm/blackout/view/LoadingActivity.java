package me.olisonsturm.blackout.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import me.olisonsturm.blackout.R;

public class LoadingActivity extends AppCompatActivity {


    ImageView startImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        startImage = findViewById(R.id.startImage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, LobbyActivity.class));
            finish();
        }, 1000);
    }
}