package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.viewmodel.TicketListViewModel;
import hes.projet.ticketme.viewmodel.TicketViewModel;

public class TicketViewActivity extends AppCompatActivity {

    private static final String TAG = "TicketView";

    private TextView category;
    private TextView subject;
    private TextView message;

    private TicketEntity ticket;
    private TicketViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_view);

        category = findViewById(R.id.category);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);

        Intent intent = getIntent();

        Long ticketId = intent.getLongExtra("ticketId", 0);

        /**
         * Get the viewModel
         */
        TicketViewModel.Factory factory = new TicketViewModel.Factory(getApplication(), ticketId);
        ViewModelProvider provider = new ViewModelProvider(this, factory);
        viewModel = provider.get(TicketViewModel.class);

        viewModel.getTicket().observe(this, ticketEntity -> {
            if (ticketEntity != null) {
                ticket = ticketEntity;

                Log.i(TAG, "loaded ticket " + ticket.toString());
                //On recupere l idTicket et le subjet pour l affichage dans la liste de message.
                category.setText(ticket.getCategory());
                subject.setText(ticket.getSubject());
                message.setText(ticket.getMessage());
            }
            else {
                Log.i(TAG, "Ticket is null");
            }
        });


    }


}