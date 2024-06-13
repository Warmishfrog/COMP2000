package com.example.comp2000_70pcnt;

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
import android.widget.Switch;
import android.widget.Toast;

public class notification_page extends AppCompatActivity {

    Boolean switchstate, admin ;
    Integer reqoff, reqbool;
    String user_fn, user_sn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel( "notification", "newnotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            switchstate = bundle.getBoolean("switchstate");
            admin = bundle.getBoolean("admin");
            reqoff = bundle.getInt("reqoff");
            user_fn = bundle.getString("user_fn");
            user_sn = bundle.getString("user_sn");
            reqbool = bundle.getInt("reqbool");
        }

        Switch notswitch = (Switch) findViewById(R.id.notificationtoggle);
        if(switchstate == null) switchstate = notswitch.isChecked();
        if(switchstate) notswitch.setChecked(true);
        else notswitch.setChecked(false);

        notswitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                 switchstate = notswitch.isChecked();

                 if (switchstate) Toast.makeText(notification_page.this,"Notifications Enabled", Toast.LENGTH_SHORT).show();
                 else Toast.makeText(notification_page.this,"Notifications Disabled", Toast.LENGTH_SHORT).show();
            }
        });
        Button simnotbutton = findViewById(R.id.simnotbutton); //SIMULATE NOTIFICATION
        simnotbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (switchstate) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(notification_page.this, "notification");
                    builder.setContentTitle("New notification");
                    builder.setContentText("Notification Received");
                    builder.setSmallIcon(R.drawable.ic_launcher_background);
                    builder.setAutoCancel(true);
                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(notification_page.this);
                    managerCompat.notify(1, builder.build());
                    Toast.makeText(getApplicationContext(),"NEW notification", Toast.LENGTH_LONG).show();
                }
            }
        });
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
        Button holidaybutton = findViewById(R.id.holidaypageicon);
        holidaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (admin) newpage(Calendar_page.class);
                else newpage(Calendar_page_e.class);
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