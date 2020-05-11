package com.nyan.budgetapp.vo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String name;
    public String currencyISO;
    public double monthlyBudget;
    public int colorHex;


    public Category() {

    }

    protected Category(Parcel in) {
        _id = in.readInt();
        name = in.readString();
        currencyISO = in.readString();
        monthlyBudget = in.readDouble();
        colorHex = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(name);
        dest.writeString(currencyISO);
        dest.writeDouble(monthlyBudget);
        dest.writeInt(colorHex);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
