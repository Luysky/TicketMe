package hes.projet.ticketme.data.dao;
import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.data.entity.UserEntity;


/**
 * TODO Find a way to bind foreign keys
 */


@Dao
public interface TicketDao {

    @Query("SELECT * FROM tickets WHERE id = :id")
    LiveData<TicketEntity> getById(Long id);

    @Query("SELECT * FROM tickets")
    LiveData<List<TicketEntity>> getAll();

    @Delete
    void delete(TicketEntity ticket);

    @Update
    void update(TicketEntity ticket);

    @Insert
    void insert(TicketEntity ticket);


    @Query("delete from `tickets`")
    void deleteAll();
}
