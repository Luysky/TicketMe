package hes.projet.ticketme.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.data.firebase.TicketListAdminLiveData;
import hes.projet.ticketme.data.firebase.TicketListLiveData;
import hes.projet.ticketme.data.firebase.TicketLiveData;
import hes.projet.ticketme.util.OnAsyncEventListener;

public class TicketRepository {

    private static TicketRepository instance;
    private static final String TAG = "TicketRepository";

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


    public LiveData<TicketEntity> getTicket(final String id, String ticketUid, int ticketStatus) {
        String statusString = ticketStatus == 0 ? "open" : "closed";

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(statusString + "_tickets")
                .child(ticketUid)
                .child(id);

        return new TicketLiveData(reference, ticketUid, ticketStatus);
    }


    /**
     * TODO remove categoryId
     * @param userId
     * @param status
     * * @return
     */
    public LiveData<List<TicketEntity>> getAllTickets(String userId, int status) {

        DatabaseReference reference;
        String statusString = status == 0 ? "open" : "closed";
        Log.i(TAG, "getAllTickets");

        boolean admin = userId == null;

        if (admin) {
            Log.i(TAG, " for admin");
            reference = FirebaseDatabase.getInstance()
                    .getReference(statusString + "_tickets");

            return new TicketListAdminLiveData(reference, status);
        }

        Log.i(TAG, " " + statusString + " for normal user: " + userId);
        reference = FirebaseDatabase.getInstance()
                .getReference(statusString + "_tickets").child(userId);

        return new TicketListLiveData(reference, status);

    }



    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
    public void insert(final TicketEntity ticket, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance()
                .getReference("open_tickets").child(ticket.getUserId()).push().getKey();

        FirebaseDatabase.getInstance()
                .getReference("open_tickets")
                .child(ticket.getUserId())
                .child(id)
                .setValue(ticket, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


    public void update(final TicketEntity ticket, final OnAsyncEventListener callback) {

        String statusString = ticket.getStatus() == 0 ? "open" : "closed";

        FirebaseDatabase.getInstance()
                .getReference(statusString + "_tickets")
                .child(ticket.getUserId())
                .child(ticket.getId())
                .updateChildren(ticket.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final TicketEntity ticket, OnAsyncEventListener callback) {

        String statusString = ticket.getStatus() == 0 ? "open" : "closed";

        FirebaseDatabase.getInstance()
                .getReference(statusString + "_tickets")
                .child(ticket.getUserId())
                .child(ticket.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
