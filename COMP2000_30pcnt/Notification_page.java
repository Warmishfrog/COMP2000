package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Notification_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel( "notification", "newnotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Button simnotbutton = findViewById(R.id.simnotbutton); //SIMULATE NOTIFICATION
        simnotbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(Notification_page.this,"NEW notification", Toast.LENGTH_LONG).show();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(Notification_page.this, "notification");
                builder.setContentTitle("New notification");
                builder.setContentText("Notification Received");
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Notification_page.this);
                managerCompat.notify(1, builder.build());
            }
        });

        ImageButton homebutton = findViewById(R.id.homepageicon);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notification_page.this,Home_page.class);
                startActivity(intent);
            }
        });
        ImageButton calendarbutton = findViewById(R.id.calendarpageicon);
        calendarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notification_page.this,Calendar_page.class);
                startActivity(intent);
            }
        });
        ImageButton detailsbutton = findViewById(R.id.detailspageicon);
        detailsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notification_page.this,Details_page.class);
                startActivity(intent);
            }
        });

    }
}