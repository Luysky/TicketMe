package hes.projet.ticketme.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import hes.projet.ticketme.data.AppDatabase;
import hes.projet.ticketme.data.async.ticket.CreateTicket;
import hes.projet.ticketme.data.async.ticket.DeleteTicket;
import hes.projet.ticketme.data.async.ticket.UpdateTicket;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.util.OnAsyncEventListener;

public class TicketRepository {

    private static TicketRepository instance;

    /**
     * Private constructor for singleton
     */
    private TicketRepository() {}

    /**
     * Public method to get an instance of this repository
     *
     * @return TicketRepository singleton instance
     */
    public static TicketRepository getInstance() {
        if (instance == null) {
            synchronized (TicketRepository.class) {
                if (instance == null) {
                    instance = new TicketRepository();
                }
            }
        }
        return instance;
    }


    public LiveData<TicketEntity> getTicket(final Long id, Context context) {
        return AppDatabase.getInstance(context).ticketDao().getById(id);
    }

    public LiveData<List<TicketEntity>> getAllTickets(Context context) {
        return AppDatabase.getInstance(context).ticketDao().getAll();
    }

    public void insert(final TicketEntity ticket, OnAsyncEventListener callback, Context context) {
        new CreateTicket(context, callback).execute(ticket);
    }

    public void update(final TicketEntity ticket, OnAsyncEventListener callback, Context context) {
        new UpdateTicket(context, callback).execute(ticket);
    }

    public void delete(final TicketEntity ticket, OnAsyncEventListener callback, Context context) {
        new DeleteTicket(context, callback).execute(ticket);
    }
}
