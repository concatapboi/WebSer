package com.example.hien.webser;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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

public class NhanVien extends AppCompatActivity {
    final String SERVER ="http://192.168.56.1/";
    NVAdapter adap;
    ListView list;
    EditText txtID, txtTEN, txtHSL;
    Button btnThemGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnThemGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtID.getText().toString().isEmpty()){
                    checkID(txtID.getText().toString());
                } else Toast.makeText(NhanVien.this,"ID empty!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkID(final String id) {
        RequestQueue reqQueue = Volley.newRequestQueue(NhanVien.this);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArr = new JSONArray(response);
                    int len = jsonArr.length();
                    for(int i=0;i<len;i++){
                        JSONObject obj = jsonArr.getJSONObject(i);
                        String ma = obj.getString("MA");
                        if(ma.equals(id)){
                            Toast.makeText(NhanVien.this,"Has ID!",Toast.LENGTH_LONG).show();
                            break;
                        }else {
                            if(txtTEN.getText().toString().isEmpty() || txtHSL.getText().toString().isEmpty()){Toast.makeText(NhanVien.this,"Name empty!",Toast.LENGTH_LONG).show();break;} else{
                                com.example.hien.webser.model.NhanVien nv = new com.example.hien.webser.model.NhanVien(txtID.getText().toString(),txtTEN.getText().toString(),Double.parseDouble(txtHSL.getText().toString()));
                                adap.add(nv);
                            }
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorLis = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    private void addControls() {
        btnThemGet = findViewById(R.id.themGET);
        txtID = findViewById(R.id.id);
        txtTEN = findViewById(R.id.ten);
        txtHSL = findViewById(R.id.hsluong);
        list = findViewById(R.id.list);
        adap = new NVAdapter(this, new ArrayList<com.example.hien.webser.model.NhanVien>());
        list.setAdapter(adap);
        xulyGetDSNV();

    }
    private void xulyGetDSNV() {
        RequestQueue reqQueue = Volley.newRequestQueue(NhanVien.this);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    adap.clear();
                    JSONArray jsonArr = new JSONArray(response);
                    int len = jsonArr.length();
                    for(int i=0;i<len;i++){
                        JSONObject obj = jsonArr.getJSONObject(i);
                        String ma = obj.getString("MA");
                        String ten = obj.getString("TEN");
                        double hsl = obj.getDouble("HSLUONG");
                        adap.add(new com.example.hien.webser.model.NhanVien(ma,ten,hsl));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorLis = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
}
