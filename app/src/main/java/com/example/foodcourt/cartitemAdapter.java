package com.example.foodcourt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class cartitemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<cartModel> cartModelArrayList;
    private String UserId;


    public cartitemAdapter(Context context, ArrayList<cartModel> cartModelArrayList,String UserId) {
        this.context = context;
        this.cartModelArrayList = cartModelArrayList;
        this.UserId = UserId;
    }





    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return cartModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cartitemAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new cartitemAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_cart, null, true);

            holder.i_img = (ImageView) convertView.findViewById(R.id.iv);
            holder.i_title = (TextView) convertView.findViewById(R.id.title);
            holder.i_price = (TextView) convertView.findViewById(R.id.price);
            holder.qty = (TextView) convertView.findViewById(R.id.qty);
            holder.delete = (ImageView) convertView.findViewById(R.id.delete_cart);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_item(cartModelArrayList.get(position).item_id);
                    cartModelArrayList.remove(position);
                    notifyDataSetChanged();
                }
            });


            convertView.setTag(holder);


            //final DataModel temp = dataModelArrayList.get(position);
            // final  ItemModel itemModel = itemModelArrayList.get(position);




        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (cartitemAdapter.ViewHolder)convertView.getTag();
        }

        Picasso.get().load(cartModelArrayList.get(position).getPicURL()).into(holder.i_img);
        holder.i_title.setText("Title: "+cartModelArrayList.get(position).getTitle());
        holder.i_price.setText(cartModelArrayList.get(position).getPrice());
        holder.qty.setText(cartModelArrayList.get(position).getQty());


        return convertView;

    }

    public void delete_item(String ItemId) {

        String URL = "https://websitedesignbyloftysoft.000webhostapp.com/food_court/delete_temp_cart.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {


                    } else if(jsonObject.getString("status").equals("false")){
                        //  Toast.makeText(getApplicationContext(),
                        //        jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString().trim(),
                                Toast.LENGTH_LONG).show();

                    }
                }


        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                Log.e("UserId",UserId);
                map.put("item_id", ItemId);
                map.put("user_id", UserId);

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }


    private class ViewHolder {


        protected TextView i_title, i_price, i_des, i_f_id, mall_id, qty;
        private ImageView i_img, delete;
        protected Button i_cart;




    }
}