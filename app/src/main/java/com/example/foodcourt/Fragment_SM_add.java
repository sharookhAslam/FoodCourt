package com.example.foodcourt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class Fragment_SM_add extends Fragment {

    ImageView fc_profile;
    EditText etname, etEmail, etpass;
    Button fc_add;
    TextView response1, mall_id_dis, mall_name;
    CardView cardView;
    FrameLayout progressLO;
    int count = 0;
    Bitmap bitmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__s_m_add, container, false);

        etname = (EditText) view.findViewById(R.id.fc_name);
        etEmail= (EditText)  view.findViewById(R.id.fc_email);
        etpass = (EditText)  view.findViewById(R.id.fc_pass);
        fc_add = (Button)  view.findViewById(R.id.fc_add);
        response1 = (TextView) view.findViewById(R.id.response1);
        cardView = (CardView) view.findViewById(R.id.response_card);
        fc_profile = (ImageView) view.findViewById(R.id.fc_profile);
        mall_id_dis = (TextView) view.findViewById(R.id.mall_id);
        mall_name = (TextView) view.findViewById(R.id.mall_id_name);
        progressLO = view.findViewById(R.id.prgrssLO);
        Bundle bundle = this.getArguments();

            String i = bundle.getString("id");
            String b = bundle.getString("id2");
            mall_id_dis.setText(i);
            mall_name.setText(b);


        fc_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });


    fc_add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (etname.getText().toString().isEmpty())
                Toast.makeText(getContext(),"Enter name",Toast.LENGTH_SHORT).show();
            else if (etEmail.getText().toString().isEmpty())
                Toast.makeText(getContext(),"Enter email",Toast.LENGTH_SHORT).show();
            else if (etpass.getText().toString().isEmpty())
                Toast.makeText(getContext(),"Enter Password",Toast.LENGTH_SHORT).show();
            else if (bitmap == null)
                Toast.makeText(getContext(),"Select an Image",Toast.LENGTH_SHORT).show();
            else {
                progressLO.setVisibility(View.VISIBLE);
                fc_add(etname.getText().toString(), etEmail.getText().toString(),
                        etpass.getText().toString(), imageToString(bitmap));
                register_fc_table(i,
                        etname.getText().toString(), etEmail.getText().toString(),
                        etpass.getText().toString(), imageToString(bitmap));
            }

        }

    });

        return view;
    }

    public void fc_add(String name, String email, String password, String image) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/add_food_court.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                //profile.setImageResource(0);
                progressLO.setVisibility(View.GONE);
                etname.setText("");
                etEmail.setText("");
                etpass.setText("");
                fc_profile.setImageResource(R.drawable.placeholder_image);
                try {


                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {

                        response1.setText(jsonObject.getString("message"));
                        cardView.setVisibility(View.VISIBLE);

                    } else if(jsonObject.getString("status").equals("false")){
                        Toast.makeText(getActivity().getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity().getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show();

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

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(stringRequest);
    }

    public void register_fc_table(String mall_id2, String name2, String email2, String password2, String image2) {

        String URL1 = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fc_table_reg.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


               // Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_LONG).show();

                //profile.setImageResource(0);

                try {


                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Hurray! congrats for new foodcourt", Toast.LENGTH_LONG).show();

                    }else if(jsonObject.getString("status").equals("false")){
                        Toast.makeText(getActivity().getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show();

                    }
                }


        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map2 = new HashMap<String, String>();
                map2.put("mall_id", mall_id2);
                map2.put("name", name2);
                map2.put("email", email2);
                map2.put("password", password2);
                map2.put("p_name", name2);
                map2.put("image", image2);
                return map2;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
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
                                    getActivity().getApplicationContext().getContentResolver(),
                                    selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        fc_profile.setImageBitmap(
                                bitmap);
                    }
                }
            });


    public String imageToString(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();

        if (bitmap == null)
        {
            Toast.makeText(getActivity().getApplicationContext(), "Added without profile image", Toast.LENGTH_LONG).show();
        } else {

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        }
        byte[] imgBytes;
        imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imgBytes);
    }

}