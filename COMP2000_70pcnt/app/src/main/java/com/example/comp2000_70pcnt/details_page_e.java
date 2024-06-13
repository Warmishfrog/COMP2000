package com.example.comp2000_70pcnt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class details_page_e extends AppCompatActivity {

    Boolean editable = false;
    EditText detail_forename, detail_surname;
    Boolean switchstate, admin ;
    Integer reqoff, reqbool;
    String user_fn, user_sn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page_e);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            switchstate = bundle.getBoolean("switchstate");
            admin = bundle.getBoolean("admin");
            reqoff = bundle.getInt("reqoff");
            user_fn = bundle.getString("user_fn");
            user_sn = bundle.getString("user_sn");
            reqbool = bundle.getInt("reqbool");
        }

        detail_forename = findViewById(R.id.detail_forename);
        detail_surname = findViewById(R.id.detail_surname);
        detail_forename.setText(user_fn);
        detail_surname.setText(user_sn);

        Button homebutton = findViewById(R.id.homepageicon);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpage(Home_page.class);
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
        ImageButton notificationbutton = findViewById(R.id.notificationpageicon);
        notificationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpage(notification_page.class);
            }
        });

        Switch EditButton = (Switch) findViewById(R.id.Editbutton);

        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editable = EditButton.isChecked();
                detail_forename.setEnabled(editable);
                detail_surname.setEnabled(editable);
                if (!editable){
                    user_fn = detail_forename.getText().toString();
                    user_sn = detail_surname.getText().toString();
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