package com.example.foodcourt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ListitmeAdapter extends BaseAdapter{

   private int counter = 0;

    private Context context;
    private ArrayList<ItemModel> itemModelArrayList;
    private ActivityCompat listitmeAdapter;

    public ListitmeAdapter(Context context, ArrayList<ItemModel> itemModelArrayList) {

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
        ListitmeAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new ListitmeAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.i_img = (ImageView) convertView.findViewById(R.id.i_img);
            holder.i_title = (TextView) convertView.findViewById(R.id.i_title);
            holder.i_price = (TextView) convertView.findViewById(R.id.i_price);
            holder.i_des = (TextView) convertView.findViewById(R.id.i_des);
            holder.i_cart = (Button) convertView.findViewById(R.id.i_cart);
            holder.i_f_id = (TextView) convertView.findViewById(R.id.i_f_id);
            holder.mall_id = (TextView) convertView.findViewById(R.id.mall_id);



            convertView.setTag(holder);


            //final DataModel temp = dataModelArrayList.get(position);
            // final  ItemModel itemModel = itemModelArrayList.get(position);
            holder.i_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    Intent intent = new Intent(context, temp_cart.class);
                    intent.putExtra("item_id", holder.i_des.getText().toString());
                    intent.putExtra("img", itemModelArrayList.get(position).getPicURL());
                    intent.putExtra("title", holder.i_title.getText().toString());
                    intent.putExtra("price", holder.i_price.getText().toString());
                    intent.putExtra("f_id", holder.i_f_id.getText().toString());
                    intent.putExtra("mall_id", holder.mall_id.getText().toString());



                    context.startActivity(intent);

    }
});



        }else {
        // the getTag returns the viewHolder object set as a tag to the view
        holder = (ListitmeAdapter.ViewHolder)convertView.getTag();
        }

        Picasso.get().load(itemModelArrayList.get(position).getPicURL()).into(holder.i_img);
        holder.i_title.setText(itemModelArrayList.get(position).getTitle());
        holder.i_price.setText("£"+itemModelArrayList.get(position).getPrice());
        holder.i_des.setText(itemModelArrayList.get(position).getDes());
       holder.mall_id.setText(itemModelArrayList.get(position).getMall_id());
        return convertView;

    }

    private class ViewHolder {


    protected TextView i_title, i_price, i_des, i_f_id, mall_id;
    private ImageView i_img;
    protected Button i_cart;




    }










}