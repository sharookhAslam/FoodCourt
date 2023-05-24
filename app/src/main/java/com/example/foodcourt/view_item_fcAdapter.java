package com.example.foodcourt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class view_item_fcAdapter extends BaseAdapter{

    private int counter = 0;

    private Context context;
    private ArrayList<itemfcModel> itemModelArrayList;


    public view_item_fcAdapter(Context context, ArrayList<itemfcModel> itemModelArrayList) {

        this.context = context;
        this.itemModelArrayList = itemModelArrayList;


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
        return itemModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      view_item_fcAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new view_item_fcAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_menu_fc, null, true);

            holder.i_img = (ImageView) convertView.findViewById(R.id.i_img);
            holder.i_title = (TextView) convertView.findViewById(R.id.i_title);
            holder.i_price = (TextView) convertView.findViewById(R.id.i_price);
            holder.i_des = (TextView) convertView.findViewById(R.id.i_des);
            holder.i_cart = (Button) convertView.findViewById(R.id.i_cart);
            holder.i_f_id = (TextView) convertView.findViewById(R.id.i_f_id);
            holder.mall_id = (TextView) convertView.findViewById(R.id.mall_id);



            convertView.setTag(holder);





        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (view_item_fcAdapter.ViewHolder)convertView.getTag();
        }

        Picasso.get().load(itemModelArrayList.get(position).getPicURL()).into(holder.i_img);
        holder.i_title.setText(itemModelArrayList.get(position).getTitle());
        holder.i_price.setText("£"+itemModelArrayList.get(position).getPrice());
        holder.i_des.setText(itemModelArrayList.get(position).getDes());
        holder.i_f_id.setText(itemModelArrayList.get(position).getFood_id());
        holder.mall_id.setText(itemModelArrayList.get(position).getMall_id());
        return convertView;

    }

    private class ViewHolder {


        protected TextView i_title, i_price, i_des, i_f_id, mall_id;
        private ImageView i_img;
        protected Button i_cart;




    }










}