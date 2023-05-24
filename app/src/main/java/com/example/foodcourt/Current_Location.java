package com.example.foodcourt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Current_Location extends AppCompatActivity {

     Button gtLocation;
     TextView text1, text2, text3, text4, text5;
     FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.current_location);


        gtLocation = (Button) findViewById(R.id.gtLocaton);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        text5 = (TextView) findViewById(R.id.text5);

       fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Current_Location.this);

       gtLocation.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(ActivityCompat.checkSelfPermission(Current_Location.this,
                       Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                        getLocation();

               }else{
                   ActivityCompat.requestPermissions(Current_Location.this,
                           new String[]{Manifest.permission.ACCESS_FINE_LOCATION},  44);

               }
           }
       });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){

                    try {

                        Geocoder geocoder = new Geocoder(Current_Location.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);
                        text1.setText(Html.fromHtml("<font><b>Latitute: </b><br/></font>"
                                +addresses.get(0).getLatitude()));
                        text2.setText(Html.fromHtml("<font><b>Longitude: </b><br/></font>"
                                +addresses.get(0).getLongitude()));
                        text3.setText(Html.fromHtml("<font><b>Country: </b><br/></font>"
                                +addresses.get(0).getCountryName()));
                        text4.setText(Html.fromHtml("<font><b>Locality: </b><br/></font>"
                                +addresses.get(0).getLocality()));
                        text5.setText(Html.fromHtml("<font><b>Address: </b><br/></font>"
                                +addresses.get(0).getAddressLine(0)));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

    }

}
