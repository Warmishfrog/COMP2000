package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
TextView viewpassword;
TextView viewusername;
EditText InputUsername;
EditText InputPassword;

String login_USERNAME = "EmployeeJIM"; //edit username here
String login_PASSWORD = "Jimbo123"; //edit password here


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputUsername = findViewById(R.id.InputUsername);
        InputPassword = findViewById(R.id.InputPassword);

        Button loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InputUsername.getText().toString().equals(login_USERNAME) && (InputPassword.getText().toString().equals(login_PASSWORD))) { //FILL The thingies
                    Toast.makeText(MainActivity.this,"LOGIN successful", Toast.LENGTH_SHORT).show();
                    Openhome();
                }
                else{
                    Toast.makeText(MainActivity.this,"LOGIN failed", Toast.LENGTH_LONG).show();
                }
            }
            public void Openhome(){
                Intent intent = new Intent(MainActivity.this,Home_page.class);
                startActivity(intent);
            }
        });
    }
}