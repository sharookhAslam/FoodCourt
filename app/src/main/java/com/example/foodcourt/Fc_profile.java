package com.example.foodcourt;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.view.View;
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

public class Fc_profile extends AppCompatActivity {
    TextView sd, textView4, sd3;
    ImageView logou;
    ImageView p_pics;
    String imgur;

    BottomNavigationView bottomNavigationView;

  Fragment_add_item fragment_add_item = new Fragment_add_item();
  Food_dashboard food_dashboard = new Food_dashboard();
   food_court_view_item food_court_view_item = new food_court_view_item();
    Food_courtprofile food_courtprofile = new Food_courtprofile();
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.f_profile);
        logou = findViewById(R.id.logout);
        sd = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        p_pics = findViewById(R.id.imageView3);
        bottomNavigationView = findViewById(R.id.nv_btView);

        Intent i = getIntent();
        String email = i.getStringExtra("email");
        fetch_fc_id(email);
        textView4.setText(email);


        getSupportFragmentManager().beginTransaction().replace(R.id.container, food_dashboard).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.add_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                fragment_add_item).commit();


                        return true;
                    case R.id.all_items:

                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                              food_court_view_item).commit();
                      return true;


                      case R.id.profile:
                          getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                food_courtprofile).commit();
                          return true;

                    case R.id.dash_food:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                food_dashboard).commit();
                        return true;
                    case R.id.invoice:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                food_courtprofile).commit();
                        return true;

                }


                return true;
            }
        });


        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new User(Fc_profile.this).removeUser();
                Intent in = new Intent(Fc_profile.this, Trial_login.class);
                startActivity(in);


            }
        });

    }

    public void fetch_fc_id(String email2) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fecth_food_profile.php";

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

                                    String id = jsonObject1.getString("f_id");
                                    String name1 = jsonObject1.getString("f_name");
                                    String mallid = jsonObject1.getString("mall_id");

                                    String p_pic = jsonObject1.getString("f_pic");
                                    imgur = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fcimages/"+p_pic;
                                    sd.setText(name1);
                                    //sd3.setText(imageurl);
                                    fetch_img(imgur);

                                    User user2 = new User(getApplicationContext());
                                    user2.setUser_id(id);

                                    Bundle bundle = new Bundle();
                                   bundle.putString("f_id", id);
                                   bundle.putString("mall_id", mallid);
                                    fragment_add_item.setArguments(bundle);



                                    Bundle bundle2 = new Bundle();
                                    bundle2.putString("f_id", id);
                                    food_court_view_item.setArguments(bundle2);


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

                map.put("f_email", email2);


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