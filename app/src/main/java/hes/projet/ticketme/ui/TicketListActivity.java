package hes.projet.ticketme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import hes.projet.ticketme.R;

import hes.projet.ticketme.data.entity.CategoryEntity;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.viewmodel.CategoryListViewModel;
import hes.projet.ticketme.viewmodel.TicketListViewModel;

public class TicketListActivity extends BaseActivity {

    private static final String TAG = "TicketListActivity";

    private long userId;

    private ListView listView;
    private List<TicketEntity> tickets = new ArrayList<>();
    private List<CategoryEntity> categories = new ArrayList<>();
    private ArrayAdapter adapter;

    private TicketListViewModel viewModel;
    private CategoryListViewModel categoryListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = requireLoggedInUser();

        if (isAdministrator())
            userId = 0;



        Intent intent = getIntent();
        int statusFilter = intent.getIntExtra("statusFilter", 0);

        String title = statusFilter == 0 ? "Tickets ouverts" : "Tickets fermés";

        initView(this, R.layout.activity_ticket_list, title);
        initDrawer();


        listView  = findViewById(R.id.list_view);

        TicketListViewModel.Factory factory = new TicketListViewModel.Factory(getApplication(), userId, statusFilter, (long) 0);
        ViewModelProvider provider = new ViewModelProvider(this, factory);
        viewModel = provider.get(TicketListViewModel.class);
        viewModel.getTickets().observe(this, ticketEntities -> {
            if (ticketEntities != null) {
                tickets = ticketEntities;

                //Ces lignes servent a la mise en place d une liste deroulante.
                adapter = new ArrayAdapter(TicketListActivity.this, android.R.layout.simple_list_item_1, tickets);
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

                Log.i(TAG, "clicked on  " + t.toString());

                //
                Intent intent = new Intent(TicketListActivity.this, TicketViewActivity.class);
                intent.putExtra("ticketId", t.getId());
                startActivity(intent);
            }
        });
    }



    //Bouton ajouter un nouveau Ticket
    public void clickAddTicket(View view){
        Intent intent = new Intent(this, TicketEditActivity.class);
        startActivity(intent);
    }

}