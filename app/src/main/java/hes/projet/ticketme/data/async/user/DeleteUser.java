package hes.projet.ticketme.data.async.user;

import android.content.Context;

import hes.projet.ticketme.data.async.HandleDbEntity;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.util.OnAsyncEventListener;

public class DeleteUser extends HandleDbEntity<UserEntity, Void, Void> {

    public DeleteUser(Context context, OnAsyncEventListener callback) {
        super(context, callback);
    }

    @Override
    protected void doAaction(UserEntity u) {
        getDatabase().userDao().delete(u);
    }
}
