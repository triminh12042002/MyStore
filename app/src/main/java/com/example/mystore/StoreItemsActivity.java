package com.example.mystore;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mystore.adapter.ItemListAdapter;
import com.example.mystore.model.Item;
import com.example.mystore.model.StoreModel;

import java.util.List;

public class StoreItemsActivity extends AppCompatActivity implements ItemListAdapter.ItemListClickListener{
    private List<Item> itemList = null;
    private ItemListAdapter itemListAdapter;
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

        TextView buttonCheckout = findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    }
}