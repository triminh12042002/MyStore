package com.example.mystore.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class StoreModel implements Parcelable {
    private String name;
    private String address;
    private String position;
    private String image;
    private List<Item> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    protected StoreModel(Parcel in) {
        name = in.readString();
        address = in.readString();
        position = in.readString();
        image = in.readString();
        items = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Creator<StoreModel> CREATOR = new Creator<StoreModel>() {
        @Override
        public StoreModel createFromParcel(Parcel in) {
            return new StoreModel(in);
        }

        @Override
        public StoreModel[] newArray(int size) {
            return new StoreModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(position);
        dest.writeString(image);
        dest.writeTypedList(items);
    }
}
