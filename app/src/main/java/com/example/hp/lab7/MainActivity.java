package com.example.hp.lab7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
   private EditText et;
   private Button bt;
   private Lab7Connect lab7;
   ListView ls;
   private List<String> item;
   private List<String> list;
   private ArrayAdapter<String> adt;
   String id,comment = "";
   private final String url = "http://10.199.3.64/sqlinsert/getData.php";
   TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Up();
            }
        });
        update();
         //text = findViewById(R.id.textView);
         //ls = findViewById(R.id.listviewque);


    }
    public void bind(){
        list = new ArrayList<String>();
        et = findViewById(R.id.editText);
        bt = findViewById(R.id.button);
    }
    public void update(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray data = new JSONArray(response);
                    for(int i = 0;i<data.length();i++){
                        JSONObject ob = data.getJSONObject(i);
                        id = ob.getString("ID");
                        comment = ob.getString("comment");
                        list.add(comment);

                    }
                    Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
                    ls = findViewById(R.id.listviewque);
                    adt = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);
                    ls.setAdapter(adt);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                        Log.d("TAG999",error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("access","true");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
public void Up(){
        bind();
    StringRequest stringRequest = new StringRequest(Request.Method.POST,
            url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
            update();
        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                    Log.d("TAG999",error.toString());
                }
            }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params = new HashMap<>();
            params.put("access","false");
            params.put("comment",et.getText().toString());
            return params;
        }
    };
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
}
}


