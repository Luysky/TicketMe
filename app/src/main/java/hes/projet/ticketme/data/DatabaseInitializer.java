package hes.projet.ticketme.data;

import android.os.AsyncTask;
import android.util.Log;

import hes.projet.ticketme.data.entity.CategoryEntity;
import hes.projet.ticketme.data.entity.TicketEntity;


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

    private static void addTicket(final AppDatabase db, final String category, final String subject,
                                  final String message) {
        TicketEntity ticket = new TicketEntity(category, subject, message);
        db.ticketDao().insert(ticket);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.ticketDao().deleteAll();

        addTicket(db, "Bug","J'ai un écran noir","Bla bla bla");
        addTicket(db, "Bug","File not found","Bla bla bla");
        addTicket(db, "Crash","Application crash au démarrage","Bla bla bla");
        addTicket(db, "Crash","Application se fige après 5 min","Bla bla bla");
        addTicket(db, "Help","Comment changer la langue?","Bla bla bla");
        addTicket(db, "Help","Comment changer la couleur?","Bla bla bla");
        addTicket(db, "Password","Mon password est bloqué","Bla bla bla");
        addTicket(db, "Password","Je n'arrive pas à changer mon password","Bla bla bla");
        addTicket(db, "Password","Acces denied pourquoi?","Bla bla bla");
        addTicket(db, "Help","Comment changer mon Username?","Bla bla bla");
        addTicket(db, "Help","Connection failed?","Bla bla bla");
        addTicket(db, "Bug","Affichage de symboles étranges","Bla bla bla");
        addTicket(db, "Bug","Mes fichiers ont disparus","Bla bla bla");
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

            populateWithTestData(database);
            populateWithBaseData(database);
            return null;
        }

    }
}
