package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.Ticket;
import hes.projet.ticketme.data.entity.CategoryEntity;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.viewmodel.CategoryListViewModel;
import hes.projet.ticketme.viewmodel.TicketListViewModel;

public class TicketListActivity extends AppCompatActivity {

    private static final String TAG = "TicketListActivity";

    private ListView listView;
    private List<String> listString = new ArrayList();
    private List<TicketEntity> tickets = new ArrayList<>();
    private List<CategoryEntity> categories = new ArrayList<>();
    private ArrayAdapter adapter;

    private TicketListViewModel viewModel;
    private CategoryListViewModel categoryListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);
        listView  = findViewById(R.id.list_view);




        TicketListViewModel.Factory factory = new TicketListViewModel.Factory(getApplication());
        ViewModelProvider provider = new ViewModelProvider(this, factory);
        viewModel = provider.get(TicketListViewModel.class);
        viewModel.getTickets().observe(this, ticketEntities -> {
            if (ticketEntities != null) {
                tickets = ticketEntities;

                //On recupere l idTicket et le subjet pour l affichage dans la liste de message.
                for(int i = 0; i < tickets.size(); i++){
                    listString.add(tickets.get(i).toString());
                }

                //Ces lignes servent a la mise en place d une liste deroulante.
                adapter = new ArrayAdapter(TicketListActivity.this, android.R.layout.simple_list_item_1, listString);
                listView.setAdapter(adapter);
            }
        });



        CategoryListViewModel.Factory factory2 = new CategoryListViewModel.Factory(getApplication());
        ViewModelProvider provider2 = new ViewModelProvider(this, factory2);
        categoryListViewModel = provider2.get(CategoryListViewModel.class);
        categoryListViewModel.getCategories().observe(this, categoryEntities ->  {
            if (categoryEntities != null) {
                categories = categoryEntities;
            }
        });



        //Action lorsque l on click sur un objet de la liste.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TicketEntity t = tickets.get(position);
                Intent intent = new Intent(TicketListActivity.this, TicketViewActivity.class);

                Log.i(TAG, "clicked on  " + t.toString());
                intent.putExtra("ticketId", t.getId());
                startActivity(intent);

                //Pour l instant un toast
                //Toast.makeText(TicketListActivity.this,listTicket.get(position).getSubject(),Toast.LENGTH_SHORT).show();
                // clickOnTicket(view);
            }
        });

    }
        //Bouton ajouter un nouveau Ticket
    public void clickAddTicket(View view){
        Intent intent = new Intent(this, TicketCreateActivity.class);
        startActivity(intent);
    }

    public void clickOnTicket(View view){
        Intent intent = new Intent(this, TicketReadActivity.class);
        startActivity(intent);
    }
}