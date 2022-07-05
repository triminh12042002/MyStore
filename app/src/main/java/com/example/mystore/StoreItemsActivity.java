package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystore.adapter.ItemListAdapter;
import com.example.mystore.model.Item;
import com.example.mystore.model.StoreModel;

import java.util.ArrayList;
import java.util.List;

public class StoreItemsActivity extends AppCompatActivity implements ItemListAdapter.ItemListClickListener{
    private List<Item> itemList = null;
    private ItemListAdapter itemListAdapter;
    private List<Item> itemInCartList = null;
    private int totalItemInCart = 0;
    private TextView viewCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_items);

        StoreModel storeModel = getIntent().getParcelableExtra("StoreModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(storeModel.getName());
        actionBar.setSubtitle(storeModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);

        itemList = storeModel.getItems();
        initRecyclerView();

        viewCartButton = findViewById(R.id.viewCartButton);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemInCartList == null || itemInCartList.size() <= 0){
                    Toast.makeText(StoreItemsActivity.this, "please add some items to cart", Toast.LENGTH_LONG);
                    return;
                }
                storeModel.setItems(itemInCartList);
                Intent i = new Intent(StoreItemsActivity.this, CartItemsActivity.class);
                i.putExtra("StoreModel", storeModel);
                startActivityForResult(i, 1000);
                Log.v("startActivity:", "startActivity");

            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        itemListAdapter = new ItemListAdapter(itemList, this);
        recyclerView.setAdapter(itemListAdapter);
    }

    @Override
    public void onAddToCartClick(Item item) {
        if(itemInCartList == null){
            itemInCartList = new ArrayList<>();
        }
        itemInCartList.add(item);
        totalItemInCart = 0;

        for(Item i : itemInCartList){
            totalItemInCart += i.getNumInCart();
        }

        viewCartButton.setText("View cart (" + totalItemInCart + " items)");
    }

    @Override
    public void onRemoveFromCartClick(Item item) {
        if(itemInCartList.contains(item)) {
            itemInCartList.remove(item);
            totalItemInCart = 0;

            for(Item i : itemInCartList){
                totalItemInCart += i.getNumInCart();
            }

            viewCartButton.setText("View cart (" + totalItemInCart + " items)");
        }

    }

    @Override
    public void onUpdateCartClick(Item item) {
        if(itemInCartList.contains(item)){
            int index = itemInCartList.indexOf(item);
            itemInCartList.remove(index);
            itemInCartList.add(index, item);

            totalItemInCart = 0;

            for(Item i : itemInCartList){
                totalItemInCart += i.getNumInCart();
            }

            viewCartButton.setText("View cart (" + totalItemInCart + " items)");
        }
    }

    @Override
    public void onViewItemDetail(Item item) {
        Intent itemIntent = new Intent(StoreItemsActivity.this, ItemActivity.class);
        itemIntent.putExtra("Item", item);
        startActivityForResult(itemIntent, 1000);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                // do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK){

            finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}