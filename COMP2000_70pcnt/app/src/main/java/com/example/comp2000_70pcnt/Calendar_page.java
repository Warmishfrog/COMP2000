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

public class Calendar_page extends AppCompatActivity {

    Boolean switchstate, admin ;
    Integer reqoff, reqbool;
    String user_fn, user_sn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            switchstate = bundle.getBoolean("switchstate");
            admin = bundle.getBoolean("admin");
            reqoff = bundle.getInt("reqoff");
            user_fn = bundle.getString("user_fn");
            user_sn = bundle.getString("user_sn");
            reqbool = bundle.getInt("reqbool");
        }

        Button acc = findViewById(R.id.acceptreq);
        Button dec = findViewById(R.id.declinereq);
        TextView activereq = findViewById(R.id.activereq);


        if (reqbool == 1){
            acc.setEnabled(true);
            dec.setEnabled(true);
            if (user_fn != null && user_sn != null) activereq.setText(user_fn + " " +user_sn + " has made a holiday request for " + reqoff + " days off");
            else activereq.setText("Employee has made a holiday request for " + reqoff + " days off");
        }
        else {
            activereq.setText("no current requests");
        }

        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Accepted Request", Toast.LENGTH_LONG).show();
                reqbool = 2; //2 SIGNIFIES ACCEPTED
                activereq.setText("no current requests");
                acc.setEnabled(false);
                dec.setEnabled(false);
            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Declined Request", Toast.LENGTH_LONG).show();
                reqbool = 3; //3 SIGNIFIES DECLINED
                reqoff = 0;
                activereq.setText("no current requests");
                acc.setEnabled(false);
                dec.setEnabled(false);
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
        ImageButton notificationbutton = findViewById(R.id.notificationpageicon);
        notificationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpage(notification_page.class);
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