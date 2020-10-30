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

public class TicketViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<TicketEntity> observableTicket;

    public TicketViewModel(@NonNull Application application, final Long ticketId, TicketRepository ticketRepository) {
        super(application);

        Context applicationContext = application.getApplicationContext();

        observableTicket = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableTicket.setValue(null);

        LiveData<TicketEntity> ticket = ticketRepository.getTicket(ticketId, applicationContext);

        // observe the changes of the entities from the database and forward them
        observableTicket.addSource(ticket, observableTicket::setValue);
    }


    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final TicketRepository ticketRepository;

        private final Long ticketId;

        public Factory(@NonNull Application application, Long ticketId) {
            this.application = application;
            this.ticketId = ticketId;
            ticketRepository = TicketRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TicketViewModel(application, ticketId, ticketRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<TicketEntity> getTicket() {
        return observableTicket;
    }
}
