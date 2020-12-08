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

import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.data.repository.TicketRepository;
import hes.projet.ticketme.util.OnAsyncEventListener;

public class TicketViewModel extends AndroidViewModel {

    private TicketRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<TicketEntity> observableTicket;

    public TicketViewModel(@NonNull Application application,
                           final String ticketId,
                           String ticketUid,
                           int ticketStatus,
                           TicketRepository ticketRepository) {
        super(application);


        observableTicket = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableTicket.setValue(null);

        if(ticketId !=null){
            LiveData<TicketEntity> ticket = ticketRepository.getTicket(ticketId, ticketUid, ticketStatus);

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
        private final String mTicketUid;
        private final int mTicketStatus;
        private final TicketRepository mRepository;



        public Factory(@NonNull Application application, String ticketId, String ticketUid, int ticketStatus) {
            mApplication = application;
            mTicketId = ticketId;
            mTicketUid = ticketUid;
            mTicketStatus = ticketStatus;
            mRepository = TicketRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TicketViewModel(mApplication, mTicketId, mTicketUid, mTicketStatus, mRepository);
        }
    }

    /**
     * Expose the LiveData TicketEntities query so the UI can observe it.
     */
    public LiveData<TicketEntity> getTicket() {
        return observableTicket;
    }


}
