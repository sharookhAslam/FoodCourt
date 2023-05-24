package com.example.foodcourt;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class temp_cart extends AppCompatActivity {
     TextView textView, textView1, textView3, textView4, textView5;
     ImageView imageView;
     Button btnadd, btnminus;
    com.google.android.material.button.MaterialButton btncart;
Bitmap bitmap;
  int countqty;
  int price;
  int p_num1, p_num2;
  String a, b, c, d, f, g;
  String user_id;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.temp_cart);
        textView = findViewById(R.id.i_title);
        textView1 = findViewById(R.id.i_price);
        textView3 = findViewById(R.id.i_des);
        textView4 = findViewById(R.id.qty);
        textView5 = findViewById(R.id.t_price);
        btnadd = findViewById(R.id.btnadd);
        btncart = findViewById(R.id.i_cart);
    btnminus  =findViewById(R.id.btnminus);
    imageView = findViewById(R.id.i_img);
    Intent i = getIntent();
 a = i.getStringExtra("item_id");
     b = i.getStringExtra("img");
    c = i.getStringExtra("title");
   d = i.getStringExtra("price");
   f = i.getStringExtra("f_id");
     g = i.getStringExtra("mall_id");


     textView1.setText(d);
     textView.setText(c);
     textView5.setText(d);




        User user = new User(temp_cart.this);
        user_id = user.getUser_id();

   countqty = 1;


   btnadd.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           countqty=countqty+1;
           textView4.setText(""+countqty);
           String price1 = textView1.getText().toString();
           p_num1 = Integer.parseInt(price1.substring(1,price1.length()));

           price = p_num1*countqty;
           textView5.setText("£"+price);
           }
       });


       btnminus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               countqty = countqty-1;

               textView4.setText(""
                       +countqty);
               String price1 = textView1.getText().toString();
               p_num1 = Integer.parseInt(price1.substring(1,price1.length()));

               price = p_num1*countqty;
               textView5.setText("£"+price);
           }
       });
       btncart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

   if(textView4.getText().toString().equals("0")){
       Toast.makeText(getApplicationContext(), "Please add quantity", Toast.LENGTH_LONG).show();



   }else
   {

       temp_cart_m();

       //Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_LONG).show();
   }


           }
       });




        Picasso.get().load(b).into(imageView);

    }


    public void temp_cart_m() {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/temp_cart.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                //  profile.setImageResource(0);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(temp_cart.this);
                        alertDialog.setIcon(R.drawable.iconfood);
                        alertDialog.setTitle("Hurrah!");
                        alertDialog.setMessage("Item added in your cart");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                finish();

                            }
                        });
                        alertDialog.create().show();




                    } else if(jsonObject.getString("status").equals("false")){
                      //  Toast.makeText(getApplicationContext(),
                             //   jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Internet connection interupt",
                                Toast.LENGTH_LONG).show();

                    }
                }


        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("title", c);
                map.put("price", textView5.getText().toString());
                map.put("qty", textView4.getText().toString());
                map.put("user_id", user_id);
                map.put("mall_id", g);
                map.put("stall_id", f);
                map.put("item_id", a);
                map.put("image", b);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }








    }






