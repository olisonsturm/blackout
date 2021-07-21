package me.olisonsturm.blackout.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.olisonsturm.blackout.R;

public class LoadingActivity extends AppCompatActivity {

    @BindView(R.id.startImage)
    ImageView startImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
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