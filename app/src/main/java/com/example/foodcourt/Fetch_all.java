package com.example.foodcourt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Fetch_all extends AppCompatActivity {
    EditText sm_profile, sm_name, sm_email, sm_pass, sm_location;

    Button add_mall, btnBack;
    TextView response1;
    ImageView profile;
    Bitmap bitmap;
    CardView cardView;

    @Override

    protected void
    onCreate(Bundle saveInstanceState) {
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

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        add_mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register_mall(sm_name.getText().toString(), sm_email.getText().toString(), sm_pass.getText().toString(), imageToString(bitmap));

            }



            private void register_mall(String name, String email, String password, String image) {

                String URL = "https://websitedesignbyloftysoft.000webhostapp.com/add_mall.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        sm_name.setText("");
                        sm_email.setText("");
                        sm_pass.setText("");

                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        //profile.setImageResource(0);

                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("true")) {

                                response1.setText(jsonObject.getString("message"));
                                cardView.setVisibility(View.VISIBLE);
                                //  Intent i = new Intent(Add_mall.this, admin_panel.class);
                                //startActivity(i);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                sm_name.setText("");
                                sm_email.setText("");
                                sm_pass.setText("");
                                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show();

                            }
                        }


                ) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("name", name);
                        map.put("email", email);
                        map.put("password", password);
                        map.put("p_name", name);
                        map.put("image", image);

                        return map;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(stringRequest);
            }


        });


    }


    private void imageChooser() {


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
                            bitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        profile.setImageBitmap(
                                bitmap);
                    }
                }
            });





    private String imageToString(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imgBytes);
    }
}