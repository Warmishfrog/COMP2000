package com.example.hello;

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

public class Calendar_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);

        ImageButton homebutton = findViewById(R.id.homepageicon);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calendar_page.this,Home_page.class);
                startActivity(intent);
            }
        });
        ImageButton detailsbutton = findViewById(R.id.detailspageicon);
        detailsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calendar_page.this,Details_page.class);
                startActivity(intent);
            }
        });
        ImageButton notificationbutton = findViewById(R.id.notificationpageicon);
        notificationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calendar_page.this,Notification_page.class);
                startActivity(intent);
            }
        });

        EditText numberinput;
        numberinput = findViewById(R.id.numberinput);
        TextView daysleft;
        daysleft = findViewById(R.id.daysleft);
        Button submitbutton = findViewById(R.id.submitbutton);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int remaining = Integer.parseInt(numberinput.getText().toString());
                    remaining = 30 - remaining; //30 days minus days requested
                    if (remaining >= 0 && remaining <= 30){ //to prevent null exception
                        daysleft.setText("You have " + remaining + " days left");
                        Toast.makeText(Calendar_page.this, "Request Sent Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Calendar_page.this, "You have no more holidays available", Toast.LENGTH_LONG).show();
                    }
                }
                catch (NumberFormatException exception){ //null exception
                    Toast.makeText(Calendar_page.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    Log.d("error", "String entered was not accepted");
                }
            }
        });
    }
}