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

import hes.projet.ticketme.data.entity.UserEntity;

public class UserListLiveData extends LiveData<List<UserEntity>> {

    private static final String TAG = "UserListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();


    public UserListLiveData(DatabaseReference ref) {
        reference = ref;
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
            setValue(toUserList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<UserEntity> toUserList(DataSnapshot snapshot) {
        List<UserEntity> users = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {

            UserEntity entity = childSnapshot.getValue(UserEntity.class);
            Log.e(TAG, "user id " + childSnapshot.getKey());
            entity.setId(childSnapshot.getKey());
            users.add(entity);
        }
        return users;
    }

}
