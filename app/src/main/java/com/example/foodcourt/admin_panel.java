package com.example.foodcourt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class admin_panel extends AppCompatActivity {
   CardView news_feed, sm, order_history, settings;
  Toolbar toolbar;
 ImageView logout;
 Bitmap bitmap;
 TextView show_image;
    String imgur;
    String p_pic;
    @Override

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.admin_profile);
        logout = findViewById(R.id.logout);
        news_feed = findViewById(R.id.news_feed);
        sm = findViewById(R.id.sm);
        order_history = findViewById(R.id.order_history);

        toolbar = findViewById(R.id.toolbar);
         Intent i = getIntent();

         String email = i.getStringExtra("email");
         String name = i.getStringExtra("name");
         p_pic = i.getStringExtra("profile_pic");

//         toolbar.setSubtitle(email);
        fetch_malls(email);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new User(admin_panel.this).removeUser();
                Intent vf = new Intent(admin_panel.this, Trial_login.class);
                startActivity(vf);
            }
        });

        news_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iff = new Intent(admin_panel.this, Admin_all_user.class);
                startActivity(iff);
            }
        });

        sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(admin_panel.this, Admin_shopingmall_list.class);
                startActivity(i);


            }
        });

        order_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(admin_panel.this, all_orders_admin.class);
                startActivity(i);
            }
        });
            }





    public void fetch_malls(String email2) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_mall_profile.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     //   Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            //String re = jsonObject.getString("message");

                            if(jsonObject.getString("message").equals("fetched")){


                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int a = 0; a<jsonArray.length(); a++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(a);

                                    String id = jsonObject1.getString("id");
                                    String name1 = jsonObject1.getString("name");
                                    String p_pic = jsonObject1.getString("profile_pic");
                                   imgur = "https://websitedesignbyloftysoft.000webhostapp.com/fcimages/"+p_pic;

//                                    toolbar.setTitle(name1);
                                    fetch_img(imgur);
                                    User user = new User(getApplicationContext());
                                    user.setUser_id(id);

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
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map =  new HashMap<String, String>();

                map.put("email", email2);


                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
 private void fetch_img(String imgur){

        ImageRequest imageRequest = new ImageRequest( imgur,

                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {


                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(imageRequest);
    }

}