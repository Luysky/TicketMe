package hes.projet.ticketme.data.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import hes.projet.ticketme.data.entity.CategoryEntity;
import hes.projet.ticketme.data.entity.TicketEntity;

@Dao
public interface CategoryDao {

    @Insert
    void insert(CategoryEntity category) throws SQLiteConstraintException;

    @Query("SELECT * FROM categories WHERE id = :id")
    LiveData<CategoryEntity> getById(Long id);

    @Query("SELECT * FROM categories")
    LiveData<List<CategoryEntity>> getAll();

    @Query("delete from `categories`")
    void deleteAll();
}
