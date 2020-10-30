package hes.projet.ticketme.data.async.ticket;

import android.content.Context;

import hes.projet.ticketme.data.async.HandleDbEntity;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.util.OnAsyncEventListener;

public class DeleteTicket extends HandleDbEntity<TicketEntity, Void, Void> {

    public DeleteTicket(Context context, OnAsyncEventListener callback) {
        super(context, callback);
    }

    @Override
    protected void doAaction(TicketEntity p) {
        getDatabase().ticketDao().delete(p);
    }
}
