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

public class TicketListAdminLiveData extends LiveData<List<TicketEntity>> {

    private static final String TAG = "TicketListLiveData";

    private final DatabaseReference reference;
    private final TicketListAdminLiveData.MyValueEventListener listener = new TicketListAdminLiveData.MyValueEventListener();


    public TicketListAdminLiveData(DatabaseReference ref, int status) {
        reference = ref;
        this.status = status;
    }

    private int status;

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

        for (DataSnapshot uidSnapshot : snapshot.getChildren()) {
            for (DataSnapshot childSnapshot : uidSnapshot.getChildren()) {
                TicketEntity entity = childSnapshot.getValue(TicketEntity.class);
                entity.setUserId(uidSnapshot.getKey());
                entity.setId(childSnapshot.getKey());
                entity.setStatus(this.status);
                tickets.add(entity);
            }

        }
        return tickets;
    }

}
