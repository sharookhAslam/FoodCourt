package com.example.foodcourt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Fragment_profile extends androidx.fragment.app.Fragment {
  String i, b, c, d;
  ImageView user_profile;
  EditText nameEdit;
  Bitmap bitmap;
    com.google.android.material.textfield.TextInputEditText etprevious, etPassword;
    Button button2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        user_profile = (ImageView) v.findViewById(R.id.user_profile);
        nameEdit = (EditText) v.findViewById(R.id.nameEdit);
        etprevious = ( com.google.android.material.textfield.TextInputEditText) v.findViewById(R.id.etprevious);
        etPassword = ( com.google.android.material.textfield.TextInputEditText) v.findViewById(R.id.etPassword);
        button2 = (Button) v.findViewById(R.id.button2);


User user = new User(getActivity());

fetch_user(user.getName());

button2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        User user = new User(getActivity());
        profile_update(user.getName());
    }
});
user_profile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        imageChooser();
    }
});


        return  v;
    }


    public void fetch_user(String email) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_user.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(succes.equals("1"))
                            {


                                for(int i=0; i<jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String image = object.getString("profile_pic");
                                    String name = object.getString("name");
                                    String pass = object.getString("password");



                                    String imgUrl = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fcimages/items/" + image;


                              nameEdit.setText(name);



                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Internet connection interupt", Toast.LENGTH_LONG).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("email", email);
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void profile_update(String user_email) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/update_profile.php";

        StringRequest stringRequest =
                new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



              //  Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();

                //profile.setImageResource(0);

                try {


                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {



                    } else if(jsonObject.getString("status").equals("false")){
                      //  Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), "Internet connection interupt", Toast.LENGTH_LONG).show();

                    }
                }


        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("name", nameEdit.getText().toString());
                map.put("pass", etprevious.getText().toString());
                map.put("cpass", etPassword.getText().toString());
                map.put("user_email", user_email);
                map.put("image", imageToString(bitmap));
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }



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
                            bitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    getActivity().getContentResolver(),
                                    selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        user_profile.setImageBitmap(
                                bitmap);
                    }
                }
            });


    public String imageToString(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();

        if (bitmap == null)
        {
            //Toast.makeText(getActivity().getApplicationContext(), "Added without profile image", Toast.LENGTH_LONG).show();
        } else {

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        }
        byte[] imgBytes;
        imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imgBytes);
    }
}


