package hes.projet.ticketme.data.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hes.projet.ticketme.data.entity.UserEntity;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<UserEntity> getById(Long id);

    @Query("SELECT * FROM users WHERE username = :username")
    LiveData<UserEntity> getByUsername(String username);

    @Query("SELECT * FROM users")
    LiveData<List<UserEntity>> getAll();

    @Delete
    void delete(UserEntity user);

    /**
     * An SQLiteConstraintException could be thrown if username already exists
     */
    @Insert
    void insert(UserEntity user);

    @Update
    void update(UserEntity user);

    @Query("delete from `users`")
    void deleteAll();
}
