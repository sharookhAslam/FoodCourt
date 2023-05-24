package com.example.foodcourt;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;
import java.util.Map;

public class Profile_Update extends AppCompatActivity {

    RecyclerView recyclerView;
    Myadapter myadapter;
    List<ALL_Malls> imageList;
    ALL_Malls modelImage;
    TextView textView;
    ListView list;
    ArrayAdapter<String> food_courts;
    LinearLayoutManager linearLayoutManager;
    @Override

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.profile_update);
    list = findViewById(R.id.list);
        textView = findViewById(R.id.m);
        //textView.setText(getIntent().getStringExtra("mall"));
        String a = getIntent().getStringExtra("mall");
         fetch_malls(a);
        recyclerView = (RecyclerView) findViewById(R.id.all_fc);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        imageList = new ArrayList<>();
        myadapter = new Myadapter(getApplicationContext(), imageList);
        recyclerView.setAdapter(myadapter);



    }



    public void fetch_malls(String mall_id) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fetch_foodCourt.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(succes.equals("1"))
                            {
                                final ArrayList<String> list = new ArrayList<String>();
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("f_id");
                                    String image = object.getString("f_pic");
                                    String name = object.getString("f_name");
                                    String imgUrl = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/fcimages/"+image;
                                    modelImage = new ALL_Malls(id, imgUrl, name);
                                    imageList.add(modelImage);
                                    myadapter.notifyDataSetChanged();
                                    String a[] = new String[] {id, name, imgUrl};
                                    //textView.setText(a[i]);
                                list.add(a[i]);


                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("mall_id", mall_id);
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);

    }
}