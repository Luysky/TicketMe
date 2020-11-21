package hes.projet.ticketme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import hes.projet.ticketme.BaseApp;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.data.repository.UserRepository;
import hes.projet.ticketme.util.OnAsyncEventListener;

public class UserListViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<UserEntity>> observableUsers;

    public UserListViewModel(@NonNull Application application,
                             final String userId,UserRepository userRepository) {
        super(application);

        mRepository = userRepository;

        observableUsers = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableUsers.setValue(null);

        LiveData<List<UserEntity>> users = mRepository.getAllUsers();

        // observe the changes of the entities from the database and forward them
        observableUsers.addSource(users, observableUsers::setValue);
    }


    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;
        private final String mUserId;
        private final UserRepository mRepository;

        public Factory(@NonNull Application application,String userId) {
            mApplication = application;
            mUserId = userId;
            mRepository = ((BaseApp)application).getUserRepository();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserListViewModel(mApplication, mUserId, mRepository);
        }
    }

    /**
     * Expose the LiveData UserEntities query so the UI can observe it.
     */
    public LiveData<List<UserEntity>> getUsers() {
        return observableUsers;
    }

    public void createUser(UserEntity user, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getUserRepository()
                .insert(user, callback);
    }

    public void updateUser(UserEntity user, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getUserRepository()
                .update(user, callback);
    }

    public void deleteUser(UserEntity user, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getUserRepository()
                .delete(user, callback);
    }
}
