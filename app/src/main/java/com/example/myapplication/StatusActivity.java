package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StatusActivity extends AppCompatActivity {


    EditText age,gender,temperature,heart_rate;
    Button predict;
    TextView result;
    String url = "https://cardio-ml-python.herokuapp.com/predict";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        temperature = findViewById(R.id.temperature);
        heart_rate = findViewById(R.id.heart_rate);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String data = jsonObject.getString("Result");
                                    if (data.equals("1")){
                                        result.setText("Heart Attack Occurs");
                                        Intent intent = new Intent(getApplicationContext(), AlertActivity.class);
                                        startActivity(intent);
                                    }else {
                                        result.setText("Heart Attack Does Not Occurs");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(StatusActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String ,String> params = new HashMap<String ,String >();
                        params.put("age",age.getText().toString());
                        params.put("gender",gender.getText().toString());
                        params.put("temperature",temperature.getText().toString());
                        params.put("heart_rate",heart_rate.getText().toString());

                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(StatusActivity.this);
                queue.add(stringRequest);

            }
        });
    }
}