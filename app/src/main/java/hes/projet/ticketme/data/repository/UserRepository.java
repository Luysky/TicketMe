package hes.projet.ticketme.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.data.firebase.UserListLiveData;
import hes.projet.ticketme.data.firebase.UserLiveData;
import hes.projet.ticketme.util.OnAsyncEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRepository {

    private static UserRepository instance;



    /**
     * Private constructor for singleton
     */
    private UserRepository() {}

    /**
     * Public method to get an instance of this repository
     *
     * @return TicketRepository singleton instance
     */
    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }


    public LiveData<UserEntity> getUser(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users").child(id);

        return new UserLiveData(reference);
    }


    /* public LiveData<UserEntity> getUserByUsername(final String username) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users");

        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(id)
                .setValue(user, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        return null;
    } */



    public LiveData<List<UserEntity>> getAllUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users");

        return new UserListLiveData(reference);
    }


    // Firebase Database paths must not contain '.', '#', '$', '[', or ']'
    public void insert(final UserEntity user, final OnAsyncEventListener callback) {
        // String id = FirebaseDatabase.getInstance().getReference("users").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getId())
                .setValue(user, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


    public void update(final UserEntity user, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getId())
                .updateChildren(user.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final UserEntity user, OnAsyncEventListener callback) {
        user.setActive(false);
        update(user, callback);
    }

}
