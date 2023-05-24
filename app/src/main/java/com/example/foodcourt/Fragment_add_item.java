package com.example.foodcourt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Fragment_add_item extends Fragment {
TextInputLayout cat_menu;
    com.google.android.material.textfield.TextInputEditText title, price, des, additional, qty;
    com.google.android.material.textfield.MaterialAutoCompleteTextView autoCompleteTextView;
    com.google.android.material.textview.MaterialTextView f_id, m_id;
    com.google.android.material.imageview.ShapeableImageView item_pic;
    com.google.android.material.button.MaterialButton add_item;
    FrameLayout progressLO;
  Bitmap bitmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_item, container, false);

         autoCompleteTextView=
                (com.google.android.material.textfield.MaterialAutoCompleteTextView) v.findViewById(R.id.cat);



        f_id =
                (com.google.android.material.textview.MaterialTextView) v.findViewById(R.id.f_id);
         m_id =
                (com.google.android.material.textview.MaterialTextView) v.findViewById(R.id.m_id);

        title = (com.google.android.material.textfield.TextInputEditText) v.findViewById(R.id.title);
        price = (com.google.android.material.textfield.TextInputEditText) v.findViewById(R.id.price);
        qty = (com.google.android.material.textfield.TextInputEditText) v.findViewById(R.id.qty);

        des = (com.google.android.material.textfield.TextInputEditText) v.findViewById(R.id.des);
        additional = (com.google.android.material.textfield.TextInputEditText) v.findViewById(R.id.additional);
  item_pic = (com.google.android.material.imageview.ShapeableImageView) v.findViewById(R.id.item_pic);
  add_item = (com.google.android.material.button.MaterialButton) v.findViewById(R.id.add_item);
  progressLO = v.findViewById(R.id.prgrssLO);

  add_item.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          if (title.getText().toString().isEmpty())
              Toast.makeText(getContext(),"Enter Title",Toast.LENGTH_SHORT).show();
          else if (price.getText().toString().isEmpty())
              Toast.makeText(getContext(),"Enter Price",Toast.LENGTH_SHORT).show();
          else if (qty.getText().toString().isEmpty())
              Toast.makeText(getContext(),"Enter Quantity",Toast.LENGTH_SHORT).show();
          else if (autoCompleteTextView.getText().toString().isEmpty())
              Toast.makeText(getContext(),"Select Category",Toast.LENGTH_SHORT).show();
          else if (bitmap == null)
              Toast.makeText(getContext(),"Select a picture",Toast.LENGTH_SHORT).show();
          else
            add_item();
      }
  });




item_pic.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        imageChooser();
    }
});

        Bundle bundle = this.getArguments();
        String i = bundle.getString("f_id");
        String b = bundle.getString("mall_id");
        f_id.setText(i);
        m_id.setText(b);
        cat_menu = (TextInputLayout) v.findViewById(R.id.cat_menu);

   String [] cat = {"Hot", "Cold"};

        ArrayAdapter<String> cat_adapter =  new ArrayAdapter<>(getActivity(), R.layout.categories_dropdown, cat);
        autoCompleteTextView.setAdapter(cat_adapter);


        return v;
    }



    public void add_item() {
        progressLO.setVisibility(View.VISIBLE);
        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/add_food_items.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response",response);
                try {
                    progressLO.setVisibility(View.GONE);
                    title.setText("");
                    price.setText("");
                    qty.setText("");
                    des.setText("");
                    autoCompleteTextView.setText("");
                    item_pic.setImageResource(R.drawable.camera);
                    Toast.makeText(getActivity(),
                            "Item Added", Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {

                    } else if(jsonObject.getString("status").equals("false")){
                        Toast.makeText(getActivity(),
                                jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString().trim(),
                                Toast.LENGTH_LONG).show();

                    }
                }


        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("title", title.getText().toString());
                map.put("price", price.getText().toString());
                map.put("qty", qty.getText().toString());
                map.put("description", des.getText().toString());
                map.put("image", imageToString(bitmap));
                map.put("additional", additional.getText().toString());
                map.put("category", autoCompleteTextView.getText().toString());
                map.put("stall_id", f_id.getText().toString());
                map.put("mall_id", m_id.getText().toString());
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }
//volley code to add items




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
                       item_pic.setImageBitmap(
                                bitmap);
                    }
                }
            });


    public String imageToString(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();

        if (bitmap == null)
        {
            Toast.makeText(getActivity(), "Added without profile image", Toast.LENGTH_LONG).show();
        } else {

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        }
        byte[] imgBytes;
        imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imgBytes);
    }


}
