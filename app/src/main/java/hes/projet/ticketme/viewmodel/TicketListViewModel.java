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
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.data.repository.TicketRepository;
import hes.projet.ticketme.util.OnAsyncEventListener;

public class TicketListViewModel extends AndroidViewModel {

    private TicketRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<TicketEntity>> observableTickets;

    public TicketListViewModel(@NonNull Application application,
                               final String userId, int status, TicketRepository ticketRepository) {
        super(application);

        mRepository = ticketRepository;

        observableTickets = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableTickets.setValue(null);
        LiveData<List<TicketEntity>> tickets = ticketRepository.getAllTickets(userId, status);

        // observe the changes of the entities from the database and forward them
        observableTickets.addSource(tickets, observableTickets::setValue);
    }


    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;
        private final String mUserId;
        private final TicketRepository mTicketRepository;

        private int status;

        private String categoryId;

        public Factory(@NonNull Application application, String userId, int status, String categoryId) {
            mApplication = application;
            mUserId = userId;
            this.status = status;
            this.categoryId = categoryId;
            mTicketRepository = TicketRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TicketListViewModel(mApplication, mUserId, status, mTicketRepository);
        }
    }

    /**
     * Expose the LiveData UserEntities query so the UI can observe it.
     */
    public LiveData<List<TicketEntity>> getTickets() {
        return observableTickets;
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
