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

import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.data.repository.TicketRepository;

public class TicketListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<TicketEntity>> observableTickets;

    public TicketListViewModel(@NonNull Application application, Long userId, int status, Long categoryId, TicketRepository ticketRepository) {
        super(application);

        Context applicationContext = application.getApplicationContext();

        observableTickets = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableTickets.setValue(null);
        LiveData<List<TicketEntity>> tickets = ticketRepository.getAllTickets(applicationContext, userId, status, categoryId);

        // observe the changes of the entities from the database and forward them
        observableTickets.addSource(tickets, observableTickets::setValue);
    }


    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final TicketRepository ticketRepository;

        private final Long userId;

        private int status;

        private Long categoryId;

        public Factory(@NonNull Application application, Long userId, int status, Long categoryId) {
            this.application = application;

            this.userId = userId;
            this.status = status;
            this.categoryId = categoryId;

            ticketRepository = TicketRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TicketListViewModel(application, userId, status, categoryId, ticketRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<TicketEntity>> getTickets() {
        return observableTickets;
    }
}
