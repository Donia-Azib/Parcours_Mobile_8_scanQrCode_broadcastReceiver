package com.example.parcours_mobile_8_scanqrcode_broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SplashScreenActivity extends AppCompatActivity {

    private Button btn_start;
    private SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        instance du shared Pref
        sharedpreferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE);

        btn_start = findViewById(R.id.start_btn);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                verify if user is logged
                VerifyIfUserIsConnected();
            }
        });
    }

    private void VerifyIfUserIsConnected() {
//        boolean true => connected / false => not connected => "is_connected"
        if(sharedpreferences.contains("is_connected"))
        {
//            contain "is_connected"
            boolean is_connected = sharedpreferences.getBoolean("is_connected",false);
//            verify value
            if(is_connected)
            {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
        else
        {
//            ! contain "is_connected"
            sharedpreferences.edit().putBoolean("is_connected",false).apply();
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }
}
