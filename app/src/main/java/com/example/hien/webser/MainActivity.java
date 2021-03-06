package com.example.hien.webser;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hien.webser.model.NhanVien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    final String SERVER ="http://192.168.56.1/";
    Button btnGetDSNV,btnThemGetNV,btnThemPostNV;
    TextView txtResponse;
    ListView list;
    ArrayAdapter adap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {
        btnGetDSNV = findViewById(R.id.btnGetDSNV);
        btnThemGetNV = findViewById(R.id.btnThemGetNV);
        btnThemPostNV = findViewById(R.id.btnThemPostNV);
        list = findViewById(R.id.list);
        txtResponse = findViewById(R.id.txtResponse);
        adap = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1);
        list.setAdapter(adap);
    }

    private void addEvents() {
        btnGetDSNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyGetDSNV();
            }
        });
        btnThemGetNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyThemGet();
            }
        });
        btnThemPostNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyThemPost();
            }
        });
    }

    private void xulyThemPost() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtResponse.setText(response);
            }
        };
        Response.ErrorListener errorLis = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtResponse.setText(error.getMessage());
            }
        };
        Uri.Builder buil = Uri.parse(SERVER + "post_themNV.php").buildUpon();
        String url = buil.build().toString();
        StringRequest req = new StringRequest(
                Request.Method.POST,
                url,
                listener,
                errorLis
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("ten", "Tran Van Teo");
                params.put("hsluong","1.2");
                return params;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add((req));
    }

    private void xulyThemGet() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtResponse.setText(response);
            }
        };
        Response.ErrorListener errorLis = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtResponse.setText(error.getMessage());
            }
        };
        Uri.Builder buil = Uri.parse(SERVER+"get_themNV.php").buildUpon();
        buil.appendQueryParameter("ten","Nguyen Van Teo");
        buil.appendQueryParameter("hsluong","4.5");
        String url = buil.build().toString();
        StringRequest req = new StringRequest(
          Request.Method.GET,
          url,
          listener,
          errorLis
        );
        req.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(req);
    }

    private void xulyGetDSNV() {
        RequestQueue reqQueue = Volley.newRequestQueue(MainActivity.this);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtResponse.setText(response);
                try{
                    adap.clear();
                    JSONArray jsonArr = new JSONArray(response);
                    int len = jsonArr.length();
                    for(int i=0;i<len;i++){
                        JSONObject obj = jsonArr.getJSONObject(i);
                        String ma = obj.getString("MA");
                        String ten = obj.getString("TEN");
                        double hsl = obj.getDouble("HSLUONG");
                        adap.add(new NhanVien(ma,ten,hsl));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorLis = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtResponse.setText(error.getMessage());
            }
        };
        Uri.Builder buil = Uri.parse(SERVER+"dsNV.php").buildUpon();
        String url = buil.build().toString();
        StringRequest req = new StringRequest(
                Request.Method.GET,
                url,
                listener,
                errorLis
        );
        req.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        reqQueue.add(req);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.other :
                Intent intent = new Intent(MainActivity.this, com.example.hien.webser.NhanVien.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
