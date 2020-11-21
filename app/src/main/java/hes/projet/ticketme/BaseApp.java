package hes.projet.ticketme;

import android.app.Application;

import hes.projet.ticketme.data.repository.CategoryRepository;
import hes.projet.ticketme.data.repository.TicketRepository;
import hes.projet.ticketme.data.repository.UserRepository;

public class BaseApp extends Application {

    public UserRepository getUserRepository() {
        return UserRepository.getInstance();
    }

    public TicketRepository getTicketRepository() {
        return TicketRepository.getInstance();
    }

    public CategoryRepository getCategoryRepository(){return CategoryRepository.getInstance();}

}
