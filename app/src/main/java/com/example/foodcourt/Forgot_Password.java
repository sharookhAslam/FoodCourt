package com.example.foodcourt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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

import java.util.HashMap;
import java.util.Map;

public class Forgot_Password extends AppCompatActivity {
    EditText ed1, ed2, ed3, ed4, ed5, ed6, textView7;
    Button button4, button7;
    TextView textView16;
    FrameLayout progressLO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ed1 = findViewById(R.id.editTextTextPersonName12);
        ed2 = findViewById(R.id.editTextTextPersonName13);
        ed3 = findViewById(R.id.editTextTextPersonName14);
        ed4 = findViewById(R.id.editTextTextPersonName15);
        ed5 = findViewById(R.id.editTextTextPersonName16);
        ed6 = findViewById(R.id.editTextTextPersonName17);
        textView7 = findViewById(R.id.textView7);
        button4 = findViewById(R.id.button4);
        button7 = findViewById(R.id.button7);
        textView16 = findViewById(R.id.textView16);
        progressLO = findViewById(R.id.prgrssLO);

        numbertomove();
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textView7.getText().toString().trim().isEmpty()){
                    email_sent(textView7.getText().toString());

                }else {

                    textView16.setText("Please enter email");

                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ed1.getText().toString().trim().isEmpty() &&
                        !ed2.getText().toString().trim().isEmpty() &&
                        !ed3.getText().toString().trim().isEmpty() &&
                        !ed4.getText().toString().trim().isEmpty() &&
                        !ed5.getText().toString().trim().isEmpty()
                        && !ed6.getText().toString().trim().isEmpty()) {


                    String enterotp = ed1.getText().toString() +
                            ed2.getText().toString() +
                            ed3.getText().toString() +
                            ed4.getText().toString() +
                            ed5.getText().toString() +
                            ed6.getText().toString();

                    String URL = "https://websitedesignbyloftysoft.000webhostapp.com/fetch_otp.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                    try {


                                        JSONObject jsonObject = new JSONObject(response);
                                        String succes = jsonObject.getString("success");
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                                        if(succes.equals("1"))
                                        {


                                            for(int i=0; i<jsonArray.length(); i++) {
                                                JSONObject object = jsonArray.getJSONObject(i);
                                                String id = object.getString("id");


                                                String otp = object.getString("otp");

                                                if(enterotp.equals(otp)){
                                                    Intent itn = new Intent(Forgot_Password.this, Change_password.class);
                                                    itn.putExtra("email", textView7.getText().toString());
                                                    startActivity(itn);


                                                }else {
                                                    Toast.makeText(getApplicationContext(), "Please Enter Correct otp", Toast.LENGTH_LONG).show();

                                                }





                                            }

                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Internet connection interupt", Toast.LENGTH_LONG).show();            }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> map = new HashMap<String, String>();
                            map.put("email", textView7.getText().toString());
                            return map;
                        }
                    };


                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
                    requestQueue.add(stringRequest);



                }else {


                    Toast.makeText(getApplicationContext(),
                            "Please Enter All Numbers", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void email_sent(String user_email) {
        progressLO.setVisibility(View.VISIBLE);

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/forgot_pass.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                //profile.setImageResource(0);

                try {
                    progressLO.setVisibility(View.GONE);

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {



                    } else if(jsonObject.getString("status").equals("false")){
                        textView16.setText("please enter valid email");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext().getApplicationContext(), "Internet connection interupt", Toast.LENGTH_LONG).show();

                    }
                }


        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();

                map.put("user_email", user_email);

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        queue.add(stringRequest);
    }

    private void numbertomove() {

        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    ed2.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    ed3.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    ed4.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    ed5.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    ed6.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}