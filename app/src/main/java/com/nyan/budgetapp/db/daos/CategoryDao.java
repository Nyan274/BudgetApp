package com.nyan.budgetapp.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nyan.budgetapp.vo.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("Select * from Category")
    List<Category> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Delete
    void delete(Category category);

}
