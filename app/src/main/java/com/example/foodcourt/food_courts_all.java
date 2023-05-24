package com.example.foodcourt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class food_courts_all extends AppCompatActivity {

    private ListView listView;
    ArrayList<DataModel> dataModelArrayList;
    private ListAdapter listAdapter;


    TextView fc_id;
    String a, b;
    androidx.appcompat.widget.Toolbar toolbar_food;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.list_food_courts);
        listView = findViewById(R.id.lv);
        //textView.setText(getIntent().getStringExtra("mall"));
        a = getIntent().getStringExtra("mall");
        b = getIntent().getStringExtra("mall_name");

        fc_id = findViewById(R.id.food_id);
        toolbar_food = findViewById(R.id.toolbar_food);
        toolbar_food.setTitle(b);


        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(getApplicationContext());

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getLocation();


        } else {
            ActivityCompat.requestPermissions(food_courts_all.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

        }


    }


    public void fetch_malls(String mall_id) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_foodCourt.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response",response);
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (succes.equals("1")) {
                                dataModelArrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String f_id = object.getString("f_id");
                                    String image = object.getString("f_pic");
                                    String name = object.getString("f_name");
                                    String imgUrl = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fcimages/" + image;

                                    DataModel playerModel = new DataModel();
                                    fc_id.setText(f_id);
                                    playerModel.setName(name);
                                    playerModel.setCity(f_id);

                                    playerModel.setImgURL(imgUrl);
                                    dataModelArrayList.add(playerModel);

                               /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                       User user = new User(getApplicationContext());
                                        user.setF_id(f_id);

                                        Intent i = new Intent(getApplicationContext(), Items_page.class);
                                        i.putExtra("mall_id", a);
                                        startActivity(i);
                                    }
                                });
*/


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
                Toast.makeText(getApplicationContext(), "Internet connection interupt", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("mall_id", mall_id);
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void setupListview() {
        //will remove progress dialog
        listAdapter = new ListAdapter(this, dataModelArrayList,1);
        listView.setAdapter(listAdapter);

    }


    @SuppressLint("MissingPermission")
    private void getLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {

                        Geocoder geocoder = new Geocoder(food_courts_all.this, Locale.getDefault());
                        List<Address> addresses = null;

                        addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);

                        double lat = addresses.get(0).getLatitude();
                        double longi = addresses.get(0).getLongitude();





                        String s = String.valueOf( lat ) ;
                        String s5 = s.substring( 0 , s.contains( "." ) ? 6 : 5 ) ;
                        if( s5.endsWith( "." ) ) { s5 = s5.substring( 0 , s5.length() - 1 ) ; }  // Drop FULL STOP in last place.


                        String s2 = String.valueOf( longi ) ;
                        String s6 = s2.substring( 0 , s2.contains( "." ) ? 6 : 5 ) ;
                        if( s6.endsWith( "." ) ) { s6 = s6.substring( 0 , s6.length() - 1 ) ; }
                        String lat2 = "" +s5;
                        String longi2 = "" +s6;
                        match_location(a, longi2, lat2);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }


            }
        });

    }

    public  void match_location(String mal_id, String logi, String lati){


        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_location.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {


                            JSONObject jsonObject = new JSONObject(response);



                            if(jsonObject.getString("status").equals("true"))
                            {

                                fetch_malls(a);
                            }else {


                               // Toast.makeText(getApplicationContext(), "You can only order within shopping mall", Toast.LENGTH_LONG).show();

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(food_courts_all.this);
                                alertDialog.setIcon(R.drawable.iconfood);
                                alertDialog.setTitle("Oops!");
                                alertDialog.setMessage("Seems Like you are not at this food court");
                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        finish();

                                    }
                                });
                                alertDialog.create().show();

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
                // map.put("mall_id",holder.mall_id.getText().toString());
                map.put("longi", logi);
                map.put("id", mal_id);
                map.put("lati", lati);
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);


    }

}


