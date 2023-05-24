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

public class Email_varify extends AppCompatActivity {


    TextView textView7;
    EditText ed1, ed2, ed3, ed4, ed5, ed6;
  Button button4;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_varify);

        ed1 = findViewById(R.id.editTextTextPersonName12);
        ed2 = findViewById(R.id.editTextTextPersonName13);
        ed3 = findViewById(R.id.editTextTextPersonName14);
        ed4 = findViewById(R.id.editTextTextPersonName15);
        ed5 = findViewById(R.id.editTextTextPersonName16);
        ed6 = findViewById(R.id.editTextTextPersonName17);
       button4 = findViewById(R.id.button4);
        Intent i = getIntent();
        email = i.getStringExtra("email");
        numbertomove();
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
                                                   Intent itn = new Intent(Email_varify.this, Trial_login.class);
                                                   startActivity(itn);
                                                   profile_update(email);

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
                           map.put("email", email);
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



    private void numbertomove() {

        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
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

                if(!s.toString().trim().isEmpty()){
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

                if(!s.toString().trim().isEmpty()){
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

                if(!s.toString().trim().isEmpty()){
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

                if(!s.toString().trim().isEmpty()){
                    ed6.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void profile_update(String user_email) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/active_user.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                //profile.setImageResource(0);

                try {


                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {



                    } else if(jsonObject.getString("status").equals("false")){
                       // Toast.makeText(getApplicationContext().getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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
}