package com.example.foodcourt;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User_Profile extends AppCompatActivity {
    DrawerLayout user_menu;
    NavigationView menu_user;
    Toolbar toolbar;
    LinearLayout linearLayout;
    ActionBarDrawerToggle toggle;
    ImageView u_profile;


    @Override

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        user_menu = (DrawerLayout) findViewById(R.id.drawer_layout);
        menu_user = (NavigationView) findViewById(R.id.nvView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        u_profile = findViewById(R.id.u_profile);
      //  linearLayout = (LinearLayout) findViewById(R.id.layout_fragment);

        setSupportActionBar(toolbar);
        setupDrawerContent(menu_user);



        menu_user.bringToFront();
        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this, user_menu, toolbar, R.string.navigation_start, R.string.Navigatio_close);
        user_menu.addDrawerListener(toggle);
        toggle.syncState();




    }


    @Override
    public void onBackPressed() {
        if(user_menu.isDrawerOpen(GravityCompat.START)) {
            user_menu.closeDrawer(GravityCompat.START);
            finish();

        }
    }


    private void setupDrawerContent(NavigationView navigationView) {


        navigationView.setCheckedItem(R.id.home);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment_Home fragment = new Fragment_Home();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


        Intent i = getIntent();
        String email = i.getStringExtra("email");
        Toast.makeText(getApplicationContext(), ""+email, Toast.LENGTH_LONG).show();


        fetch_user(email);

        navigationView.setNavigationItemSelectedListener(


                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }




    public void selectDrawerItem(MenuItem menuItem) {


        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.home:
                fragmentClass = Fragment_Home.class;

                   break;
            case R.id.nav_first_fragment:
                fragmentClass = Fragment_profile.class;

                break;
            case R.id.nav_second_fragment:
                fragmentClass = Fragment_history.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = Fragment_Support.class;
                break;
            case R.id.current_loc:
                fragmentClass = Fragment_CurrentLocation.class;
                break;
            case R.id.referal:
                fragmentClass = fragment_referral.class;
                break;
            case R.id.logout:
                new User(User_Profile.this).removeUser();
                Intent i = new Intent(User_Profile.this, Trial_login.class);
                startActivity(i);
            default:
                fragmentClass = Fragment_Home.class;
          break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);



        // Set action bar title
        setTitle(menuItem.getTitle());

        // Close the navigation drawer
        user_menu.closeDrawers();
    }


    public void fetch_user(String email) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_user.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(succes.equals("1"))
                            {


                                for(int i=0; i<jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String image = object.getString("profile_pic");
                                    String name = object.getString("name");
                                    String pass = object.getString("password");
                                    User user = new User(User_Profile.this);
                                    user.setUser_id(id);


                                    String imgUrl = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fcimages/items/" + image;



                                    toolbar.setTitle(name);



                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Internet connection interupt", Toast.LENGTH_LONG).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("email", email);
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);

    }
   

}



