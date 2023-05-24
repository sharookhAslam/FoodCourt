package com.example.foodcourt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.stripe.android.paymentsheet.model.PaymentOption;

public class payment_option extends AppCompatActivity {
    CardView cash, debit;
    String tp, total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);
      Intent it = getIntent();
     tp =  it.getStringExtra("tp");
        Toast.makeText(getApplicationContext(), "Total = "+tp, Toast.LENGTH_LONG).show();
        cash = findViewById(R.id.cash);
        debit = findViewById(R.id.debit);

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent itn = new Intent(payment_option.this, Shipment_details.class);
                itn.putExtra("tp",tp);
                startActivity(itn);
            }
        });

        debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itn = new Intent(payment_option.this, CardPayment.class);
                itn.putExtra("tp",tp);
                startActivity(itn);
            }
        });


    }
}