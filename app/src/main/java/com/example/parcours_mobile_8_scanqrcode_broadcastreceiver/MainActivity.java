package com.example.parcours_mobile_8_scanqrcode_broadcastreceiver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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




//    menu 1
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }
//    menu 2
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout_btn)
        {
//            is_connected => false
//            => loginActivity

            sharedpreferences.edit().clear().apply();
            sharedpreferences.edit().putBoolean("is_connected",false).apply();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}
