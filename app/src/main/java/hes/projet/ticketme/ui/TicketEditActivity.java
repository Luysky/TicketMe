package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hes.projet.ticketme.R;
import hes.projet.ticketme.adapter.ListAdapter;
import hes.projet.ticketme.data.async.ticket.CreateTicket;
import hes.projet.ticketme.data.async.ticket.UpdateTicket;
import hes.projet.ticketme.data.entity.CategoryEntity;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.util.OnAsyncEventListener;
import hes.projet.ticketme.viewmodel.CategoryListViewModel;
import hes.projet.ticketme.viewmodel.CategoryViewModel;
import hes.projet.ticketme.viewmodel.TicketViewModel;

public class TicketEditActivity extends OptionsMenuActivity {

    private static final String TAG = "TicketEditActivity";

    /**
     * TicketEntity handled by the form.
     *
     * It can be a new TicketEntity to create or an existing to edit
     */
    private TicketEntity ticket;

    /*
     * Form controls
     */
    private Spinner spinner;
    private EditText editTextSubject;
    private EditText editTextMessage;



    private String subject;
    private String message;


    /*
     * Category list to show in the spinner
     */
    private List<CategoryEntity> categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Initialize view and elements
         */

        setContentView(R.layout.activity_ticket_edit);

        initMenu();
        initReturn();


        /*
         * Initialize form controls
         */

        //
        editTextSubject = findViewById(R.id.editTextSubject);

        //
        editTextMessage = findViewById(R.id.editTextTextMultiLine);

        //
        spinner = findViewById(R.id.spinnerCategory);

        ListAdapter<CategoryEntity> adapter = new ListAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                showTicketCategory();
            }
        });

        loadCategories(adapter);


        /*
         * Load ticketId if transmitted in intent. If not it is a new ticket.
         */

        Intent intent = getIntent();
        Long ticketId = intent.getLongExtra("ticketId", 0);

        Log.i(TAG, "Ticket id as extra: " + ticketId);


        /*
         * Load ticket or set new ticket depending on mode
         */

        if (ticketId == 0) {
            Log.i(TAG, "New ticket");

            ticket = new TicketEntity();

            // Set a default category for new tickets
            ticket.setCategoryId((long) 1);
        }
        else {
            Log.i(TAG, "loading ticket");

            // Load existing ticket to edit.
            loadTicket(ticketId);
        }

    }


    private void showTicketCategory() {

        if (ticket == null)
            return;

        if (categories == null)
            return;

        for(CategoryEntity category : categories) {
            if(category.getId().equals(ticket.getCategoryId())) {
                int pos = categories.indexOf(category);
                spinner.setSelection(pos);
                return;
            }
        }
    }


    public void clickSaveTicket(View view) {

        /*
         * Curernt form values
         */

        subject = editTextSubject.getText().toString();
        message = editTextMessage.getText().toString();

        /*
         * Check values in form
         */

        //
        if(subject.equals("")){
            Toast.makeText(TicketEditActivity.this,"Veuillez remplir le sujet!",Toast.LENGTH_SHORT).show();
            return;
        }
        ticket.setSubject(subject);

        //
        if(message.equals("")){
            Toast.makeText(TicketEditActivity.this,"Veuillez remplir le message!",Toast.LENGTH_SHORT).show();
            return;
        }
        ticket.setMessage(message);

        //
        CategoryEntity category = (CategoryEntity) spinner.getSelectedItem();
        ticket.setCategoryId(category.getId());


        /*
         * Save new or existing ticket
         */

        if (ticket.getId() == null) {
            Log.i(TAG, "Sauver le nouveau ticket: " + ticket.getSubject());
            new CreateTicket(getApplication(), new OnAsyncEventListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Exception e) {

                }
            }).execute(ticket);
        }
        else {
            Log.i(TAG, "Modifier ticket: " + ticket.toString());

            new UpdateTicket(getApplication(), new OnAsyncEventListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Exception e) {

                }
            }).execute(ticket);
        }
    }

    @Override
    public void onReturn(View view){

        //Creation d un message d alerte en cas d utilisation du bouton retour
        AlertDialog.Builder builder = new AlertDialog.Builder(TicketEditActivity.this);

        builder.setCancelable(true);
        builder.setTitle("Attention!");
        builder.setMessage("Les modifications ne seront pas enregistrées. Voulez-vous quitter la création de ticket?");

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();

    }

    private void loadTicket(long ticketId) {
        // Get TicketViewModel
        TicketViewModel ticketViewModel;

        TicketViewModel.Factory factory = new TicketViewModel.Factory(getApplication(), ticketId);
        ViewModelProvider provider = new ViewModelProvider(this, factory);
        ticketViewModel = provider.get(TicketViewModel.class);
        ticketViewModel.getTicket().observe(this, ticketEntity -> {
            if (ticketEntity != null) {

                Log.i(TAG, "loaded ticket " + ticketEntity.toString());

                ticket = ticketEntity;


                editTextSubject.setText(ticket.getSubject());
                editTextMessage.setText(ticket.getMessage());

                showTicketCategory();
            }
        });

    }

    private void loadCategories(ListAdapter<CategoryEntity> adapter) {

        // Get CategoryViewModel
        CategoryListViewModel categoriesViewModel;

        CategoryListViewModel.Factory categoryFactory = new CategoryListViewModel.Factory(getApplication());
        ViewModelProvider categoryProvider = new ViewModelProvider(this, categoryFactory);
        categoriesViewModel = categoryProvider.get(CategoryListViewModel.class);
        categoriesViewModel.getCategories().observe(this, categories -> {
            if (categories != null) {
                this.categories = categories;
                adapter.updateData(categories);
            }
        });

    }

}