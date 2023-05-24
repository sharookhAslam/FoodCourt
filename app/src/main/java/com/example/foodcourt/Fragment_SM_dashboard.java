package com.example.foodcourt;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
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

public class Fragment_SM_dashboard extends Fragment {
    TextView textView;
    private ListView listView;
    FrameLayout progressLO;
    ArrayList<DataModel> dataModelArrayList;
    private ListAdapter listAdapter;
    pl.droidsonroids.gif.GifImageView img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment__s_m_dashboard, container, false);
        textView = (TextView) v.findViewById(R.id.id_f);
        listView = (ListView) v.findViewById(R.id.lv_order);
        progressLO = v.findViewById(R.id.prgrssLO);
        img = (pl.droidsonroids.gif.GifImageView) v.findViewById(R.id.gifImageView);
         User u = new User(getActivity());

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                fetch_malls(u.getUser_id());
            }
        },2000);

               return v;

    }


    public void fetch_malls(String mall_id) {
        Log.e("id",mall_id);
        progressLO.setVisibility(View.VISIBLE);

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_foodCourt.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response",response);
                        progressLO.setVisibility(View.GONE);
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (succes.equals("1")) {
                                dataModelArrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("f_id");
                                    String image = object.getString("f_pic");
                                    String name = object.getString("f_name");
                                    String imgUrl = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fcimages/" + image;

                                    DataModel playerModel = new DataModel();

                                    playerModel.setName(name);

                                    playerModel.setImgURL(imgUrl);
                                    dataModelArrayList.add(playerModel);

textView.setVisibility(View.INVISIBLE);
img.setVisibility(View.INVISIBLE);



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
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("mall_id", mall_id);
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }
    private void setupListview() {
        //will remove progress dialog
        listAdapter = new ListAdapter(getActivity(), dataModelArrayList,2);
        listView.setAdapter(listAdapter);

    }

}