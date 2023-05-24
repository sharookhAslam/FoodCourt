package com.example.foodcourt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment_Support extends androidx.fragment.app.Fragment {
    Button btncall;
    Button btnchat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //btnchat = getView().findViewById(R.id.btnchat);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__support, container, false);
        btnchat = (Button) view.findViewById(R.id.btnchat);
        btnchat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String number = "+923431113198";
                String url = "https://api.whatsapp.com/send?phone="+number;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        btncall = (Button) view.findViewById(R.id.btncall);
        btncall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:+923431113198"));
                startActivity(callIntent);
            }
        });
        return view;

    }
}