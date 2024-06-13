package com.example.comp2000_70pcnt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Home_page extends AppCompatActivity {

    Boolean switchstate, admin ;
    Integer reqoff, reqbool;
    String user_fn, user_sn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            switchstate = bundle.getBoolean("switchstate");
            admin = bundle.getBoolean("admin");
            reqoff = bundle.getInt("reqoff");
            user_fn = bundle.getString("user_fn");
            user_sn = bundle.getString("user_sn");
            reqbool = bundle.getInt("reqbool");
        }

        if (admin == true && reqbool == 1 && switchstate == true) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "notification");
            builder.setContentTitle("New Employee Request");
            if (user_fn != null && user_sn != null) builder.setContentText(user_fn + " " + user_sn + " has made a holiday request for " + reqoff + " days off");
            else builder.setContentText("Employee has made a holiday request for " + reqoff + " days off");
            builder.setSmallIcon(R.drawable.ic_launcher_background);
            builder.setAutoCancel(true);
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
            managerCompat.notify(1, builder.build());
            Toast.makeText(getApplicationContext(), "NEW notification", Toast.LENGTH_LONG).show();
        }
        else if (admin == false && reqbool == 2 && switchstate == true){ //2 SIGNIFIES ACCEPTED
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "notification");
            builder.setContentTitle("Holiday Request Accepted");
            builder.setContentText("Your holiday request for " + reqoff + " days off has been accepted");
            builder.setSmallIcon(R.drawable.ic_launcher_background);
            builder.setAutoCancel(true);
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
            managerCompat.notify(1, builder.build());
            Toast.makeText(getApplicationContext(), "NEW notification from admin", Toast.LENGTH_LONG).show();
            reqbool = 0;
        }
        else if (admin == false && reqbool == 3 && switchstate == true){ //3 SIGNIFIES DECLINED
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "notification");
            builder.setContentTitle("Holiday Request Declined");
            builder.setContentText("Your holiday request for " + reqoff + " days off has been declined");
            builder.setSmallIcon(R.drawable.ic_launcher_background);
            builder.setAutoCancel(true);
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
            managerCompat.notify(1, builder.build());
            Toast.makeText(getApplicationContext(), "NEW notification from admin", Toast.LENGTH_LONG).show();
            reqbool = 0;
        }

        Button logoutbutton = findViewById(R.id.logoutbutton);
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchstate) Toast.makeText(getApplicationContext(),"SUCCESSFULLY LOGGED OUT", Toast.LENGTH_LONG).show();
                newpage(MainActivity.class);
            }
        });

        TextView loggedinas = findViewById(R.id.loggedinasVAR);
        if (admin) loggedinas.setText("Admin");
        else if (user_fn !=  null && user_sn !=null) loggedinas.setText(user_fn + " " + user_sn);
        else loggedinas.setText("Employee");

        Button detailsbutton = findViewById(R.id.detailspageicon);
        detailsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(admin) newpage(details_page.class);
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
        Button holidaybutton = findViewById(R.id.holidaypageicon);
        holidaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (admin) newpage(Calendar_page.class); //change this to received request page
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
