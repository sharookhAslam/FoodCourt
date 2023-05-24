package com.example.foodcourt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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


public class Trial_login extends AppCompatActivity {
    Button button;
    EditText email, password;
    TextView create;
    FrameLayout progressLO;
    TextView textView5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        create = findViewById(R.id.create);
        button = findViewById(R.id.signin);
        progressLO = findViewById(R.id.prgrssLO);
        textView5 = findViewById(R.id.textView5);

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Trial_login.this, Forgot_Password.class);
                startActivity(i);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Trial_login.this, MainActivity.class);
                startActivity(i);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().isEmpty())
                    Toast.makeText(Trial_login.this,"Enter email",Toast.LENGTH_SHORT).show();
                else if (password.getText().toString().isEmpty())
                    Toast.makeText(Trial_login.this,"Enter password",Toast.LENGTH_SHORT).show();
                else
                    login(email.getText().toString(), password.getText().toString());
                //Intent i = new Intent(Signing.this, User_Profile.class);
                //startActivity(i);
            }
        });
    }


    private void login(String vemail, String vpassword) {
        progressLO.setVisibility(View.VISIBLE);
        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/trial_login.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                email.setText("");
                password.setText("");
               // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                progressLO.setVisibility(View.GONE);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    //String re = jsonObject.getString("message");

                        if (jsonObject.getString("message").equals("Admin Login")) {

                            User user = new User(Trial_login.this);
                            user.setName(vemail);
                            user.setUserrole("admin");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(a);
                                String id = jsonObject1.getString("id");
                                String email1 = jsonObject1.getString("email");
                                String name1 = jsonObject1.getString("name");
                                String p_pic = jsonObject1.getString("profile_pic");
                                String imageurl = "https://websitedesignbyloftysoft.000webhostapp.com/fcimages/" + p_pic;

                                Intent i = new Intent(Trial_login.this, admin_panel.class);
                                i.putExtra("email", user.getName());
                                i.putExtra("name", name1);
                                i.putExtra("id", id);


                                i.putExtra("profile_pic", imageurl);
                                startActivity(i);


                            }
                        } else if (jsonObject.getString("message").equals("food Login")) {

                            User user = new User(Trial_login.this);
                            user.setName(vemail);
                            user.setUserrole("food stall");
                            Intent i = new Intent(Trial_login.this, Fc_profile.class);
                            i.putExtra("email", user.getName());
                            startActivity(i);

                        } else if (jsonObject.getString("message").equals("customer Login")) {


                            User user = new User(Trial_login.this);
                            user.setName(vemail);
                            user.setUserrole("customer");

                            Intent i = new Intent(Trial_login.this, User_Profile.class);
                            i.putExtra("email", user.getName());

                            startActivity(i);

                       /*
                        user.setUserrole("customer");

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(a);

                            String email1 = jsonObject1.getString("email");
                            String name1 = jsonObject1.getString("name");
                            String p_pic = jsonObject1.getString("profile_pic");
                            String imageurl = "https://websitedesignbyloftysoft.000webhostapp.com/fcimages/" + p_pic;

                            Intent i = new Intent(Trial_login.this, User_Profile.class);
                            i.putExtra("email", user.getName());
                            i.putExtra("name", name1);
                            //i.putExtra("profile_pic", imageurl);
                            startActivity(i);

                        }  */
                        } else if (jsonObject.getString("message").equals("sm Login")) {

                            User user = new User(Trial_login.this);
                            user.setName(vemail);
                            user.setUserrole("mall");
                            Intent i = new Intent(Trial_login.this, Sh_malls.class);
                            i.putExtra("email", user.getName());
                            startActivity(i);

   /*
                        user.setUserrole("mall");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(a);

                            String email1 = jsonObject1.getString("email");
                            String name1 = jsonObject1.getString("name");
                            String p_pic = jsonObject1.getString("profile_pic");
                            String imageurl = "https://websitedesignbyloftysoft.000webhostapp.com/fcimages/" + p_pic;

                            Intent i = new Intent(Trial_login.this, Sh_malls.class);
                            i.putExtra("email", user.getName());
                            i.putExtra("name", name1);
                            i.putExtra("profile_pic", imageurl);
                            startActivity(i);
                        }
*/
                        }
                        else {
                            Toast.makeText(Trial_login.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
                        }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        email.setText("");
                        password.setText("");
                        Toast.makeText(getApplicationContext(), "Internet connection interupt", Toast.LENGTH_LONG).show();

                    }
                }



        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map =  new HashMap<String, String>();

                map.put("email", vemail);
                map.put("password", vpassword);

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }



        @Override
        public void onBackPressed() {
            moveTaskToBack(true);
        }

    public void nullClick(View view) {
    }
}


