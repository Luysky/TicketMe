package hes.projet.ticketme.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import hes.projet.ticketme.data.AppDatabase;
import hes.projet.ticketme.data.async.ticket.CreateTicket;
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

    public void insert(final TicketEntity client, OnAsyncEventListener callback, Context context) {
        new CreateTicket(context, callback).execute(client);
    }
/*
    public void update(final ClientEntity client, OnAsyncEventListener callback, Context context) {
        new UpdateClient(context, callback).execute(client);
    }

    public void delete(final ClientEntity client, OnAsyncEventListener callback, Context context) {
        new DeleteClient(context, callback).execute(client);
    }*/
}
