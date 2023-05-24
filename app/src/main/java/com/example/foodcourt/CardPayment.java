package com.example.foodcourt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CardPayment extends AppCompatActivity {

    String PUBLISH_KEY = "pk_test_51LiUr3IByFlUM2XAbB2cqfUh6xnLsA71vUQpKeTHHDHBYAih6odbgEfwpEexw0KOX5FzYkznWZYKdAZBK0OGvb5h007TWaq5jW";
    String SECRET_KEY = "sk_test_51LiUr3IByFlUM2XAUnf9QKyRazsKFJtDb6uyweZcJynCE4Bhb3TpOSmrG4X9SfvV7IWaLitPUulStNhouWTIIZYd00ejgdazEe";
    PaymentSheet paymentSheet;

    String CustomerId;
    String ClientSecret;
    String ephermal_key;
    String total, tp;

    FrameLayout progressLO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);
        progressLO = findViewById(R.id.prgrssLO);
        Intent it = getIntent();
        tp =  it.getStringExtra("tp");
        Log.e("tp",tp);

        PaymentConfiguration.init(this,PUBLISH_KEY);
        paymentSheet = new PaymentSheet(this, PaymentSheetResult->{
            onPaymentResult(PaymentSheetResult);
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            CustomerId = jsonObject.getString("id");
                            Log.e("CustomerId",CustomerId);
                            getEphermalKey(CustomerId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                header.put("Content-Type","application/x-www-form-urlencoded");
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed){
            progressLO.setVisibility(View.GONE);
            Toast.makeText(CardPayment.this,"Payment Success.",Toast.LENGTH_SHORT).show();
            Intent itn = new Intent(this, Shipment_details.class);
            itn.putExtra("tp",tp);
            startActivity(itn);
        }else {
            Toast.makeText(CardPayment.this,"Payment failed",Toast.LENGTH_SHORT).show();
        }
    }

    private void getEphermalKey(String customerId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            ephermal_key = jsonObject.getString("id");
                            Log.e("Ephermal Key",ephermal_key);
                            getClientSecret(customerId,ephermal_key);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                header.put("Content-Type","application/x-www-form-urlencoded");
                header.put("Stripe-Version","2022-08-01");
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("customer",customerId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getClientSecret(String customerId, String ephermalKey) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            ClientSecret = jsonObject.getString("client_secret");
                            Log.e("ClientSecret",ClientSecret);
                            PaymentFlow();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        Log.e("error",res);
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                header.put("Content-Type","application/x-www-form-urlencoded");
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("customer",customerId);
                params.put("amount", tp.substring(1,tp.length())+"00");
                params.put("currency","gbp");
                params.put("automatic_payment_methods[enabled]","true");
                return params ;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void PaymentFlow() {
        paymentSheet.presentWithPaymentIntent(
                ClientSecret,new PaymentSheet.Configuration("FoodCourtUk",
                        new PaymentSheet.CustomerConfiguration(
                                CustomerId,ephermal_key
                        ))
        );
    }
}