package com.example.parcours_mobile_8_scanqrcode_broadcastreceiver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedpreferences;
    private Button scan_qr_code_btn,choose_pic_btn;

//    qr code 1
    private static final int REQUEST_CODE_QR_SCAN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //        instance du shared Pref
        sharedpreferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
        Toast.makeText(this, "email "+sharedpreferences.getString("user_email",null), Toast.LENGTH_SHORT).show();



        scan_qr_code_btn = findViewById(R.id.scan_qr_code_btn);
        scan_qr_code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                qr code 2
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, 20);
                }
                else
                {
                    Intent i = new Intent(MainActivity.this, QrCodeActivity.class);
                    startActivityForResult( i,REQUEST_CODE_QR_SCAN);
                }
            }
        });


    }//end on create

//    qr code 3
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == REQUEST_CODE_QR_SCAN)
            {
                if(data==null)
                    return;
                //Getting the passed result
                String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Scan result");
                alertDialog.setMessage(result);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        }




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

            new MaterialAlertDialogBuilder(MainActivity.this)
                    .setTitle("Disconnect !")
                    .setMessage("Are u sure ?! ")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sharedpreferences.edit().clear().apply();
                    sharedpreferences.edit().putBoolean("is_connected",false).apply();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            })
                    .setCancelable(false)
                    .show();





        }

        return super.onOptionsItemSelected(item);
    }
}
