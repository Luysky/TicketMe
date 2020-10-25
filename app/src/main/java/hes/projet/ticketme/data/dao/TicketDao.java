package hes.projet.ticketme.data.dao;
import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import hes.projet.ticketme.data.entity.TicketEntity;

@Dao
public interface TicketDao {

    @Query("SELECT * FROM tickets WHERE id = :id")
    LiveData<TicketEntity> getById(Long id);

    @Delete
    void delete(TicketEntity ticket);

    /**
     * TODO Clarify why using SQLSQLiteConstraintException
     *
     * @param ticket
     * @throws SQLiteConstraintException
     */
    @Insert
    void insert(TicketEntity ticket) throws SQLiteConstraintException;


    @Query("delete from `tickets`")
    void deleteAll();
}
