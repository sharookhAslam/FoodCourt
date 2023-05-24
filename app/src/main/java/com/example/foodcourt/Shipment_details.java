package com.example.foodcourt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import java.util.UUID;

public class Shipment_details extends AppCompatActivity {
    com.google.android.material.textfield.TextInputEditText name, phone;
    com.google.android.material.button.MaterialButton btn_c;
    String user_id, t_amount, tp, f_id;
 String order_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_details);
        btn_c = findViewById(R.id.c_order);
        Intent it = getIntent();
        tp =  it.getStringExtra("tp");
     name = findViewById(R.id.user_name);
     phone = findViewById(R.id.phone);
        User user = new User(Shipment_details.this);
        user_id = user.getUser_id();
        f_id = user.getF_id();
   order_id = UUID.randomUUID().toString();
     btn_c.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
 // Toast.makeText(getApplicationContext(), ""+user_id, Toast.LENGTH_LONG).show();
             if (name.getText().toString().isEmpty())
                 Toast.makeText(Shipment_details.this,"Enter your name",Toast.LENGTH_SHORT).show();
             else if (phone.getText().toString().isEmpty())
                 Toast.makeText(Shipment_details.this,"Enter your phone number",Toast.LENGTH_SHORT).show();
             else
                 PlaceOrder();

         }
     });





    }

    private void PlaceOrder() {
        PlaceOrder();
        add_order(user_id);
        add_info();
        email_sent();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Shipment_details.this);
        alertDialog.setIcon(R.drawable.iconfood);
        alertDialog.setTitle("Thank you for your order");
        alertDialog.setMessage("You have to wait untill you order will completed");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete_item();
                Intent intent = new Intent(Shipment_details.this, User_Profile.class);
                startActivity(intent);

            }
        });
        alertDialog.create().show();
    }

    private void add_order(String a) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/order_details.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {

                    } else if(jsonObject.getString("status").equals("false")){
                        Toast.makeText(getApplicationContext(),
                                jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Internet Connection Interupt".trim(),

                                Toast.LENGTH_LONG).show();

                    }
                }



        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_id", a);
                map.put("order_id", order_id);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    private void add_info() {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/save_payment_info.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               Log.e("Response_2",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {

                    } else if(jsonObject.getString("status").equals("false")){
                        Toast.makeText(getApplicationContext(),
                                jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Internet Connection Interupt".trim(),
                                Toast.LENGTH_LONG).show();

                    }
                }


        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_id", user_id);
                map.put("name", name.getText().toString());
                map.put("phone", phone.getText().toString());
                map.put("payment", "Cash");
                map.put("t_amount", tp);
                map.put("order_id", order_id);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
    public void delete_item() {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/delete_cart.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            //    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                //  profile.setImageResource(0);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {


                    } else if(jsonObject.getString("status").equals("false")){
                      //  Toast.makeText(getApplicationContext(),
                               // jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Internet Connection Interupt".trim(),
                                Toast.LENGTH_LONG).show();

                    }
                }


        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("user_id", user_id);

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }


    private void email_sent() {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/order_email.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response",response);
                //  profile.setImageResource(0);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                    } else if(jsonObject.getString("status").equals("false")){
                        Toast.makeText(getApplicationContext(),
                                jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Internet Connection Interupt".trim(),
                                Toast.LENGTH_LONG).show();

                    }
                }


        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_id", f_id);

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}