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

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout edit_email,edit_password,edit_first_name,edit_last_name,edit_phone;
    private Button btn_register;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.progressBar2);

        edit_email = findViewById(R.id.email_input);
        edit_password = findViewById(R.id.password_input);
        edit_first_name = findViewById(R.id.first_name_input);
        edit_last_name = findViewById(R.id.last_name_input);
        edit_phone = findViewById(R.id.phone_input);

        btn_register = findViewById(R.id.register_btn);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                1 : str from all edit text
//                2 : api
                String email = edit_email.getEditText().getText().toString();
                String password = edit_password.getEditText().getText().toString();
                String first_name = edit_first_name.getEditText().getText().toString();
                String last_name = edit_last_name.getEditText().getText().toString();
                String phone = edit_phone.getEditText().getText().toString();

                if(email.isEmpty() || password.isEmpty() || first_name.isEmpty() || last_name.isEmpty() || phone.isEmpty())
                    Toast.makeText(RegisterActivity.this, "complete the form ... !", Toast.LENGTH_SHORT).show();
                else
                    RegisterUser(email,password,first_name,last_name,phone);


            }
        });


    }

    private void RegisterUser(String email, String password, String first_name, String last_name, String phone) {
        progressBar.setVisibility(View.VISIBLE);

        String url ="https://backend-people-crud-app.herokuapp.com/users/register";

        JsonObject json_data = new JsonObject();
        json_data.addProperty("email",email);
        json_data.addProperty("password",password);
        json_data.addProperty("firstname",first_name);
        json_data.addProperty("lastname",last_name);
        json_data.addProperty("phone",phone);



        Ion.with(RegisterActivity.this)
                .load(url)
                .setJsonObjectBody(json_data)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null)
                            Toast.makeText(RegisterActivity.this, "Exception : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        else
                        {
//  case 1 : register successfully
//  {
//    "message": "Admin registred successfully"
//  }

//  case 2 :
//  "errmsg": "E11000 duplicate key error index: heroku_cdrtqp5m.users.$email_1 dup key: { : \"dali@gmail\" }"

                            if(!result.has("message"))
                            {
//                                 case 2
                                String err = result.get("errmsg").getAsString();
                                Toast.makeText(RegisterActivity.this, "Error : "+err, Toast.LENGTH_SHORT).show();
                                edit_email.setError("duplicate email");

                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Admin registred successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }

                            progressBar.setVisibility(View.GONE);


                        }


                    }
                });

    }
}
