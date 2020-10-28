package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import hes.projet.ticketme.R;

public class TicketEditActivity extends AppCompatActivity {

    private Toolbar menuToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_edit);

        //Utilisation de l action bar
        menuToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(menuToolBar);
    }
}