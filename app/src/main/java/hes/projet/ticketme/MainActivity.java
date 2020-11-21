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
        String userId = getLoggedInUserId();

        if (userId.equals("")) {
            goTo(LoginActivity.class);
        } else {
            goTo(TicketListActivity.class);
        }

    }

    private void goTo(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}