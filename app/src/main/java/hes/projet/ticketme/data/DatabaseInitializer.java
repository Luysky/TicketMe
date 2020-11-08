package hes.projet.ticketme.data;

import android.os.AsyncTask;
import android.util.Log;

import hes.projet.ticketme.data.entity.CategoryEntity;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.data.entity.UserEntity;


public class DatabaseInitializer {
    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addCategory(final AppDatabase db, final String categoryName) {
        CategoryEntity category = new CategoryEntity(categoryName);
        db.categoryDao().insert(category);
    }

    private static void addTicket(final AppDatabase db, final Long category, final Long user, final String subject,
                                  final String message) {
        TicketEntity ticket = new TicketEntity(user, category, subject, message);
        db.ticketDao().insert(ticket);
    }


    private static void addUser(final AppDatabase db, final String username, final String password,
                                  final boolean admin) {
        UserEntity user = new UserEntity(username, password, admin);
        db.userDao().insert(user);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.ticketDao().deleteAll();

        addUser(db,"tom@gmail.com","1234",true);
        addUser(db,"ben@gmail.com","1234",true);
        addUser(db, "toto@gmail.com","123",false);
        addUser(db, "tata@gmail.com","123",false);
        addUser(db, "titi@gmail.com","123",false);
        addUser(db,"tutu@gmail.com","123",false);

        addTicket(db, (long) 1, (long) 3,"J'ai un écran noir","Bla bla bla");
        addTicket(db, (long) 1, (long) 4,"File not found","Bla bla bla");
        addTicket(db, (long) 2, (long) 3, "Application crash au démarrage","Bla bla bla");
        addTicket(db, (long) 2, (long) 3,"Application se fige après 5 min","Bla bla bla");
        addTicket(db, (long) 3, (long) 3,"Comment changer la langue?","Bla bla bla");
        addTicket(db, (long) 3, (long) 5,"Comment changer la couleur?","Bla bla bla");
        addTicket(db, (long) 4, (long) 3,"Mon password est bloqué","Bla bla bla");
        addTicket(db, (long) 4, (long) 3,"Je n'arrive pas à changer mon password","Bla bla bla");
        addTicket(db, (long) 4, (long) 4,"Acces denied pourquoi?","Bla bla bla");
        addTicket(db, (long) 3, (long) 5,"Comment changer mon Username?","Bla bla bla");
        addTicket(db, (long) 3, (long) 3,"Connection failed?","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Affichage de symboles étranges","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");
        addTicket(db, (long) 1, (long) 3,"Mes fichiers ont disparus","Bla bla bla");

    }

    private static void populateWithBaseData(AppDatabase db) {
        db.categoryDao().deleteAll();

        addCategory(db, "Bug");
        addCategory(db, "Crash");
        addCategory(db, "Help");
        addCategory(db, "Password");
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {

            populateWithBaseData(database);
            populateWithTestData(database);
            return null;
        }

    }
}
