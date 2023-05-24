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

import pl.droidsonroids.gif.GifImageView;


public class Food_dashboard extends Fragment {

    TextView textView;
    private ListView listView;
    ArrayList<fcorderModel> itemModelArrayList;

    private orderfcAdapter listAdapter;
    pl.droidsonroids.gif.GifImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
View v = inflater.inflate(R.layout.fragment_food_dashboard, container, false);

        textView = (TextView) v.findViewById(R.id.id_f);
        listView = (ListView) v.findViewById(R.id.lv_order);
        img = (pl.droidsonroids.gif.GifImageView) v.findViewById(R.id.gifImageView);
  User user = new User(getActivity());
  String food_id = user.getUser_id();

  fetch_items(food_id);

              return v;
    }


    public void fetch_items(String stall_id) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_order_currenttime.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        Log.e("Response",response);
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(succes.equals("1"))
                            {

                                itemModelArrayList = new ArrayList<>();
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("item_id");

                                    String title = object.getString("title");
                                    String price = object.getString("price");
                                    String qty = object.getString("qty");
                                    String order_id = object.getString("order_id");
                                    String date = object.getString("time");

                                    fcorderModel playerModel = new fcorderModel();

                                    playerModel.setTitle(title);
                                    playerModel.setPrice(price);
                                    playerModel.setQty(qty);
                                    playerModel.setPayment_method(order_id);
                                    playerModel.setDate(date.substring(0,10));
                                    playerModel.setOrder_id(order_id);


                                    itemModelArrayList.add(playerModel);


                     textView.setVisibility(View.INVISIBLE);
                     img.setVisibility(View.INVISIBLE);

                                                            /*
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                                        @Override
                                        public void onItemClick(AdapterView<?>
                                        parent, View view, int position, long id) {
                                            Intent intent = new Intent(getApplicationContext(), order_page.class);
                                            intent.putExtra("item_id", playerModel.getDes());
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });
                                    */
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
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("food_id", stall_id);
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }


    private void setupListview(){
        //will remove progress dialog
        listAdapter = new orderfcAdapter(getActivity(), itemModelArrayList);
        listView.setAdapter(listAdapter);



    }

}