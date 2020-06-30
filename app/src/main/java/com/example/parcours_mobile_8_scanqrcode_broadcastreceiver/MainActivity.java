package com.example.parcours_mobile_8_scanqrcode_broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //        instance du shared Pref
        sharedpreferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
        Toast.makeText(this, "email "+sharedpreferences.getString("user_email",null), Toast.LENGTH_SHORT).show();


    }
}
