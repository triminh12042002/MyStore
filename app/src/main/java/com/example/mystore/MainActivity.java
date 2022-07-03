package com.example.mystore;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mystore.adapter.StoreListAdapter;
import com.example.mystore.model.Item;
import com.example.mystore.model.StoreModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements StoreListAdapter.StoreModelListClickListener {

    private List<Item> ItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Store List");

        List<StoreModel> storeModelList = getStoreData();

        initRecycleView(storeModelList);

        fillItemList(storeModelList);

        AutoCompleteTextView editText = findViewById(R.id.autoComplete);
        AutoCompleteItemAdapter adapter = new AutoCompleteItemAdapter(this, ItemList);
        editText.setAdapter(adapter);

        TextView viewMapButton = findViewById(R.id.viewMapButton);
        viewMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
                ArrayList<StoreModel> storeModelArrayList = new ArrayList<>(storeModelList);
                mapIntent.putParcelableArrayListExtra("StoreModelList", storeModelArrayList);
                startActivityForResult(mapIntent, 1000);
            }
        });
    }

    private void fillItemList(List<StoreModel> storeModelList) {
        ItemList = new ArrayList<>();
        for (int i = 0; i < storeModelList.size(); i++)
        {
            for (int j = 0; j < storeModelList.get(i).getItems().size(); j++)
            {
                ItemList.add(storeModelList.get(i).getItems().get(j));
            }
        }
    }

    private void initRecycleView(List<StoreModel> storeModelList) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StoreListAdapter adapter = new StoreListAdapter(storeModelList, this);
        recyclerView.setAdapter(adapter);

    }

    private List<StoreModel> getStoreData() {
//        InputStream is = getResources().openRawResource(R.raw.store);
        // write from file to list of store
        String jsonString = "";
        try {
            InputStream is = getResources().openRawResource(R.raw.store);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        StoreModel[] storeModels = gson.fromJson(jsonString, StoreModel[].class);
        List<StoreModel> storeModelList = Arrays.asList(storeModels);
        return storeModelList;
    }

    @Override
    public void onItemClick(StoreModel storeModel) {
        Intent intent = new Intent(MainActivity.this, StoreItemsActivity.class);
        intent.putExtra("StoreModel",storeModel);
        startActivity(intent);
    }

}
