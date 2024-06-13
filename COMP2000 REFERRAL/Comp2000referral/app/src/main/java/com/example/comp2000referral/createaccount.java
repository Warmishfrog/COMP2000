package com.example.comp2000referral;

import static java.lang.Integer.parseInt;
import static java.lang.Math.round;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class createaccount extends AppCompatActivity {

    EditText InputUsername, InputPassword, InputID;

    Boolean notifystate;
    Integer notifyInt;
    String user_un, user_pw;
    private RequestQueue mRequestQueue;

    private String url = "http://web.socem.plymouth.ac.uk/COMP2000/api/referral/students";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            notifystate = bundle.getBoolean("notifystate");
            user_un= bundle.getString("user_un");
            user_pw = bundle.getString("user_pw");
            notifyInt = bundle.getInt("notifyInt");
        }

        InputID = findViewById(R.id.InputID);
        String IDtempvalue = String.valueOf((int)(Math.random() *(100 - 1) + 1) );  //generate a random number up to 100
        Log.d("debug",IDtempvalue);
        InputID.setText(IDtempvalue);// pre-load this for the user so they don't have to choose a number

        Button createbutton = findViewById(R.id.createbutton);
        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                POST();
            }

        });
        Button returnlogin = findViewById(R.id.returnlogin);
        returnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){newpage(MainActivity.class);}
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
    private void POST() {

        InputUsername = findViewById(R.id.InputUsername);
        InputPassword = findViewById(R.id.InputPassword);

        try {
            //int ID_value =  (int)(Math.random() *(100 - 1) + 1); //generates a random ID from 1 to 100 to assign to the user ID

            int info_id = parseInt(InputID.getText().toString());
            String info_un = InputUsername.getText().toString();
            String info_pw = InputPassword.getText().toString();

            mRequestQueue = Volley.newRequestQueue(createaccount.this);
            JSONObject datapayload = new JSONObject(); //creates payload
            try { //fills payload
                datapayload.put("id", info_id);
                datapayload.put("username", info_un);
                datapayload.put("password", info_pw);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //submits payload
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, datapayload, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (notifystate)
                        Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                    newpage(MainActivity.class);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 307) { //NO API
                        if (notifystate) {
                            Toast.makeText(getApplicationContext(), "There was a connectivity error", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Temporary account created", Toast.LENGTH_SHORT).show();
                        }
                            user_un = InputUsername.getText().toString();
                            user_pw = InputPassword.getText().toString();
                            newpage(MainActivity.class);

                        }
                    else if (notifystate) {
                        Toast.makeText(getApplicationContext(), "Could not create account", Toast.LENGTH_LONG).show();
                    }
                    Log.i("Error:", error.toString());



                }
            });
            mRequestQueue.add(request);
        } catch (NumberFormatException e) {
            if (notifystate)
                Toast.makeText(getApplicationContext(), "Please Insert an ID to create a user", Toast.LENGTH_LONG).show();
            Log.i("Error:", e.toString());
        }
    }
}