package com.example.foodcourt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class order_page extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
  textView = findViewById(R.id.item_id);
        Intent i =new Intent();
        textView.setText(i.getStringExtra("item_id"));
    }


}