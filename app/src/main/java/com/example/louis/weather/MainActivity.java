package com.example.louis.weather;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = "dd4948fb8ab5a938ec7bcad2c2a43ba4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue       requestQueue = Volley.newRequestQueue(this);
        String             url          = "http://api.openweathermap.org/data/2.5/forecast/?q=Bordeaux,FR&APPID=" + API_KEY;
        final TextView     display      = findViewById(R.id.display);

        display.append("Weather\n");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray list = response.getJSONArray("list");
                            for (int j = 0; j < list.length(); j += 8) {
                                JSONObject wlist = list.getJSONObject(j);
                                    //display.append("" + j);
                                    JSONArray weather = wlist.getJSONArray("weather");
                                    JSONObject weatherInfos = weather.getJSONObject(0);
                                    String description = weatherInfos.getString("description");
                                    display.append(((j == 0) ? "Today" : "in " + j / 8 + " days") +" : " + description + "\n");
                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        display.append("error");
                        Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);

    }
}
