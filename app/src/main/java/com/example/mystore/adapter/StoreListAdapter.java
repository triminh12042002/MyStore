package com.example.mystore.adapter;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mystore.R;
import com.example.mystore.model.StoreModel;

import java.io.File;
import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.MyViewHoler> {

    private List<StoreModel> storeModelList;
    private StoreModelListClickListener clickListener;

    public StoreListAdapter(List<StoreModel> storeModelList, StoreModelListClickListener clickListener) {
        this.storeModelList = storeModelList;
        this.clickListener = clickListener;
    }

    public void updateData(List<StoreModel> storeModelList) {
        this.storeModelList = storeModelList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public StoreListAdapter.MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, @SuppressLint("RecyclerView") int position) {
//        int position = holder.getAdapterPosition();
        holder.storeName.setText( storeModelList.get(position).getName());
        holder.storeAddress.setText(storeModelList.get(position).getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(storeModelList.get(position));
            }
        });

        Glide.with(holder.thumbImage)
                .load(storeModelList.get(position).getImage())
                .into(holder.thumbImage);


    }

    @Override
    public int getItemCount() {
        return storeModelList.size();
    }

    static class MyViewHoler extends RecyclerView.ViewHolder{
        TextView storeName;
        TextView storeAddress;
        ImageView thumbImage;

        public MyViewHoler(View view) {
            super(view);
            storeName = view.findViewById(R.id.storeName);
            storeAddress = view.findViewById(R.id.storeAddress);
            thumbImage = view.findViewById(R.id.thumbImage);
        }
    }

    public interface StoreModelListClickListener{
        public void onItemClick(StoreModel storeModel);
    }
}
