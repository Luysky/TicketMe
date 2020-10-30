package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import hes.projet.ticketme.R;

public class TicketReadActivity extends AppCompatActivity {

    private String category;
    private String subject;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_read);


        Intent intent = getIntent();
        category = intent.getStringExtra("catégorie");
        subject = intent.getStringExtra("sujet");
        message = intent.getStringExtra("message");

    }
}