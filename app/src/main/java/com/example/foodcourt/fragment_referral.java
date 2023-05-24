package com.example.foodcourt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;


public class fragment_referral extends Fragment {
  Button btnReferral, btnShare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_referral, container, false);

        btnShare = (Button) view.findViewById(R.id.button3);
        btnShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String body = "Your body here";
                String sub = "Your Subject";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });
        btnReferral = (Button) view.findViewById(R.id.button);
        btnReferral.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Snackbar mySnackbar = Snackbar.make(view, "Comming soon in your area!", Snackbar.LENGTH_LONG);

                mySnackbar.show();
            }
        });
        return view;

    }

    }
