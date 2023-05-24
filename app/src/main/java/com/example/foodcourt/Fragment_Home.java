package com.example.foodcourt;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Home extends Fragment {


    RecyclerView recyclerView;
    Myadapter myadapter;
    List<ALL_Malls> imageList;
    ALL_Malls modelImage;

    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__home, container, false);
            fetch_malls();
        recyclerView = (RecyclerView) view.findViewById(R.id.all_malls);
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        imageList = new ArrayList<>();
        myadapter = new Myadapter(getActivity().getApplicationContext(), imageList);
        recyclerView.setAdapter(myadapter);

        return view;

    }

    public void fetch_malls() {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/show_malls.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response",response);
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(succes.equals("1"))
                            {
                                for(int i=0; i<jsonArray.length(); i++){
                              JSONObject object = jsonArray.getJSONObject(i);
                               String id = object.getString("id");
                               String image = object.getString("profile_pic");
                               String name = object.getString("name");
                               String imgUrl = "https://websitedesignbyloftysoft.000webhostapp.com/fcimages/"+image;
                              modelImage = new ALL_Malls(id, imgUrl, name);
                              imageList.add(modelImage);
                              myadapter.notifyDataSetChanged();



                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Internet connection interupt", Toast.LENGTH_LONG).show();            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }
}