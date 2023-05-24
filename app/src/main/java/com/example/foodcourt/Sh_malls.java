package com.example.foodcourt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Sh_malls extends AppCompatActivity {
    TextView sd, sd2;
    ImageView  p_pics,logou;
    String imgur;

    BottomNavigationView bottomNavigationView;


   FrameLayout frameLayout;
   Fragment_SM_add fragmentSmAdd = new Fragment_SM_add();
   Fragment_SM_dashboard fragmentSmDashboard = new Fragment_SM_dashboard();
   Fragment_SM_profile fragmentSmProfile = new Fragment_SM_profile();
   Fragment_SM_shop fragmentSmShop = new Fragment_SM_shop();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.sm_profile);
        logou = findViewById(R.id.logout);
        sd = findViewById(R.id.textView3);
        sd2 = findViewById(R.id.textView4);
        bottomNavigationView = findViewById(R.id.nv_btView);
        frameLayout = findViewById(R.id.container);
          p_pics = findViewById(R.id.imageView3);

        Intent i = getIntent();
        String email = i.getStringExtra("email");
  //      String name = i.getStringExtra("name");
        sd.setText(email);
//        sd2.setText(name);
        fetch_malls(email);
   getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentSmDashboard).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.add_shop:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                              fragmentSmAdd).commit();
                        fetch_malls(sd.getText().toString());
                    return true;
                    case R.id.sm_pro:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentSmProfile).commit();
             return true;
                    case R.id.dash_SM:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentSmDashboard).commit();

                        return true;
                    case R.id.shops_SM:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentSmShop).commit();
                        return true;

                }


                return false;
            }
        });



        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new User(Sh_malls.this).removeUser();
                Intent in = new Intent(Sh_malls.this, Trial_login.class);
                startActivity(in);



            }
        });

    }



    public void fetch_malls(String email2) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_mall_profile.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                 //       Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        Log.i("response",response);
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
                                    sd2.setText(name1);
                                    //sd3.setText(imageurl);
                                    fetch_img(imgur);
                                    User user = new User(getApplicationContext());
                                    user.setUser_id(id);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("id", id);
                                    bundle.putString("id2",sd.getText().toString());
                                    fragmentSmAdd.setArguments(bundle);
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
                    public void onResponse(Bitmap response) {

                        p_pics.setImageBitmap(response);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


}