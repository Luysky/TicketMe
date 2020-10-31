package hes.projet.ticketme.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;


import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.data.repository.TicketRepository;
import hes.projet.ticketme.data.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<UserEntity> observableUser;

    public UserViewModel(@NonNull Application application, final Long id, UserRepository userRepository) {
        super(application);

        Context applicationContext = application.getApplicationContext();

        observableUser = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableUser.setValue(null);

        LiveData<UserEntity> user = userRepository.getUser(id, applicationContext);

        // observe the changes of the entities from the database and forward them
        observableUser.addSource(user, observableUser::setValue);
    }


    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final UserRepository userRepository;

        private final Long userId;

        public Factory(@NonNull Application application, Long userId) {
            this.application = application;
            this.userId = userId;
            userRepository = UserRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(application, userId, userRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<UserEntity> getUser() {
        return observableUser;
    }

}
