package hes.projet.ticketme.data.repository;

import android.content.Context;

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


    public LiveData<TicketEntity> getTicket(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("tickets").child(id);

        return new TicketLiveData(reference);
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

        boolean admin = userId == null;

        if (admin) {
            reference = FirebaseDatabase.getInstance()
                    .getReference(statusString + "_tickets");

            return new TicketListAdminLiveData(reference, status);
        }

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
