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

public class Admin_alluser_adapter extends BaseAdapter {

    private Context context;
    private ArrayList<DataModel> dataModelArrayList;

    public Admin_alluser_adapter(Context context, ArrayList<DataModel> dataModelArrayList) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
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
        Admin_alluser_adapter.ViewHolder holder;

        if (convertView == null) {
            holder = new  Admin_alluser_adapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_news, null, true);

            holder.u_id = (TextView) convertView.findViewById(R.id.u_id);
            holder.u_name = (TextView) convertView.findViewById(R.id.name);
            holder.u_email= (TextView) convertView.findViewById(R.id.email);
            holder.u_status= (TextView) convertView.findViewById(R.id.status);
            holder.u_otp= (TextView) convertView.findViewById(R.id.otp);



            convertView.setTag(holder);

            //final DataModel temp = dataModelArrayList.get(position);





        }else {
            // the getTag returns the viewHolder object set as a tag to the view

            holder = (Admin_alluser_adapter.ViewHolder)convertView.getTag();
        }


        holder.u_id.setText(dataModelArrayList.get(position).getId());
        holder.u_name.setText(dataModelArrayList.get(position).getU_name());
        holder.u_status.setText(dataModelArrayList.get(position).getU_status());
        holder.u_email.setText(dataModelArrayList.get(position).getU_email());
        holder.u_otp.setText(dataModelArrayList.get(position).getU_otp());
        return convertView;
    }

    private class ViewHolder {

        protected TextView u_id, u_name, u_status, u_otp, u_email;

    }

}


