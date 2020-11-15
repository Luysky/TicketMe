package hes.projet.ticketme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import hes.projet.ticketme.ui.LoginActivity;
import hes.projet.ticketme.ui.TicketListActivity;
import hes.projet.ticketme.ui.BaseActivity;
import hes.projet.ticketme.util.Constants;
import hes.projet.ticketme.viewmodel.CategoryViewModel;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Check if user is logged in or not.
         * If logged in redirect to ticket list else to login
         */
        long userId = getLoggedInUserId();

        if (userId == 0) {

            /*
             * Check if db is installed or not (it takes too much time to install at login time)
             */

            SharedPreferences settings = getSharedPreferences(Constants.PREF_FILE, 0);
            if (!settings.getBoolean(Constants.INSTALLED_DB, false)) {

                // Not installed, therefore access a category to initialize database then redirect to login

                CategoryViewModel.Factory factory = new CategoryViewModel.Factory(getApplication(), (long) 1);
                ViewModelProvider provider = new ViewModelProvider(this, factory);
                CategoryViewModel catViewModel = provider.get(CategoryViewModel.class);
                catViewModel.getCategory().observe(this, categoryEntity -> {
                    if (categoryEntity != null) {
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean(Constants.INSTALLED_DB, true);
                        editor.apply();

                        goTo(LoginActivity.class);
                    } else {
                        Log.i(TAG, "Ticket is null");
                    }
                });
            } else {
                goTo(LoginActivity.class);
            }
        } else {
            goTo(TicketListActivity.class);
        }

    }

    private void goTo(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}