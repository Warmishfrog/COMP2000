package com.example.comp2000_70pcnt;

import static java.sql.DriverManager.println;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Calendar_page_e extends AppCompatActivity {

    Boolean switchstate, admin ;
    Integer reqoff, reqbool;
    String user_fn, user_sn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page_e);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            switchstate = bundle.getBoolean("switchstate");
            admin = bundle.getBoolean("admin");
            reqoff = bundle.getInt("reqoff");
            user_fn = bundle.getString("user_fn");
            user_sn = bundle.getString("user_sn");
            reqbool = bundle.getInt("reqbool");
        }

        TextView daysleft;
        daysleft = findViewById(R.id.daysleft);
        daysleft.setText("You have " + (30 - reqoff) + " days left");

        Button homebutton = findViewById(R.id.homepageicon);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpage(Home_page.class);
            }
        });
        Button detailsbutton = findViewById(R.id.detailspageicon);
        detailsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (admin) newpage(details_page.class);
                else newpage(details_page_e.class);
            }
        });
        ImageButton notificationbutton = findViewById(R.id.notificationpageicon);
        notificationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpage(notification_page.class);
            }
        });

        EditText numberinput;
        numberinput = findViewById(R.id.numberinput);
        Button submitbutton = findViewById(R.id.submitbutton);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int input = Integer.parseInt(numberinput.getText().toString());
                    if (input <= 30){
                        int output = (30-reqoff) - input; //30 days minus days requested
                        if (output < 0){
                            Toast.makeText(Calendar_page_e.this, "The value you have entered is too large", Toast.LENGTH_LONG).show();
                        }
                        else{
                            reqoff += input;
                            reqbool = 1;
                            daysleft.setText("You have " + (30 - reqoff) + " days left");
                            Toast.makeText(Calendar_page_e.this, "Request Sent Successfully", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Calendar_page_e.this, "The value you have entered is too large", Toast.LENGTH_LONG).show();
                    }
                }
                catch (NumberFormatException exception){ //null exception
                    Toast.makeText(Calendar_page_e.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    Log.d("error", "String entered was not accepted");
                }
            }
        });
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
}