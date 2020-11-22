package hes.projet.ticketme.data.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.data.entity.UserEntity;

public class TicketListLiveData  extends LiveData<List<TicketEntity>> {

    private static final String TAG = "TicketListLiveData";

    private int status;
    private final DatabaseReference reference;
    private final TicketListLiveData.MyValueEventListener listener = new TicketListLiveData.MyValueEventListener();


    public TicketListLiveData(DatabaseReference ref, int status) {
        reference = ref;
        this.status = status;
    }


    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toTicketList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<TicketEntity> toTicketList(DataSnapshot snapshot) {
        List<TicketEntity> tickets = new ArrayList<>();

        String uid = snapshot.getKey();
        Log.e(TAG, "Listing tickets for " + uid);

        for (DataSnapshot childSnapshot : snapshot.getChildren()) {

            TicketEntity entity = childSnapshot.getValue(TicketEntity.class);

            String ticketKey = childSnapshot.getKey();
            entity.setId(ticketKey);
            Log.e(TAG, "Found ticket " + ticketKey);

            entity.setUserId(uid);
//            entity.setId(childSnapshot.getKey());
            entity.setStatus(this.status);
            tickets.add(entity);
        }
        return tickets;
    }

}
