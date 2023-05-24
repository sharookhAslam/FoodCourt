package com.example.foodcourt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button  button;
    EditText sname;
    EditText semail;
    EditText spassword;
    TextView login;
    FrameLayout progressLO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        button = findViewById(R.id.signup);
        sname = findViewById(R.id.editText);
        semail = findViewById(R.id.editText2);
        spassword = findViewById(R.id.editText3);
         login = findViewById(R.id.login);
         progressLO = findViewById(R.id.prgrssLO);

     login.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent i = new Intent(MainActivity.this, Trial_login.class);
             startActivity(i);
         }
     });

    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

         //   Intent i = new Intent(MainActivity.this, Signing.class);
           // startActivity(i);
            if (sname.getText().toString().isEmpty())
                Toast.makeText(MainActivity.this, "Enter name", Toast.LENGTH_LONG).show();
            else if (semail.getText().toString().isEmpty())
                Toast.makeText(MainActivity.this, "Enter email", Toast.LENGTH_LONG).show();
            else if (spassword.getText().toString().isEmpty())
                Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_LONG).show();
            else
                register_user(sname.getText().toString(), semail.getText().toString(), spassword.getText().toString());

        }
    });

    }


    public void register_user(final String name, String email, final String password){

        progressLO.setVisibility(View.VISIBLE);

    String URL = "https://websitedesignbyloftysoft.000webhostapp.com/signup.php";

    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

                sname.setText("");
                semail.setText("");
                spassword.setText("");
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                progressLO.setVisibility(View.GONE);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.getString("status").equals("true")){
                      Intent i = new Intent(MainActivity.this, Email_varify.class);
                      i.putExtra("email", email);
                      startActivity(i);
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
                    sname.setText("");
                    sname.setText("");
                    sname.setText("");
                    Toast.makeText(getApplicationContext(), "INTERNET CONNECTION INTERRUPT", Toast.LENGTH_LONG).show();

                }
            }



    ){
        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> map =  new HashMap<String, String>();
            map.put("name", name);
            map.put("email", email);
            map.put("password", password);

        return map;
        }
    };

    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
    queue.add(stringRequest);
}

   

}



/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
 */



