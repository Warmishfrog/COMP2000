package com.example.comp2000_70pcnt;

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
    EditText InputUsername, InputPassword;
    String login_USERNAME_e;
    String login_PASSWORD_e = "123"; //edit password here
    String login_USERNAME = "Admin"; //edit admin username here
    String login_PASSWORD = "456"; //edit admin password here

    Boolean switchstate, admin;
    Integer reqoff, reqbool;
    String user_fn, user_sn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            switchstate = bundle.getBoolean("switchstate");
            reqoff = bundle.getInt("reqoff");
            user_fn = bundle.getString("user_fn");
            user_sn = bundle.getString("user_sn");
            reqbool = bundle.getInt("reqbool");
        }

        if (user_fn == null && user_sn == null) login_USERNAME_e = "Employee"; //edit employee login here
        else login_USERNAME_e = user_fn+user_sn;
        if (switchstate == null) switchstate = true;

        InputUsername = findViewById(R.id.InputUsername);
        InputPassword = findViewById(R.id.InputPassword);

        Button loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InputUsername.getText().toString().equals(login_USERNAME_e) && (InputPassword.getText().toString().equals(login_PASSWORD_e))) { //FILL The thingies
                    admin = false;
                    if (switchstate) Toast.makeText(MainActivity.this,"LOGIN successful", Toast.LENGTH_SHORT).show();
                    newpage(Home_page.class);
                }
                else if (InputUsername.getText().toString().equals("Employee") && (InputPassword.getText().toString().equals(login_PASSWORD_e))) { //FILL The admin thingies
                    admin = false;
                    user_fn = null;
                    user_sn = null;
                    if (switchstate) Toast.makeText(MainActivity.this,"LOGIN successful", Toast.LENGTH_SHORT).show();
                    newpage(Home_page.class);
                }
                else if (InputUsername.getText().toString().equals(login_USERNAME) && (InputPassword.getText().toString().equals(login_PASSWORD))) { //FILL The admin thingies
                    admin = true;
                    if (switchstate) Toast.makeText(MainActivity.this,"Admin LOGIN successful", Toast.LENGTH_SHORT).show();
                    newpage(Home_page.class);
                }

                else
                if (switchstate) Toast.makeText(MainActivity.this,"LOGIN failed", Toast.LENGTH_LONG).show();
            }
            public void newpage(Class whatpage){
                Intent intent = new Intent(getApplicationContext(),whatpage);
                intent.putExtra("switchstate", switchstate);
                intent.putExtra("admin", admin);
                intent.putExtra("reqoff", reqoff);
                intent.putExtra("user_fn", user_fn);
                intent.putExtra("user_sn", user_sn);
                intent.putExtra("reqbool", reqbool);
                startActivity(intent);
            }
        });
    }
}