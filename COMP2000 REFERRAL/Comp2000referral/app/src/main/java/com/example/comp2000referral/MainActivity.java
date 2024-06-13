package com.example.comp2000referral;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText InputUsername, InputPassword;
    String login_USERNAME = "user"; //default username
    String login_PASSWORD = "123"; //default password

    Boolean notifystate;
    Integer notifyInt;
    String user_un, user_pw;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            notifystate = bundle.getBoolean("notifystate");
            user_un= bundle.getString("user_un");
            user_pw = bundle.getString("user_pw");
            notifyInt = bundle.getInt("notifyInt");
        }

        Log.d("debug time", user_un +" " + user_pw);

        if (user_un== null && user_pw == null) login_USERNAME = "user"; //edit username input
        else {login_USERNAME = user_un; login_PASSWORD = user_pw;} //Overriding user details
        if (notifystate == null) notifystate = true;

        InputUsername = findViewById(R.id.InputUsername);
        InputPassword = findViewById(R.id.InputPassword);

        Button loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InputUsername.getText().toString().equals(login_USERNAME) && (InputPassword.getText().toString().equals(login_PASSWORD))) { //FILL The thingies
                    if (notifystate) Toast.makeText(MainActivity.this,"LOGIN successful", Toast.LENGTH_SHORT).show();
                    newpage(Home_page.class);
                }
                else if (notifystate) Toast.makeText(MainActivity.this,"LOGIN failed", Toast.LENGTH_LONG).show();
                }


        });
        Button createbutton = findViewById(R.id.createbutton);
        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){newpage(createaccount.class);}
        });

    }
    public void newpage(Class whatpage){
        Intent intent = new Intent(getApplicationContext(),whatpage);
        intent.putExtra("notifystate", notifystate);
        intent.putExtra("user_un", user_un);
        intent.putExtra("user_pw", user_pw);
        intent.putExtra("notifyInt", notifyInt);
        startActivity(intent);
    }
}