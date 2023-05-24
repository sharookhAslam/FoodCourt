package com.example.foodcourt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Fragment_CurrentLocation extends Fragment {


    Button gtLocation;
    TextView text1, text2, text3, text4, text5;
    Context thisContext;

    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisContext = container.getContext();

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment__current_location, container, false);


        gtLocation = (Button) view.findViewById(R.id.gtLocaton);
        text1 = (TextView) view.findViewById(R.id.text1);
        text2 = (TextView) view.findViewById(R.id.text2);
        text3 = (TextView) view.findViewById(R.id.text3);
        text4 = (TextView) view.findViewById(R.id.text4);
        text5 = (TextView) view.findViewById(R.id.text5);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(thisContext);

        if (ActivityCompat.checkSelfPermission(thisContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getLocation();

        } else {
            ActivityCompat.requestPermissions((Activity) thisContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

        }
        return view;

    }




    @SuppressLint("MissingPermission")
    private void getLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){

                    try {

                        Geocoder geocoder = new Geocoder(thisContext, Locale.getDefault());
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