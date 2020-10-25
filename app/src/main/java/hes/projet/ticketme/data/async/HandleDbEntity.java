
package hes.projet.ticketme.data.async;

import android.content.Context;
import android.os.AsyncTask;

import hes.projet.ticketme.data.AppDatabase;
import hes.projet.ticketme.util.OnAsyncEventListener;

public abstract class HandleDbEntity<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public HandleDbEntity(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Result doInBackground(Params... params) {
        try {
            for (Params p : params)
                doAaction(p);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    public void onPostExecute(Result result) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }

    protected abstract void doAaction(Params p);

    public AppDatabase getDatabase() {
        return database;
    }
}

