package hes.projet.ticketme.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hes.projet.ticketme.data.entity.TicketEntity;


@Dao
public interface TicketDao {

    @Query("SELECT * FROM tickets WHERE id = :id")
    LiveData<TicketEntity> getById(Long id);

    @Query("SELECT * FROM tickets")
    LiveData<List<TicketEntity>> getAll();

    @Query("SELECT * FROM tickets WHERE status = :status")
    LiveData<List<TicketEntity>> getAllByStatus(int status);

    @Query("SELECT * FROM tickets WHERE user = :userId AND status = :status")
    LiveData<List<TicketEntity>> getAllOfUserByStatus(long userId, int status);

    @Query("SELECT * FROM tickets WHERE category = :categoryId AND status = :status")
    LiveData<List<TicketEntity>> getAllInCategoryByStatus(long categoryId, int status);

    @Query("SELECT * FROM tickets WHERE user = :userId AND category = :categoryId AND status = :status")
    LiveData<List<TicketEntity>> getAllOfUserInCategoryByStatus(long userId, long categoryId, int status);

    @Delete
    void delete(TicketEntity ticket);

    @Update
    void update(TicketEntity ticket);

    @Insert
    void insert(TicketEntity ticket);


    @Query("delete from `tickets`")
    void deleteAll();
}
