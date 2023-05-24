package com.example.foodcourt;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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


public class Fragment_history extends androidx.fragment.app.Fragment {
  TextView item_id_t;
    private ListView listView;
    ArrayList<HistoryModel> historyModelArrayList;

    private HistoryAdapter listAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_history, container, false);
        listView = (ListView) view.findViewById(R.id.lv_history);
        item_id_t = view.findViewById(R.id.item_id);


User user = new User(getActivity());
fetch_user_history(user.getUser_id());

return view;
    }




    public void fetch_user_history(String id) {

        String URL = " https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_user_history.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     //   Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(succes.equals("1"))
                            {

                                historyModelArrayList = new ArrayList<>();
                                for(int i=0; i<jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String order_id = object.getString("order_id");
                                    String title = object.getString("title");
                                    String qty = object.getString("qty");
                                    String mall_id = object.getString("mall_id");
                                    String food_id = object.getString("food_id");
                                    String item_id = object.getString("item_id");
                                    String price = object.getString("price");
                                    String image = object.getString("picture");
                                    item_id_t.setText(item_id);
                                    HistoryModel playerModel = new HistoryModel();
                                    String imgURL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fcimages/items/"+image;
                                    playerModel.setTitle(title);
                                    playerModel.setPrice(price);
                                    playerModel.setQty(qty);
                                    playerModel.setPicURL(imgURL);

                                    historyModelArrayList.add(playerModel);

                                }
                                setupListview();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Internet connection interupt", Toast.LENGTH_LONG).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("user_id", id);
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }




    private void setupListview(){
        //will remove progress dialog
        listAdapter = new HistoryAdapter(getActivity(), historyModelArrayList);
        listView.setAdapter(listAdapter);



    }
}