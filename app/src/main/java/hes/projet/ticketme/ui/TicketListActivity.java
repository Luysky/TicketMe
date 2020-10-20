package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.Ticket;

public class TicketListActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> listString = new ArrayList();
    private List<Ticket> listTicket = new ArrayList<>();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);
        listView  = findViewById(R.id.list_view);

        //Creation manuelle de Ticket pour les tests
        Ticket ticket1 = new Ticket(1,"Bug","J'ai un écran noir","Bla bla bla");
        Ticket ticket2 = new Ticket(2,"Bug","File not found","Bla bla bla");
        Ticket ticket3 = new Ticket(3,"Crash","Application crash au démarrage","Bla bla bla");
        Ticket ticket4 = new Ticket(4,"Crash","Application se fige après 5 min","Bla bla bla");
        Ticket ticket5 = new Ticket(5,"Help","Comment changer la langue?","Bla bla bla");
        Ticket ticket6 = new Ticket(6,"Help","Comment changer la couleur?","Bla bla bla");
        Ticket ticket7 = new Ticket(7,"Password","Mon password est bloqué","Bla bla bla");
        Ticket ticket8 = new Ticket(8,"Password","Je n'arrive pas à changer mon password","Bla bla bla");
        Ticket ticket9 = new Ticket(9,"Password","Acces denied pourquoi?","Bla bla bla");
        Ticket ticket10 = new Ticket(10,"Help","Comment changer mon Username?","Bla bla bla");
        Ticket ticket11 = new Ticket(11,"Help","Connection failed?","Bla bla bla");
        Ticket ticket12 = new Ticket(12,"Bug","Affichage de symboles étranges","Bla bla bla");
        Ticket ticket13 = new Ticket(13,"Bug","Mes fichiers ont disparus","Bla bla bla");

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

        //On recupere l idTicket et le subjet pour l affichage dans la liste de message.
        for(int i=0; i<listTicket.size();i++){
            listString.add(listTicket.get(i).ticketToString());
        }

        //Ces lignes servent a la mise en place d une liste deroulante.
        adapter = new ArrayAdapter(TicketListActivity.this, android.R.layout.simple_list_item_1, listString);
        listView.setAdapter(adapter);

        //Action lorsque l on click sur un objet de la liste.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pour l instant un toast
                Toast.makeText(TicketListActivity.this,listTicket.get(position).getSubject(),Toast.LENGTH_SHORT).show();
            }
        });

    }
        //Bouton ajouter un nouveau Ticket
    public void clickAddTicket(View view){
        Intent intent = new Intent(this, TicketCreateActivity.class);
        startActivity(intent);
    }
}