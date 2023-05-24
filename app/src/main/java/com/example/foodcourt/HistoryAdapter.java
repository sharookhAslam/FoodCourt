package com.example.foodcourt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HistoryModel> historyModelArrayList;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> historyModelArrayList) {

        this.context = context;
        this.historyModelArrayList = historyModelArrayList;
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
        return historyModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_history, null, true);

            holder.picURL = (ImageView) convertView.findViewById(R.id.iv);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.qty = (TextView) convertView.findViewById(R.id.qty);
            holder.food_court = (TextView) convertView.findViewById(R.id.from);
            holder.sm = (TextView) convertView.findViewById(R.id.at);

            convertView.setTag(holder);

            //final DataModel temp = dataModelArrayList.get(position);





        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        Picasso.get().load(historyModelArrayList.get(position).getPicURL()).into(holder.picURL);
        holder.title.setText("Name: "+historyModelArrayList.get(position).getTitle());
        holder.price.setText("Price: "+historyModelArrayList.get(position).getPrice());
        holder.qty.setText("Qty: "+historyModelArrayList.get(position).getQty());
        holder.food_court.setText("From: "+historyModelArrayList.get(position).getFood_court());
        holder.sm.setText("At: "+historyModelArrayList.get(position).getSm());

        return convertView;
    }

    private class ViewHolder {

        protected TextView title, price, qty, food_court, sm, tm;
        protected ImageView picURL;



    }

}


