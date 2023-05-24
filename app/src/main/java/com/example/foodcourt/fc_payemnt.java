package com.example.foodcourt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fc_payemnt extends AppCompatActivity {

    String order_id;
    private ListView listView3;
    ArrayList<fcorderModel> itemModelArrayList3;
    TextView textView;

    private paymentAdapter listAdapter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fc_payemnt);
        listView3 = findViewById(R.id.lv_orders_all);

        textView = findViewById(R.id.user);
        User user = new User(getApplicationContext());
        fetch_payments(user.getOrder_id());



    }
    public void fetch_payments(String user_id) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/payment_info_order_id.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(succes.equals("1"))
                            {

                                itemModelArrayList3 = new ArrayList<>();
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);


                                    String name= object.getString("name");
                                    String phone= object.getString("phone");

                                    String payemnt_method = object.getString("payemnt_method");
                                    fcorderModel playerModel = new fcorderModel();

                                    playerModel.setU_name(name);
                                    playerModel.setPhone(phone);
                                    playerModel.setPayment_method(payemnt_method);




                                    itemModelArrayList3.add(playerModel);




                                }
                                setupListview2();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("order_id", user_id);
                return map;
            }
        };



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void setupListview2() {
        //will remove progress dialog
        listAdapter4 = new paymentAdapter(this, itemModelArrayList3);
        listView3.setAdapter(listAdapter4);


    }  }