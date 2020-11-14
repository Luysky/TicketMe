package hes.projet.ticketme.data.async.ticket;

import android.content.Context;
import android.os.AsyncTask;

import hes.projet.ticketme.data.AppDatabase;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.util.OnAsyncEventListener;

public class UpdateTicket extends AsyncTask<TicketEntity, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateTicket(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }


    @Override
    protected Void doInBackground(TicketEntity... params) {
        try {
            for (TicketEntity ticket : params)
                database.ticketDao().update(ticket);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }


    /**
     * Triggered after async task has completed
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
