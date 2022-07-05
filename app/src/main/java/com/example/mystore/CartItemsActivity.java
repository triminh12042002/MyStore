package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mystore.adapter.CartItemsAdapter;
import com.example.mystore.model.Item;
import com.example.mystore.model.StoreModel;

public class CartItemsActivity extends AppCompatActivity {
    private RecyclerView cartItemsRecycleView;
    private CartItemsAdapter cartItemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_items);

        StoreModel storeModel = getIntent().getParcelableExtra("StoreModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(storeModel.getName());
        actionBar.setSubtitle(storeModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView cartTitle = findViewById(R.id.cartTitle);
        cartTitle.setText("Total: " + storeModel.getItems().size() + " items,  $" + calcTotalPrice(storeModel));
        initRecyclerView(storeModel);
    }

    private void initRecyclerView(StoreModel storeModel) {
        cartItemsRecycleView = findViewById(R.id.cartItemsRecycleView);
        cartItemsRecycleView.setLayoutManager(new LinearLayoutManager(this));
        cartItemsAdapter = new CartItemsAdapter(storeModel.getItems());
        cartItemsRecycleView.setAdapter(cartItemsAdapter);
    }

    private float calcTotalPrice(StoreModel storeModel){
        float total = 0;
        for(Item i : storeModel.getItems()){
            total += i.getNumInCart()*i.getPrice();
        }
        return total;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}