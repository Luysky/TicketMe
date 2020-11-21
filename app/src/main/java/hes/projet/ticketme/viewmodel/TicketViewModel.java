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

import java.util.List;

import hes.projet.ticketme.BaseApp;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.data.repository.TicketRepository;
import hes.projet.ticketme.util.OnAsyncEventListener;

public class TicketViewModel extends AndroidViewModel {

    private TicketRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<TicketEntity> observableTicket;

    public TicketViewModel(@NonNull Application application,
                           final String ticketId, TicketRepository ticketRepository) {
        super(application);

        Context applicationContext = application.getApplicationContext();

        observableTicket = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableTicket.setValue(null);

        if(ticketId !=null){
            LiveData<TicketEntity> ticket = mRepository.getTicket(ticketId);

            // observe the changes of the entities from the database and forward them
            observableTicket.addSource(ticket, observableTicket::setValue);
        }


    }


    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;
        private final String mTicketId;
        private final TicketRepository mRepository;



        public Factory(@NonNull Application application, String ticketId) {
            mApplication = application;
            mTicketId = ticketId;
            mRepository = ((BaseApp)application).getTicketRepository();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TicketViewModel(mApplication, mTicketId, mRepository);
        }
    }

    /**
     * Expose the LiveData TicketEntities query so the UI can observe it.
     */
    public LiveData<TicketEntity> getTicket() {
        return observableTicket;
    }

    public void createTicket(TicketEntity ticket, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getTicketRepository()
                .insert(ticket, callback);
    }

    public void updateTicket(TicketEntity ticket, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getTicketRepository()
                .update(ticket, callback);
    }

    public void deleteTicket(TicketEntity ticket, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getTicketRepository()
                .update(ticket, callback);
    }
}
