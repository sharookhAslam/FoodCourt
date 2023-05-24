package com.example.foodcourt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;


public class food_court_view_item extends Fragment {

    TextView textView;
    private ListView listView;
    ArrayList<itemfcModel> itemModelArrayList;

    private view_item_fcAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_food_court_view_item, container, false);
        listView = (ListView) v.findViewById(R.id.menu);


        Bundle bundle = this.getArguments();
        String i = bundle.getString("f_id");
        fetch_items(i);
        return v;
    }


    public void fetch_items(String stall_id) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_items.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response",response);
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (succes.equals("1")) {

                                itemModelArrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("item_id");
                                    String image = object.getString("picture");
                                    String title = object.getString("title");
                                    String price = object.getString("price");
                                    String description = object.getString("description");

                                    String imgUrl = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fcimages/items/" + image;
                                    itemfcModel playerModel = new itemfcModel();

                                    playerModel.setTitle(title);
                                    playerModel.setPrice(price);
                                    playerModel.setPicURL(imgUrl);
                                    playerModel.setDes(description);

                                    itemModelArrayList.add(playerModel);


                                    setupListview();

                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("stall_id", stall_id);
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }


    private void setupListview() {
        //will remove progress dialog
        listAdapter = new view_item_fcAdapter(getActivity(), itemModelArrayList);
        listView.setAdapter(listAdapter);


    }
}

