package hes.projet.ticketme.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import hes.projet.ticketme.data.AppDatabase;
import hes.projet.ticketme.data.async.user.CreateUser;
import hes.projet.ticketme.data.async.user.DeleteUser;
import hes.projet.ticketme.data.async.user.UpdateUser;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.util.OnAsyncEventListener;

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


    public LiveData<UserEntity> getUser(final Long id, Context context) {
        return AppDatabase.getInstance(context).userDao().getById(id);
    }

    public LiveData<UserEntity> getUserByUsername(final String username, Context context) {
        return AppDatabase.getInstance(context).userDao().getByUsername(username);
    }

    public LiveData<List<UserEntity>> getAllUsers(Context context) {
        return AppDatabase.getInstance(context).userDao().getAll();
    }

    public void insert(final UserEntity userEntity, OnAsyncEventListener callback, Context context) {
        new CreateUser(context, callback).execute(userEntity);
    }

    public void update(final UserEntity user, OnAsyncEventListener callback, Context context) {
        new UpdateUser(context, callback).execute(user);
    }

    public void delete(final UserEntity user, OnAsyncEventListener callback, Context context) {
        new DeleteUser(context, callback).execute(user);
    }
}
