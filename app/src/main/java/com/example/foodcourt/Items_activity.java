package com.example.foodcourt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
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

public class Items_activity extends AppCompatActivity {
    ImageView item_pic;
    TextView des, item_title, item_price, additional, qty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        item_pic = findViewById(R.id.item_img);
        item_title = findViewById(R.id.item_title);
        item_price = findViewById(R.id.item_price);
        des = findViewById(R.id.item_descritpion);
        qty = findViewById(R.id.qty);

        qty.setText(getIntent().getStringExtra("mall"));
    }



    public void fetch_items(String stall_id) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_items.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(succes.equals("1"))
                            {
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("item_id");
                                    String image = object.getString("picture");
                                    String title = object.getString("title");
                                    String price = object.getString("price");
                                    String imgUrl = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fcimages/"+image;
                                    //modelImage = new ALL_Malls(id, imgUrl, name);
                                    //imageList.add(modelImage);
                                     //                            myadapter.notifyDataSetChanged();

                                 item_title.setText(title);
                                 item_price.setText(price);
                                 des.setText(id);
                                }

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
               // map.put("mall_id", mall_id);
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);

    }
}