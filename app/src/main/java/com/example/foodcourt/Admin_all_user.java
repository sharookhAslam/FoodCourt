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

public class Admin_all_user extends AppCompatActivity {
    String order_id;
    private ListView listView5;
    ArrayList<DataModel> dataModelArrayList;

    private Admin_alluser_adapter listAdapter5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_user);

        listView5 = findViewById(R.id.lv_orders_user);

        fetch_users();

    }


    public void fetch_users() {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/all_users.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(succes.equals("1"))
                            {

                                dataModelArrayList = new ArrayList<>();
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);


                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    String status= object.getString("status");
                                    String otp= object.getString("otp");

                                    DataModel playerModel = new DataModel();

                                   playerModel.setId(id);
                                   playerModel.setU_name(name);
                                   playerModel.setU_email(email);
                                   playerModel.setU_status(status);
                                   playerModel.setU_otp(otp);


                                    dataModelArrayList.add(playerModel);



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
        listAdapter5 = new Admin_alluser_adapter(this, dataModelArrayList);
        listView5.setAdapter(listAdapter5);



    }

}
