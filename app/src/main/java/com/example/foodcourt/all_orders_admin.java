package com.example.foodcourt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class all_orders_admin extends AppCompatActivity {
    String order_id;
    private ListView listView3;
    ArrayList<fcorderModel> itemModelArrayList3;

    private Admin_orderAdapter listAdapter3;
    TextView textView;
    private paymentAdapter listAdapter4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders_admin);

        listView3 = findViewById(R.id.lv_orders_all);
        textView = findViewById(R.id.u);
        fetch_items();

    }


    public void fetch_items() {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/admin_all_order.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(succes.equals("1"))
                            {

                                itemModelArrayList3 = new ArrayList<>();
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);


                                    String title = object.getString("title");
                                    String price = object.getString("price");
                                    String qty = object.getString("qty");
                                    String time= object.getString("time");
                                    String user_id= object.getString("user_id");
                                   order_id= object.getString("order_id");
                                    fcorderModel playerModel = new fcorderModel();

                                    playerModel.setTitle(title);
                                    playerModel.setPrice(price);
                                    playerModel.setQty(qty);
                                    playerModel.setTime(time);
                                    playerModel.setPayment_method(user_id);

                                    textView.setText(user_id);

                                    itemModelArrayList3.add(playerModel);



                                }
                                setupListview();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();            }
        });



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);

    }



    private void setupListview(){
        //will remove progress dialog
        listAdapter3 = new Admin_orderAdapter(this, itemModelArrayList3);
        listView3.setAdapter(listAdapter3);



    }

}