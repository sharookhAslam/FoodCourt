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




public class  orderfcAdapter  extends BaseAdapter {

    private int counter = 0;

    private Context context;
    private ArrayList<fcorderModel> itemModelArrayList;


    public  orderfcAdapter (Context context, ArrayList<fcorderModel> itemModelArrayList) {

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
        orderfcAdapter .ViewHolder holder;

        if (convertView == null) {
            holder = new  orderfcAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_fc_order, null, true);


            holder.i_title = (TextView) convertView.findViewById(R.id.title);
            holder.i_price = (TextView) convertView.findViewById(R.id.price);
            holder.orderId = (TextView) convertView.findViewById(R.id.order_id);

            holder.qty = (TextView) convertView.findViewById(R.id.qty);

            holder.time= (TextView) convertView.findViewById(R.id.time);


            convertView.setTag(holder);


            //final DataModel temp = dataModelArrayList.get(position);
            // final  ItemModel itemModel = itemModelArrayList.get(position);




        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (orderfcAdapter .ViewHolder)convertView.getTag();
        }


        holder.i_title.setText(itemModelArrayList.get(position).getTitle());
        holder.i_price.setText("Price: "+itemModelArrayList.get(position).getPrice());
        holder.qty.setText("Qty: "+itemModelArrayList.get(position).getQty());

        holder.orderId.setText("Id: "+itemModelArrayList.get(position).getOrder_id());
        holder.time.setText("Date: "+itemModelArrayList.get(position).getDate());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(context);
                user.setOrder_id(itemModelArrayList.get(position).getPayment_method());

                Intent i = new Intent(context, fc_payemnt.class);

                context.startActivity(i);
            }
        });



        return convertView;

    }

    private class ViewHolder {


        protected TextView i_title, i_price, qty,u_phone, u_name, orderId, time;






    }










}