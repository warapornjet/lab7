package com.example.hp.lab7;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lab7Connect {
    private final Activity main;
    private List<String> list;
    private String URL = "10.199.3.64";

    public Lab7Connect() { this.main = null;}
    public Lab7Connect(Activity m){
       main = m;
       list = new ArrayList<String>();
    }

    public List<String> getData() {
        String url = URL + "/sqlinsert/getData.php";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response == null){
                    Toast.makeText(main.getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
                }else{
                    Showjson(response);
                    Toast.makeText(main.getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG123",error.toString());
                    }
                }
                );
        RequestQueue requestQueue = Volley.newRequestQueue(main.getApplicationContext());
        requestQueue.add(stringRequest);
        return list;
    }
    public void Showjson(String response){
        String comment = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            for(int i = 0;i<result.length();i++){
                JSONObject collect = result.getJSONObject(i);
                comment = collect.getString("comment");
                list.add(comment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
