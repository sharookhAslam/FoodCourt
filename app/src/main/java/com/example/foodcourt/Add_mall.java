package com.example.foodcourt;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Add_mall extends AppCompatActivity {
    EditText sm_profile, sm_name, sm_email, sm_pass, sm_location;
    FrameLayout progressLO;

    Button add_mall, btnBack;
    TextView response1,longi, lati;
    ImageView profile, loc;
    Bitmap bitmap;
    CardView cardView;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.admin_add_mall);

        sm_name = findViewById(R.id.sm_name);
        sm_email = findViewById(R.id.sm_email);
        sm_pass = findViewById(R.id.sm_pass);
        add_mall = findViewById(R.id.add);
        profile = findViewById(R.id.sm_profile);
        response1 = findViewById(R.id.response1);
        btnBack = findViewById(R.id.btnBack);
        cardView = findViewById(R.id.response_card);
        loc = findViewById(R.id.loc);
        longi = findViewById(R.id.longitude);
        lati = findViewById(R.id.latitude);
        progressLO = findViewById(R.id.prgrssLO);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        add_mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (bitmap == null)
                   Toast.makeText(Add_mall.this,"Add an image",Toast.LENGTH_SHORT).show();
               else if (sm_email.getText().toString().isEmpty())
                   Toast.makeText(Add_mall.this,"Enter email",Toast.LENGTH_SHORT).show();
               else if (sm_name.getText().toString().isEmpty())
                   Toast.makeText(Add_mall.this,"Enter a name",Toast.LENGTH_SHORT).show();
               else if (sm_pass.getText().toString().isEmpty())
                   Toast.makeText(Add_mall.this,"Enter a password",Toast.LENGTH_SHORT).show();
               else {
                   progressLO.setVisibility(View.VISIBLE);
                   register_mall();
               }


            }

        });


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getLocation();

        } else {
            ActivityCompat.requestPermissions((Activity) this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

        }
    }


            public void register_mall() {

                String URL = "https://websitedesignbyloftysoft.000webhostapp.com/add_mall.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      Log.i("Response",response);
                        progressLO.setVisibility(View.GONE);
                        sm_name.setText("");
                        sm_email.setText("");
                        sm_pass.setText("");
                        response1.setVisibility(View.VISIBLE);
                        response1.setText("Successfully Added");
                        profile.setImageResource(R.drawable.profile);
                        cardView.setVisibility(View.VISIBLE);

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
                        map.put("name", sm_name.getText().toString());
                        map.put("email", sm_email.getText().toString());
                        map.put("password", sm_pass.getText().toString());
                        map.put("p_name", sm_email.getText().toString());
                        map.put("longi", longi.getText().toString());
                        map.put("lati", lati.getText().toString());
                        map.put("image", imageToString(bitmap));
                        return map;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(stringRequest);
            }

/*
    public void register_mall_table(String name2, String email2, String password2, String image2) {

        String URL1 = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/add_mall_table.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


        //       Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                //profile.setImageResource(0);

                try {


                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                       Toast.makeText(getApplicationContext(),
                               "Hurray! congrats for new shopping mall", Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show();

                    }
                }


        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map2 = new HashMap<String, String>();
                map2.put("name", name2);
                map2.put("email", email2);
                map2.put("password", password2);
                map2.put("p_name", name2);
                map2.put("image", image2);
                return map2;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

*/

    public void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                                  try {
                                     bitmap = MediaStore.Images.Media.getBitmap(
                                             this.getContentResolver(),
                                             selectedImageUri);
                                      profile.setImageBitmap(
                                              bitmap);
                                  } catch (IOException e) {
                                    Toast.makeText(this, String.valueOf(e), Toast.LENGTH_LONG).show();
                                   //   e.printStackTrace();
                                  }


                    }
                }
            });



   public String imageToString(Bitmap bitmap) {


           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

           bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
       byte[] imgBytes = byteArrayOutputStream.toByteArray();

           return Base64.getEncoder().encodeToString(imgBytes);





   }


    @SuppressLint("MissingPermission")
    private void getLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){

                    try {

                        Geocoder geocoder = new Geocoder(Add_mall.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);
                      double l1 = addresses.get(0).getLatitude();
                        double l2 = addresses.get(0).getLongitude();
                        String s = String.valueOf( l1 ) ;
                        String s5 = s.substring( 0 , s.contains( "." ) ? 6 : 5 ) ;
                        if( s5.endsWith( "." ) ) { s5 = s5.substring( 0 , s5.length() - 1 ) ; }  // Drop FULL STOP in last place.


                        String s2 = String.valueOf( l2 ) ;
                        String s6 = s2.substring( 0 , s2.contains( "." ) ? 6 : 5 ) ;
                        if( s6.endsWith( "." ) ) { s6 = s6.substring( 0 , s6.length() - 1 ) ; }  // Drop FULL STOP in last place.


                        lati.setText(Html.fromHtml("<font><b>Latitute: </b><br/></font>"
                                +s5));
                        longi.setText(Html.fromHtml("<font><b>Longitude: </b><br/></font>"
                                +s6));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

    }


    public void nullClick(View view) {
    }
}

