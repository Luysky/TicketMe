package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import hes.projet.ticketme.R;

public class TicketReadActivity extends OptionsMenuActivity {

    //CLASSE NE SERVANT A RIEN


    private String category;
    private String subject;
    private String message;
    private Toolbar menuToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_read);

        initMenu();
        initReturn();


        Intent intent = getIntent();
        category = intent.getStringExtra("catégorie");
        subject = intent.getStringExtra("sujet");
        message = intent.getStringExtra("message");

    }
}