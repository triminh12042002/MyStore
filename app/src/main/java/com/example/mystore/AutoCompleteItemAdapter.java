package com.example.mystore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mystore.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AutoCompleteItemAdapter extends ArrayAdapter <Item> {

    private List<Item> ItemListFull;

    public AutoCompleteItemAdapter(@NonNull Context context, @NonNull List<Item> ItemList) {
        super(context, 0, ItemList);
        ItemListFull = new ArrayList<>(ItemList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_autocomplete_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.text_view_name);
        //Image imageViewFlag = convertView.findViewById(R.id.image_view_flag);

        Item item = getItem(position);

        if (item != null) {
            textViewName.setText(item.getName());
            //imageView.setImageResource(item.geImage());
        }

        return convertView;
    }

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Item> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(ItemListFull);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Item item : ItemListFull) {
                    if (item.getName().toLowerCase(Locale.ROOT).contains(filterPattern)){
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Item) resultValue).getName();
        }
    };
}
