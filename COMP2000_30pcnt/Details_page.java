package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Details_page extends AppCompatActivity {

    boolean editable = false;
    EditText detail_username;
    EditText detail_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        detail_username = findViewById(R.id.detail_username);
        detail_password = findViewById(R.id.detail_password);

        ImageButton homebutton = findViewById(R.id.homepageicon);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details_page.this,Home_page.class);
                startActivity(intent);
            }
        });
        ImageButton calendarbutton = findViewById(R.id.calendarpageicon);
        calendarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details_page.this,Calendar_page.class);
                startActivity(intent);
            }
        });
        ImageButton notificationbutton = findViewById(R.id.notificationpageicon);
        notificationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details_page.this,Notification_page.class);
                startActivity(intent);
            }
        });

        Button EditButton = findViewById(R.id.Editbutton);

        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editable) {
                    Log.d("t", "bushy");
                    editable = true;
                    detail_username.setEnabled(false);
                    detail_password.setEnabled(false);
                    EditButton.setText("Edit Details");

                }
                else if (editable) {
                    editable = false;
                    detail_username.setEnabled(true);
                    detail_password.setEnabled(true);
                    EditButton.setText("Save Details");
                }
            }
        });
    }
}