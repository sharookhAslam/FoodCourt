package com.example.foodcourt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class cart extends AppCompatActivity {
    private ListView listView2;
    ArrayList<cartModel> cartModelArrayList;
  TextView textView, textView2, textView3;
    private cartitemAdapter listAdapter;
    int sum = 0;
    Button btn_check;
    String item_id, user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        textView = findViewById(R.id.no_item);
        listView2 = findViewById(R.id.lv_cart);
        btn_check = findViewById(R.id.checkout);
        textView3 = findViewById(R.id.tb_num);
          textView2  =findViewById(R.id.t_p);

  User user = new User(cart.this);
  user_id = user.getUser_id();


   fetch_cart(user_id);


   btn_check.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {


           Intent itn = new Intent(cart.this, payment_option.class);

          itn.putExtra("tp", textView2.getText().toString());
           startActivity(itn);
       }
   });


    }

    private int getTotal() {
        int sum = 0;
        for (int i = 0;i<cartModelArrayList.size();i++){
            sum = sum + Integer.parseInt(cartModelArrayList.get(i).price.substring(1,cartModelArrayList.get(i).price.length()));
        }
        return sum;
    }

    public void fetch_cart(String user_id) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_cart.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(succes.equals("1"))
                            {

                                cartModelArrayList = new ArrayList<>();
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    item_id = object.getString("item_id");
                                    String image = object.getString("item_pic");
                                    String title = object.getString("title");
                                    String price = object.getString("price");


                                    String qty = object.getString("qty");


                                    cartModel playerModel = new cartModel();

                                    playerModel.setTitle(title);
                                    playerModel.setPrice(price);
                                    playerModel.setQty(qty);
                                    playerModel.setPicURL(image);
                                    playerModel.setItem_id(item_id);

                                    cartModelArrayList.add(playerModel);



                                    String message = jsonObject.getString("message");


                                    textView2.setText(message);

                                    setupListview();

                                }

                            }else if(succes.equals("No items added yet")){

                               textView.setVisibility(View.VISIBLE);

                                textView.setText("No any item added yet");
                                btn_check.setVisibility(View.INVISIBLE);
                                 textView2.setVisibility(View.INVISIBLE);
                                 textView3.setVisibility(View.INVISIBLE);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "internet Connection Interupt", Toast.LENGTH_LONG).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("user_id", user_id);
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);

    }



    private void setupListview(){
        //will remove progress dialog
        listAdapter = new cartitemAdapter(this, cartModelArrayList,user_id);
        listView2.setAdapter(listAdapter);
        textView2.setText("£"+getTotal());

    }



    public void delete_item() {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/delete_temp_cart.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {


                    } else if(jsonObject.getString("status").equals("false")){
                      //  Toast.makeText(getApplicationContext(),
                        //        jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString().trim(),
                                Toast.LENGTH_LONG).show();

                    }
                }


        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("item_id", item_id);
                map.put("user_id", user_id);

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }


}