package com.example.foodcourt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class smListAdapter extends BaseAdapter{


    private Context context;
    private ArrayList<smModel> smModelArrayList;

    public smListAdapter(Context context, ArrayList<smModel> smModelArrayList) {

        this.context = context;
        this.smModelArrayList = smModelArrayList;
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
        return smModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return smModelArrayList.get(position);
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
            convertView = inflater.inflate(R.layout.shopping_list, null, true);

            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.tvcountry = (TextView) convertView.findViewById(R.id.country);


            convertView.setTag(holder);

            //final DataModel temp = dataModelArrayList.get(position);





        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        Picasso.get().load(smModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.tvname.setText("Name: "+smModelArrayList.get(position).getName());
        holder.tvcountry.setText("Email: "+smModelArrayList.get(position).getCountry());


        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname, tvcountry, tvcity;
        protected ImageView iv;
    }

}


