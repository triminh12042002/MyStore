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
import com.example.mystore.model.Item;
import com.example.mystore.model.StoreModel;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHoler> {

    private List<Item> itemList;
    private ItemListClickListener clickListener;

    public ItemListAdapter(List<Item> itemList, ItemListClickListener clickListener) {
        this.itemList = itemList;
        this.clickListener = clickListener;
    }

    public void updateData(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemListAdapter.MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_row, parent, false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, @SuppressLint("RecyclerView") int position) {
        holder.itemName.setText( itemList.get(position).getName());
        holder.itemPrice.setText("Price: " + itemList.get(position).getPrice());
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onAddToCartClick(itemList.get(position));
            }
        });

        Glide.with(holder.thumbImage)
                .load(itemList.get(position).getUrl())
                .into(holder.thumbImage);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class MyViewHoler extends RecyclerView.ViewHolder{
        TextView itemName;
        TextView itemPrice;
        TextView addToCartButton;
        ImageView thumbImage;

        public MyViewHoler(View view) {
            super(view);
            itemName = view.findViewById(R.id.itemName);
            itemPrice = view.findViewById(R.id.itemPrice);
            addToCartButton = view.findViewById(R.id.addToCartButton);
            thumbImage = view.findViewById(R.id.thumbImage);
        }
    }

    public interface ItemListClickListener{
        public void onAddToCartClick(Item item);
    }
}
