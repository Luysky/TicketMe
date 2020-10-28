package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import hes.projet.ticketme.R;

public class TicketReadActivity extends AppCompatActivity {

    private String category;
    private String subject;
    private String message;
    private Toolbar menuToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_read);

        //Utilisation de l action bar
        menuToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(menuToolBar);


        Intent intent = getIntent();
        category = intent.getStringExtra("catégorie");
        subject = intent.getStringExtra("sujet");
        message = intent.getStringExtra("message");

    }
}