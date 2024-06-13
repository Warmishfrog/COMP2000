package com.example.comp2000referral;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class details_page extends AppCompatActivity {

    EditText detail_password, detail_username, ID_value;
    Boolean notifystate;
    Integer notifyInt;
    String user_un, user_pw;

    private Button ViewButton, CreateButton, DeleteButton, UpdateButton;

    private RequestQueue mRequestQueue;
    private String url = "http://web.socem.plymouth.ac.uk/COMP2000/api/referral/students"; //IMPORTANT API LINK


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            notifystate = bundle.getBoolean("notifystate");
            user_un= bundle.getString("user_un");
            user_pw = bundle.getString("user_pw");
            notifyInt = bundle.getInt("notifyInt");
        }

        detail_password = findViewById(R.id.detail_password);
        detail_username = findViewById(R.id.detail_username);
        Button homebutton = findViewById(R.id.homepageicon);
        Button prefbutton = findViewById(R.id.prefpageicon);
        ViewButton = (Button) findViewById(R.id.ViewUser);
        UpdateButton = (Button) findViewById(R.id.UpdateUser);
        DeleteButton = (Button) findViewById(R.id.DeleteUser);

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpage(Home_page.class);
            }
        });
        prefbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { newpage(pref_page.class); //change this to received request page
            }
        });

        ViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GET();
            }
        });
        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PUT();
            }
        });
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DELETE();
            }
        });
    }

    public void newpage(Class whatpage) {
        Intent intent = new Intent(getApplicationContext(), whatpage);
        intent.putExtra("notifystate", notifystate);
        intent.putExtra("user_un", user_un);
        intent.putExtra("user_pw", user_pw);
        intent.putExtra("notifyInt", notifyInt);
        startActivity(intent);
    }

    private void GET() {
        mRequestQueue = Volley.newRequestQueue(details_page.this);

        ID_value = findViewById(R.id.detail_id);
        String url_value = ID_value.getText().toString();
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + url_value, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    detail_password = findViewById(R.id.detail_password);
                    detail_username = findViewById(R.id.detail_username);
                    String info_username = "";
                    String info_password = "";
                    try {
                        info_username = response.getString("username");
                        info_password = response.getString("password");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    detail_password.setText(info_username);
                    detail_username.setText(info_password);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 307) { //NO API
                        if (notifystate)
                        {
                            Toast.makeText(getApplicationContext(), "There was a connectivity error", Toast.LENGTH_SHORT).show();
                            detail_password.setText(user_pw);
                            detail_username.setText(user_un);
                        }
                    }
                    else if (notifystate)
                        Toast.makeText(getApplicationContext(), "There is no user with this ID", Toast.LENGTH_LONG).show();
                    Log.i("Error:", error.toString());
                }
            });
            mRequestQueue.add(request);
        } catch (NumberFormatException e) {
            if (notifystate)
                Toast.makeText(getApplicationContext(), "Please Insert an ID to view a user", Toast.LENGTH_LONG).show();
            Log.i("Error:", e.toString());
        }
    }

    private void PUT() {
        try {
            ID_value = findViewById(R.id.detail_id);
        detail_password = findViewById(R.id.detail_password);
        detail_username = findViewById(R.id.detail_username);
        int info_id = parseInt(ID_value.getText().toString());
        String info_fn = detail_password.getText().toString();
        String info_sn = detail_username.getText().toString();
        String url_value = ID_value.getText().toString();

            mRequestQueue = Volley.newRequestQueue(details_page.this);
            JSONObject datapayload = new JSONObject(); //creates payload
            try { //fills payload
                datapayload.put("id", info_id);
                datapayload.put("username", info_fn);
                datapayload.put("password", info_sn);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //submits payload
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url + url_value, datapayload, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (notifystate)
                        Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_SHORT).show();
                    notifyInt = 1;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 307) { //NO API
                        if (notifystate)
                            Toast.makeText(getApplicationContext(), "There was a connectivity error", Toast.LENGTH_LONG).show();
                        user_un = detail_username.getText().toString();
                        user_pw = detail_password.getText().toString();
                        notifyInt = 1; //for testing purposes
                    }
                    else if (notifystate)
                        Toast.makeText(getApplicationContext(), "There is no user with this ID", Toast.LENGTH_LONG).show();
                    Log.i("Error:", error.toString());
                }
            });
            mRequestQueue.add(request);
        } catch (NumberFormatException e) {
            if (notifystate)
                Toast.makeText(getApplicationContext(), "Please Insert an ID to update a user", Toast.LENGTH_LONG).show();
            Log.i("Error:", e.toString());
        }

    }

    private void DELETE() {
        try {

            ID_value = findViewById(R.id.detail_id);
            String url_value = ID_value.getText().toString();

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url + url_value, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (notifystate)
                        Toast.makeText(getApplicationContext(), "User DELETED", Toast.LENGTH_LONG).show();
                    user_un = null;
                    user_pw = null;

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 307) { //NO API
                        if (notifystate)
                            Toast.makeText(getApplicationContext(), "There was a connectivity error", Toast.LENGTH_LONG).show();
                        user_un = null;
                        user_pw = null;
                        detail_password.setText(user_pw);
                        detail_username.setText(user_un);
                        Log.d("debug time", user_un +" " + user_pw);

                    }
                    else if (notifystate)
                        Toast.makeText(getApplicationContext(), "There is no user with this ID", Toast.LENGTH_LONG).show();
                    Log.i("Error:", error.toString());
                }
            });

            mRequestQueue.add(request);
        } catch (NumberFormatException e) {
            if (notifystate)
                Toast.makeText(getApplicationContext(), "Please Insert an ID to delete a user", Toast.LENGTH_LONG).show();
            Log.i("Error:", e.toString());
        }
        catch (NullPointerException e){
            if (notifystate)
                Toast.makeText(getApplicationContext(), "Please Insert an ID to delete a user", Toast.LENGTH_LONG).show();
            Log.i("Error:", e.toString());
        }
    }
}
