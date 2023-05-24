package com.example.foodcourt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin_shopingmall_list extends AppCompatActivity {

  TextView textView;
  FloatingActionButton add_mall;
    private ListView listView;
    ArrayList<smModel> smModelArrayList;
    private smListAdapter listAdapter;
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.admin_shopingmall_list);
listView = findViewById(R.id.listsm);
         textView = findViewById(R.id.smname);
         add_mall = findViewById(R.id.add_mall);
        fetch_malls();
         add_mall.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent i = new Intent(Admin_shopingmall_list.this, Add_mall.class);
                 startActivity(i);

             }
         });




    }
    public void fetch_malls() {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/show_malls.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response ",response);
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(succes.equals("1"))
                            {

                                smModelArrayList = new ArrayList<>();
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String email = object.getString("email");
                                    String image = object.getString("profile_pic");

                                    String name = object.getString("name");
                                    String imgUrl = "https://websitedesignbyloftysoft.000webhostapp.com/fcimages/"+image;

                                    smModel playerModel = new smModel();

                                    playerModel.setName(name);
                                    playerModel.setCountry(email);
                                    playerModel.setImgURL(imgUrl);
                                    smModelArrayList.add(playerModel);


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
                Log.e("Error",error.toString());            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);

    }


    private void setupListview() {
        //will remove progress dialog
        listAdapter = new smListAdapter(this, smModelArrayList);
        listView.setAdapter(listAdapter);

    }
}



