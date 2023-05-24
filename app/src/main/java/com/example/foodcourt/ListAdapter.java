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

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DataModel> dataModelArrayList;
    private int activityId;

    public ListAdapter(Context context, ArrayList<DataModel> dataModelArrayList,int activityId) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
        this.activityId = activityId;
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
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModelArrayList.get(position);
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
            convertView = inflater.inflate(R.layout.lv_player, null, true);

            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.tvcountry = (TextView) convertView.findViewById(R.id.country);
            holder.tvcity = (TextView) convertView.findViewById(R.id.city);

          DataModel temp = dataModelArrayList.get(position);


            convertView.setTag(holder);

            //final DataModel temp = dataModelArrayList.get(position);





        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        Picasso.get().load(dataModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.tvname.setText("Name: "+dataModelArrayList.get(position).getName());

        holder.tvcity.setText(dataModelArrayList.get(position).getCity());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityId == 1) {
                    User user = new User(context);
                    user.setF_id(dataModelArrayList.get(position).getCity());

                    Intent i = new Intent(context, Items_page.class);
                    i.putExtra("mall_id", user.getMall_id());
                    context.startActivity(i);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname, tvcountry, tvcity;
        protected ImageView iv;
    }

}


