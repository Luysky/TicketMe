package hes.projet.ticketme.data.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.data.entity.UserEntity;

public class TicketLiveData extends LiveData<TicketEntity> {


    private static final String TAG = "TicketLiveData";

    private final DatabaseReference reference;
    private final TicketLiveData.MyValueEventListener listener = new TicketLiveData.MyValueEventListener();

    public TicketLiveData(DatabaseReference ref) {
        this.reference = ref;
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
            if (dataSnapshot.exists()) {
                TicketEntity entity = dataSnapshot.getValue(TicketEntity.class);
                entity.setId(dataSnapshot.getKey());
                setValue(entity);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }


}
