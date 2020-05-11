package com.nyan.budgetapp.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nyan.budgetapp.vo.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {

    @Query("Select * from `Transaction`")
    List<Transaction> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Transaction category);

    @Delete
    void delete(Transaction category);
}
