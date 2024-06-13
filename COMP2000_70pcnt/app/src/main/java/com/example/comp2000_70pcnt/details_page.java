package com.example.comp2000_70pcnt;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class details_page extends AppCompatActivity {

    EditText detail_forename, detail_surname, ID_value;
    Boolean switchstate, admin;
    Integer reqoff, reqbool;
    String user_fn, user_sn;

    private Button ViewButton, CreateButton, DeleteButton, UpdateButton;

    private RequestQueue mRequestQueue;
    private String url = "http://web.socem.plymouth.ac.uk/COMP2000/api/employees";
    //private String urltest = "http://www.mocky.io/v2/597c41390f0000d002f4dbd1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            switchstate = bundle.getBoolean("switchstate");
            admin = bundle.getBoolean("admin");
            reqoff = bundle.getInt("reqoff");
            user_fn = bundle.getString("user_fn");
            user_sn = bundle.getString("user_sn");
            reqbool = bundle.getInt("reqbool");
        }

        detail_forename = findViewById(R.id.detail_forename);
        detail_surname = findViewById(R.id.detail_surname);
        Button homebutton = findViewById(R.id.homepageicon);
        Button holidaybutton = findViewById(R.id.holidaypageicon);
        ImageButton notificationbutton = findViewById(R.id.notificationpageicon);
        CreateButton = (Button) findViewById(R.id.CreateUser);
        ViewButton = (Button) findViewById(R.id.ViewUser);
        UpdateButton = (Button) findViewById(R.id.UpdateUser);
        DeleteButton = (Button) findViewById(R.id.DeleteUser);

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpage(Home_page.class);
            }
        });
        notificationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpage(notification_page.class);
            }
        });
        holidaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (admin) newpage(Calendar_page.class); //change this to received request page
                else newpage(Calendar_page_e.class);
            }
        });

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POST();
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
        intent.putExtra("switchstate", switchstate);
        intent.putExtra("admin", admin);
        intent.putExtra("reqoff", reqoff);
        intent.putExtra("user_fn", user_fn);
        intent.putExtra("user_sn", user_sn);
        intent.putExtra("reqbool", reqbool);
        startActivity(intent);
    }

    private void POST() {
        ID_value = findViewById(R.id.detail_id);
        detail_forename = findViewById(R.id.detail_forename);
        detail_surname = findViewById(R.id.detail_surname);

        try {

            int info_id = parseInt(ID_value.getText().toString());
            String info_fn = detail_forename.getText().toString();
            String info_sn = detail_surname.getText().toString();

            mRequestQueue = Volley.newRequestQueue(details_page.this);
            JSONObject datapayload = new JSONObject(); //creates payload
            try { //fills payload
                datapayload.put("id", info_id);
                datapayload.put("forename", info_fn);
                datapayload.put("surname", info_sn);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //submits payload
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, datapayload, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (switchstate)
                        Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error:", error.toString());
                    if (switchstate)
                        Toast.makeText(getApplicationContext(), "This user ID already exists", Toast.LENGTH_LONG).show();

                }
            });
            mRequestQueue.add(request);
        } catch (NumberFormatException e) {
            if (switchstate)
                Toast.makeText(getApplicationContext(), "Please Insert an ID to create a user", Toast.LENGTH_LONG).show();
            Log.i("Error:", e.toString());
        }
    }

    private void GET() {
        mRequestQueue = Volley.newRequestQueue(details_page.this);

        ID_value = findViewById(R.id.detail_id);
        String url_value = ID_value.getText().toString();
        String url = "http://web.socem.plymouth.ac.uk/COMP2000/api/employees/";
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + url_value, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    detail_forename = findViewById(R.id.detail_forename);
                    detail_surname = findViewById(R.id.detail_surname);
                    String info_forename = "";
                    String info_surname = "";
                    try {
                        info_forename = response.getString("forename");
                        info_surname = response.getString("surname");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    detail_forename.setText(info_forename);
                    detail_surname.setText(info_surname);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (switchstate)
                        Toast.makeText(getApplicationContext(), "There is no user with this ID", Toast.LENGTH_LONG).show();
                    Log.i("Error:", error.toString());
                }
            });
            mRequestQueue.add(request);
        } catch (NumberFormatException e) {
            if (switchstate)
                Toast.makeText(getApplicationContext(), "Please Insert an ID to view a user", Toast.LENGTH_LONG).show();
            Log.i("Error:", e.toString());
        }
    }

    private void PUT() {
        try {
            ID_value = findViewById(R.id.detail_id);
        detail_forename = findViewById(R.id.detail_forename);
        detail_surname = findViewById(R.id.detail_surname);
        int info_id = parseInt(ID_value.getText().toString());
        String info_fn = detail_forename.getText().toString();
        String info_sn = detail_surname.getText().toString();
        String url_value = ID_value.getText().toString();
        String url = "http://web.socem.plymouth.ac.uk/COMP2000/api/employees/";

            mRequestQueue = Volley.newRequestQueue(details_page.this);
            JSONObject datapayload = new JSONObject(); //creates payload
            try { //fills payload
                datapayload.put("id", info_id);
                datapayload.put("forename", info_fn);
                datapayload.put("surname", info_sn);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //submits payload
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url + url_value, datapayload, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (switchstate)
                        Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (switchstate)
                        Toast.makeText(getApplicationContext(), "There is no user with this ID", Toast.LENGTH_LONG).show();
                    Log.i("Error:", error.toString());
                }
            });
            mRequestQueue.add(request);
        } catch (NumberFormatException e) {
            if (switchstate)
                Toast.makeText(getApplicationContext(), "Please Insert an ID to update a user", Toast.LENGTH_LONG).show();
            Log.i("Error:", e.toString());
        }

    }

    private void DELETE() {
        try {

            ID_value = findViewById(R.id.detail_id);
            String url_value = ID_value.getText().toString();
            String url = "http://web.socem.plymouth.ac.uk/COMP2000/api/employees/";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url + url_value, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (switchstate)
                        Toast.makeText(getApplicationContext(), "User DELETED", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (switchstate)
                        Toast.makeText(getApplicationContext(), "There is no user with this ID", Toast.LENGTH_LONG).show();
                    Log.i("Error:", error.toString());
                }
            });

            mRequestQueue.add(request);
        } catch (NumberFormatException e) {
            if (switchstate)
                Toast.makeText(getApplicationContext(), "Please Insert an ID to delete a user", Toast.LENGTH_LONG).show();
            Log.i("Error:", e.toString());
        }
        catch (NullPointerException e){
            if (switchstate)
                Toast.makeText(getApplicationContext(), "Please Insert an ID to delete a user", Toast.LENGTH_LONG).show();
            Log.i("Error:", e.toString());
        }
    }
}
