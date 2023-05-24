package com.example.foodcourt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class paymentAdapter extends BaseAdapter {


    private int counter = 0;

    private Context context;
    private ArrayList<fcorderModel> itemModelArrayList;


    public paymentAdapter(Context context, ArrayList<fcorderModel> itemModelArrayList) {

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
        paymentAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new paymentAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_payment, null, true);



            holder.payment_method = (TextView) convertView.findViewById(R.id.payment_method);
            holder.u_name = (TextView) convertView.findViewById(R.id.u_Name);

            holder.u_phone = (TextView) convertView.findViewById(R.id.u_phone);



            convertView.setTag(holder);


            //final DataModel temp = dataModelArrayList.get(position);
            // final  ItemModel itemModel = itemModelArrayList.get(position);


        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (paymentAdapter.ViewHolder) convertView.getTag();
        }



        holder.u_name.setText("User name: " + itemModelArrayList.get(position).getU_name());
        holder.u_phone.setText("User no: " + itemModelArrayList.get(position).getPhone());
        holder.payment_method.setText("Payment: " + itemModelArrayList.get(position).getPayment_method());

        return convertView;

    }

    private class ViewHolder {


        protected TextView i_title, i_price, qty, u_phone, u_name, payment_method, time;


    }


}








