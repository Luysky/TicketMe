package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;

import hes.projet.ticketme.R;

import hes.projet.ticketme.data.entity.CategoryEntity;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.viewmodel.CategoryListViewModel;
import hes.projet.ticketme.viewmodel.TicketListViewModel;

public class TicketListActivity extends OptionsMenuActivity {

    private static final String TAG = "TicketListActivity";

    private ListView listView;
    private List<TicketEntity> tickets = new ArrayList<>();
    private List<CategoryEntity> categories = new ArrayList<>();
    private ArrayAdapter adapter;

    private TicketListViewModel viewModel;
    private CategoryListViewModel categoryListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ticket_list);

        //initMenu = toolbar
        //initManager = Manager en plus
        initMenu();
        initManager();

        listView  = findViewById(R.id.list_view);



        Log.i(TAG, "onCreate");



        TicketListViewModel.Factory factory = new TicketListViewModel.Factory(getApplication());
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
                Intent intent = new Intent(TicketListActivity.this, TicketViewActivity.class);

                Log.i(TAG, "clicked on  " + t.toString());
                intent.putExtra("ticketId", t.getId());
                startActivity(intent);
            }
        });

    }

    //Methode servant a rajouter une option (Manager) dans l action bar
    // @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        super.onCreateOptionsMenu(menu);
//        Log.i(TAG, "onCreateOptionsMenu");
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.manager_menu,menu);
//        return true;
//    }


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