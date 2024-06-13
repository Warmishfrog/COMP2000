package com.example.comp2000referral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Home_page extends AppCompatActivity {

    Boolean notifystate;
    Integer notifyInt;
    String user_un, user_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            notifystate = bundle.getBoolean("notifystate");
            user_un= bundle.getString("user_un");
            user_pw = bundle.getString("user_pw");
            notifyInt = bundle.getInt("notifyInt");
        }

        if (notifystate && notifyInt == 1) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(Home_page.this, "notification");
            builder.setContentTitle("account update");
            builder.setContentText("Your account has been updated");
            builder.setSmallIcon(R.drawable.ic_launcher_background);
            builder.setAutoCancel(true);
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Home_page.this);
            managerCompat.notify(1, builder.build()); //ignore error
            Toast.makeText(getApplicationContext(),"NEW notification", Toast.LENGTH_LONG).show();
            notifyInt = 0;
        }

        Button logoutbutton = findViewById(R.id.logoutbutton);
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notifystate) Toast.makeText(getApplicationContext(),"SUCCESSFULLY LOGGED OUT", Toast.LENGTH_LONG).show();
                newpage(MainActivity.class);
            }
        });

        TextView loggedinas = findViewById(R.id.loggedinasVAR);
        if (user_un!=  null && user_pw !=null) loggedinas.setText(user_un);
        else loggedinas.setText("User");

        Button detailsbutton = findViewById(R.id.detailspageicon);
        detailsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpage(details_page.class);
            }
        });
        Button prefbutton = findViewById(R.id.prefpageicon);
        prefbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { newpage(pref_page.class); //change this to received request page
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
