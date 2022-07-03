package com.example.mystore.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mystore.R;
import com.example.mystore.model.Item;

import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.MyViewHoler> {

    private List<Item> itemList;

    public CartItemsAdapter(List<Item> itemList) {
        this.itemList = itemList;
        Log.v("cartItemList:", "cartItemList" + itemList.size());
    }

    public void updateData(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartItemsAdapter.MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_recycler_row, parent, false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, @SuppressLint("RecyclerView") int position) {
        holder.itemName.setText( itemList.get(position).getName());
        holder.itemPrice.setText("Price: " + itemList.get(position).getPrice());
        holder.itemNum.setText("Quantity: " + itemList.get(position).getNumInCart());


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
        TextView itemNum;
        ImageView thumbImage;


        public MyViewHoler(View view) {
            super(view);
            itemName = view.findViewById(R.id.itemName);
            itemPrice = view.findViewById(R.id.itemPrice);
            thumbImage = view.findViewById(R.id.thumbImage);
            itemNum = view.findViewById(R.id.itemNum);
        }
    }

}
