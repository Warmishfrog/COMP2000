package com.example.comp2000referral;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class pref_page extends AppCompatActivity {

    Boolean notifystate;
    Integer notifyInt;
    String user_un, user_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_page);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel( "notification", "newnotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            notifystate = bundle.getBoolean("notifystate");
            user_un= bundle.getString("user_un");
            user_pw = bundle.getString("user_pw");
            notifyInt = bundle.getInt("notifyInt");
        }

        Switch notswitch = (Switch) findViewById(R.id.notificationtoggle);
        if(notifystate == null) notifystate = notswitch.isChecked();
        if(notifystate) notswitch.setChecked(true);
        else notswitch.setChecked(false);

        notswitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                 notifystate = notswitch.isChecked();

                 if (notifystate) Toast.makeText(pref_page.this,"Notifications Enabled", Toast.LENGTH_SHORT).show();
                 else Toast.makeText(pref_page.this,"Notifications Disabled", Toast.LENGTH_SHORT).show();
            }
        });
        Button simnotbutton = findViewById(R.id.simnotbutton); //SIMULATE NOTIFICATION
        simnotbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (notifystate) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(pref_page.this, "notification");
                    builder.setContentTitle("New notification");
                    builder.setContentText("Notification Received");
                    builder.setSmallIcon(R.drawable.ic_launcher_background);
                    builder.setAutoCancel(true);
                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(pref_page.this);
                    managerCompat.notify(1, builder.build()); //ignore error
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
            public void onClick(View v) {newpage(details_page.class);
            }
        });
        Button prefbutton = findViewById(R.id.prefpageicon);
        prefbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {newpage(pref_page.class);
            }
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