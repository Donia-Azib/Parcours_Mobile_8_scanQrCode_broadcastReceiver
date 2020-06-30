package com.example.parcours_mobile_8_scanqrcode_broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout edit_email,edit_password;
    private Button btn_login;
    private Button register;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar);

        edit_email = findViewById(R.id.email_input);
        edit_password = findViewById(R.id.password_input);

        btn_login = findViewById(R.id.login_btn);

        register = findViewById(R.id.create_account);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                -- scenario :
//                -- 1 : get email and password from input
//                -- 2 : api

                String email = edit_email.getEditText().getText().toString();
                String password = edit_password.getEditText().getText().toString();

                if(email.isEmpty() || password.isEmpty())
                    Toast.makeText(LoginActivity.this, "complete form .. !", Toast.LENGTH_SHORT).show();
                else
                    UserLogin(email,password);


            }
        });


    }

    private void UserLogin(String email_str, String password_str) {
        progressBar.setVisibility(View.VISIBLE);

        String url ="https://backend-people-crud-app.herokuapp.com/users/login";

        JsonObject json_data = new JsonObject();
//        --"email"+value , "password"+value
        json_data.addProperty("email",email_str);
        json_data.addProperty("password",password_str);
//        {
//        "email" : "ghassen@yahoo.fr",
//        "password" : "azertyu25"
//        }

        Ion.with(LoginActivity.this)
                .load(url)
                .setJsonObjectBody(json_data)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e !=null)
                            Toast.makeText(LoginActivity.this, "Exception "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        else
                        {
//                            --"message" : "erreur"
//                            --"token" : "azerthjdnk.ssvdhz....."

//                            --if ma3andich att message => login successfully
//                            --if att message fih donnee => fama une erreur
                            if(!result.has("message"))
                            {
//                               login successfully
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
//                               fama une erreur
                                String msg = result.get("message").getAsString();
                                Toast.makeText(LoginActivity.this, "Error :  "+msg , Toast.LENGTH_SHORT).show();
                            }

                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });




    }
}
