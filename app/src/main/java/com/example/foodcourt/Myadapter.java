package com.example.foodcourt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Myadapter extends RecyclerView.Adapter<ImageViewHolder>{

  private Context context;

    public Myadapter(Context context, List<ALL_Malls> mall_list) {
        this.context = context;
        this.mall_list = mall_list;
    }

    private List<ALL_Malls> mall_list;


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_adapter, parent, false);


        return new ImageViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Glide.with(context).load(mall_list.get(position).getImageurl()).into(holder.imageView);
        ALL_Malls all_malls = mall_list.get(position);
        holder.bind(all_malls);

        final ALL_Malls temp = mall_list.get(position);
        holder.mainLO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(context);
                user.setMall_id(temp.getId());
              Intent intent = new Intent(context, food_courts_all.class);
              intent.putExtra("mall", temp.getId());
              intent.putExtra("mall_name", temp.getName());
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              context.startActivity(intent);
 /*
                Bundle bundle = new Bundle();
                bundle.putString("mall", temp.getId());
                // set Fragmentclass Arguments
                foodcourt_all fragobj = new foodcourt_all();
                fragobj.setArguments(bundle);
*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return mall_list.size();
    }
}

class ImageViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    LinearLayout mainLO;
    TextView name;
    TextView id;
    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img_mall);
        name = itemView.findViewById(R.id.mall_name);
        id = itemView.findViewById(R.id.rating);
        mainLO = itemView.findViewById(R.id.mainLO);

    }
    public void bind(ALL_Malls all_malls){

        name.setText(all_malls.getName());
        id.setText(all_malls.getId());
}

}




