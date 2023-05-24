package com.example.foodcourt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class Change_password extends AppCompatActivity {
    com.google.android.material.textfield.TextInputEditText etconfirmPassword, etPassword;
    com.google.android.material.button.MaterialButton button4;
    TextView alert_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        etconfirmPassword = findViewById(R.id.etconfirmPassword);
        etPassword = findViewById(R.id.etPassword);
        button4 = findViewById(R.id.button4);
        alert_msg = findViewById(R.id.alert_msg);


        Intent i = getIntent();
        String email = i.getStringExtra("email");
        Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etconfirmPassword.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty())
                    Toast.makeText(Change_password.this,"Fill all the areas",Toast.LENGTH_SHORT).show();
                else if(!(etconfirmPassword.getText().toString().equals(etPassword.getText().toString()))) {
                    alert_msg.setText("Password not matched");

                }else {

                    pass_update(email, etconfirmPassword.getText().toString());



                }
            }
        });
    }

    public void pass_update(String user_email, String password) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/update_password.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                //profile.setImageResource(0);

                try {


                    JSONObject jsonObject = new JSONObject(response);




                    if (jsonObject.getString("status").equals("true")) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Change_password.this);
                        alertDialog.setIcon(R.drawable.iconfood);
                        alertDialog.setTitle("Great password changed succesfully");
                        alertDialog.setMessage("You can Login now");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(Change_password.this, Trial_login.class);
                                startActivity(intent);

                            }
                        });
                        alertDialog.create().show();

                    } else if(jsonObject.getString("status").equals("false")){
                        // Toast.makeText(getApplicationContext().getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        alert_msg.setText(jsonObject.getString("message"));

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
                map.put("password", password);

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        queue.add(stringRequest);
    }
}