package hes.projet.ticketme.data.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import hes.projet.ticketme.data.entity.TicketEntity;

@Dao
public abstract class TicketDao {

    @Query("SELECT * FROM tickets WHERE id = :id")
    public abstract LiveData<TicketEntity> getById(Long id);

    @Delete
    public abstract void delete(TicketEntity ticket);


}
