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