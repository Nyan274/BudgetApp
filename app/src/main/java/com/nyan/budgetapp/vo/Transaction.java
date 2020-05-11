package com.nyan.budgetapp.vo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Transaction implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int index;
    public long timeStamp;
    public double amount;
    public String currencyISO;
    @Embedded(prefix = "c_")
    public Category category;

    public Transaction() {

    }

    protected Transaction(Parcel in) {
        index = in.readInt();
        timeStamp = in.readLong();
        amount = in.readDouble();
        currencyISO = in.readString();
        category = in.readParcelable(Category.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeLong(timeStamp);
        dest.writeDouble(amount);
        dest.writeString(currencyISO);
        dest.writeParcelable(category, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
