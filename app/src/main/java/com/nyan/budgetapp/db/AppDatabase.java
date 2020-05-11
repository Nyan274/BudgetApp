package com.nyan.budgetapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nyan.budgetapp.db.daos.CategoryDao;
import com.nyan.budgetapp.db.daos.TransactionDao;
import com.nyan.budgetapp.vo.Category;
import com.nyan.budgetapp.vo.Transaction;

@Database(entities = {Transaction.class, Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "AppDatabase.db";

    public abstract CategoryDao categoryDao();

    public abstract TransactionDao transactionDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(final Context context) {
        synchronized (AppDatabase.class) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return instance;
    }
}
