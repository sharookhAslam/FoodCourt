package com.example.foodcourt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Items_page extends AppCompatActivity {
   TextView textView;
    private ListView listView;
ArrayList<ItemModel> itemModelArrayList;

    private ListitmeAdapter listAdapter;
   private ListitmeAdapter counter;
    String b;
    String c;
    Toolbar toolbar;
   ImageView btn_ca;

    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.item_page);
        textView = findViewById(R.id.food_id);
        btn_ca = findViewById(R.id.car_bt);
   toolbar = findViewById(R.id.fc_name);



        Intent i = getIntent();

        //String a = i.getStringExtra("food_id");
        User user = new User(getApplicationContext());
        String a = user.getF_id();

        c = i.getStringExtra("mall_id");
        listView = findViewById(R.id.lv_item);

       fetch_items(a);

  btn_ca.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

          Intent i = new Intent(Items_page.this, cart.class);
          startActivity(i);
      }
  });

    }


    public void fetch_items(String stall_id) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_items.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Respnse",response);
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(succes.equals("1"))
                            {

                                itemModelArrayList = new ArrayList<>();
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("item_id");
                                    String image = object.getString("picture");
                                    String title = object.getString("title");
                                    String price = object.getString("price");
                                    String description = object.getString("description");


                                    String imgUrl = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fcimages/items/"+image;
                                    ItemModel playerModel = new ItemModel();
                                    User user2 = new User(getApplicationContext());
                                    String f_id2 = user2.getF_id();
                                    playerModel.setTitle(title);
                                    playerModel.setPrice(price);
                                    playerModel.setPicURL(imgUrl);
                                    playerModel.setDes(description);
                                    playerModel.setFood_id(f_id2);
                                    playerModel.setMall_id(c);

                                    itemModelArrayList.add(playerModel);




                                                            /*
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                                        @Override
                                        public void onItemClick(AdapterView<?>
                                        parent, View view, int position, long id) {
                                            Intent intent = new Intent(getApplicationContext(), order_page.class);
                                            intent.putExtra("item_id", playerModel.getDes());
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });
                                    */
                                    setupListview();

                                }

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Internet connection Interupt", Toast.LENGTH_LONG).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("stall_id", stall_id);
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void setupListview(){
        //will remove progress dialog
        listAdapter = new ListitmeAdapter(this, itemModelArrayList);
        listView.setAdapter(listAdapter);



    }
}
