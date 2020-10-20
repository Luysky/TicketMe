package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.Ticket;

public class TicketListActivity extends AppCompatActivity {

    ListView listView;
    List<String> listString = new ArrayList();
    List<Ticket> listTicket = new ArrayList<>();

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

        listView  = findViewById(R.id.list_view);

        Ticket ticket1 = new Ticket(1,"Bug affichage","J'ai un écran noir");
        Ticket ticket2 = new Ticket(2,"Bug affichage","File not found");
        Ticket ticket3 = new Ticket(3,"Crash","Application crash au démarrage");
        Ticket ticket4 = new Ticket(4,"Crash","Application se fige après 5 min");
        Ticket ticket5 = new Ticket(5,"Help","Comment changer la langue?");
        Ticket ticket6 = new Ticket(6,"Help","Comment changer la couleur?");
        Ticket ticket7 = new Ticket(7,"Password","Mon password est bloqué");
        Ticket ticket8 = new Ticket(8,"Password","Je n'arrive pas à changer mon password");
        Ticket ticket9 = new Ticket(9,"Password","Acces denied pourquoi?");
        Ticket ticket10 = new Ticket(10,"Help","Comment changer mon Username?");
        Ticket ticket11 = new Ticket(11,"Help","Connection failed?");
        Ticket ticket12 = new Ticket(12,"Bug","Affichage de symboles étranges");
        Ticket ticket13 = new Ticket(13,"Bug","Mes fichiers ont disparus");

        listTicket.add(ticket1);
        listTicket.add(ticket2);
        listTicket.add(ticket3);
        listTicket.add(ticket4);
        listTicket.add(ticket5);
        listTicket.add(ticket6);
        listTicket.add(ticket7);
        listTicket.add(ticket8);
        listTicket.add(ticket9);
        listTicket.add(ticket10);
        listTicket.add(ticket11);
        listTicket.add(ticket12);
        listTicket.add(ticket13);


        for(int i=0; i<listTicket.size();i++){
            listString.add(listTicket.get(i).ticketToString());
        }


        //1 = le contexte, 2 layout, 3 list
        adapter = new ArrayAdapter(TicketListActivity.this, android.R.layout.simple_list_item_1, listString);

        listView.setAdapter(adapter);
    }
}