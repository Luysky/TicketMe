package hes.projet.ticketme.data;

import android.os.AsyncTask;
import android.util.Log;

import hes.projet.ticketme.data.entity.TicketEntity;


public class DatabaseInitializer {
    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addTicket(final AppDatabase db, final String category, final String subject,
                                  final String message) {
        TicketEntity ticket = new TicketEntity(category, subject, message);
        db.ticketDao().insert(ticket);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.ticketDao().deleteAll();

        addTicket(db, "michel.platini@fifa.com", "Michel", "Platini");
        addTicket(db, "sepp.blatter@fifa.com", "Sepp", "Blatter");
        addTicket(db, "ebbe.schwartz@fifa.com", "Ebbe", "Schwartz");
        addTicket(db, "aleksander.ceferin@fifa.com", "Aleksander", "Ceferin");
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}
