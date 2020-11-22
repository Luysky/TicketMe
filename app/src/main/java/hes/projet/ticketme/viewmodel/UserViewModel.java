package hes.projet.ticketme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;


import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.data.repository.UserRepository;
import hes.projet.ticketme.util.OnAsyncEventListener;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<UserEntity> observableUser;

    public UserViewModel(@NonNull Application application,
                         final String id, UserRepository userRepository) {
        super(application);

        mRepository = userRepository;

        observableUser = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableUser.setValue(null);

        if(id != null){
            LiveData<UserEntity> user = mRepository.getUser(id);

            // observe the changes of the entities from the database and forward them
            observableUser.addSource(user, observableUser::setValue);
        }

    }


    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;
        private final String mUserId;
        private final UserRepository mRepository;


        public Factory(@NonNull Application application, String userId) {
            mApplication = application;
            mUserId = userId;
            mRepository = UserRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(mApplication, mUserId, mRepository);
        }
    }

    /**
     * Expose the LiveData UserEntities query so the UI can observe it.
     */
    public LiveData<UserEntity> getUser() {
        return observableUser;
    }

    public void createUser(UserEntity user, OnAsyncEventListener callback) {
        UserRepository.getInstance()
                .insert(user, callback);
    }

    public void updateUser(UserEntity user, OnAsyncEventListener callback) {
        UserRepository.getInstance()
                .update(user, callback);
    }

    public void deleteUser(UserEntity user, OnAsyncEventListener callback) {
        UserRepository.getInstance()
                .delete(user, callback);
    }
}
