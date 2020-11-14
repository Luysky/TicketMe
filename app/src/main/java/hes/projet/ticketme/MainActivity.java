package hes.projet.ticketme;

import android.content.Intent;
import android.os.Bundle;

import hes.projet.ticketme.ui.LoginActivity;
import hes.projet.ticketme.ui.TicketListActivity;
import hes.projet.ticketme.ui.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Check if user is logged in or not.
         * If logged in redirect to ticket list else to login
         */
        Intent intent;
        long userId = getLoggedInUserId();

        if (userId == 0) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, TicketListActivity.class);
        }

        startActivity(intent);
    }
}