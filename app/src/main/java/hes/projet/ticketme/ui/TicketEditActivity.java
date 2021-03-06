/**
 * Last review by BK on 14.11.2020
 */

package hes.projet.ticketme.ui;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import hes.projet.ticketme.R;
import hes.projet.ticketme.adapter.ListAdapter;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.data.repository.TicketRepository;
import hes.projet.ticketme.util.OnAsyncEventListener;
import hes.projet.ticketme.viewmodel.CategoryListViewModel;
import hes.projet.ticketme.viewmodel.TicketViewModel;


public class TicketEditActivity extends BaseActivity {

    private static final String TAG = "TicketEditActivity";


    /**
     * TicketEntity handled by the form.
     *
     * It can be a new TicketEntity to create or an existing to edit
     */
    private TicketEntity ticket;

    private TicketRepository ticketRepository;


    /*
     * Form controls
     */

    private Spinner spinnerCategory;
    private EditText editTextSubject;
    private EditText editTextMessage;


    /*
     * Initial values of new or loaded ticket
     */

    private String subject;
    private String message;
    private String category;


    /*
     * Category list to show in the spinner
     */

    private List<String> categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userId = requireLoggedInUser();


        /*
         * Initialize view and elements
         */

        /**
         * TODO Use string from xml file
         */
        initView(this, R.layout.activity_ticket_edit, "Edit a ticket");
        initReturn();


        ticketRepository = TicketRepository.getInstance();


        /*
         * Initialize form controls
         */

        //
        editTextSubject = findViewById(R.id.ticketEdit_editTextSubject);

        //
        editTextMessage = findViewById(R.id.ticketeEdit_editTextTextMultiLine);

        //
        spinnerCategory = findViewById(R.id.ticketEdit_spinnerCategory);

        ListAdapter<String> adapter = new ListAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

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
        String ticketId = intent.getStringExtra("ticketId");
        String ticketUid = intent.getStringExtra("ticketUid");
        int ticketStatus = intent.getIntExtra("ticketStatus", 0);

        Log.i(TAG, "Ticket id as extra: " + ticketId);


        /*
         * Load ticket or set new ticket depending on mode
         */

        if (ticketId == null) {
            Log.i(TAG, "New ticket");

            ticket = new TicketEntity();

            ticket.setUserId(userId);

            // Set a default category for new tickets
            ticket.setCategory("Bug");

            subject = "";
            message = "";
            category = "Bug";

        }
        else {
            Log.i(TAG, "loading ticket");

            // Load existing ticket to edit.
            loadTicket(ticketId, ticketUid, ticketStatus);
        }
    }


    /**
     * Add activity specific menu to actionBar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ticket_edit_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Detect click on custom activity menu items
     *
     * @param item Clicked item in menu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_save_ticket) {
            clickSaveTicket();
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }


    /**
     * Triggered when user clicks on back button
     */
    @Override
    public void onReturn(View view){

        /*
         * Check if form values have changed
         */

        boolean changedSubject = !subject.equals(editTextSubject.getText().toString());
        boolean changedMessage = !message.equals(editTextMessage.getText().toString());
        boolean changedCategory = !category.equals((String) spinnerCategory.getSelectedItem());


        /*
         * If changes detected, let user go back
         */

        if (!changedCategory && !changedMessage && !changedSubject) {
            finish();
            return;
        }


        /*
         * There are changed values, alert user about loosing changes
         */
        Runnable run = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };

        displayAlert(getString(R.string.alert_titleWarning),getString(R.string.alert_ticketChangesNotSaved),run);
    }


    /**
     * Show right category in spinner when both ticket and category list are loaded
     */
    private void showTicketCategory() {

        if (ticket == null)
            return;

        if (categories == null)
            return;

        for(String category : categories) {
            if(category .equals(ticket.getCategory())) {
                int pos = categories.indexOf(category);
                spinnerCategory.setSelection(pos);
                return;
            }
        }
    }

    public void clickSaveTicket() {

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
           displayMessage(getString(R.string.toast_subjectEmpty),0);
            return;
        }
        ticket.setSubject(subject);

        //
        if(message.equals("")){
            displayMessage(getString(R.string.toast_messageEmpty),0);
            return;
        }
        ticket.setMessage(message);

        //
        ticket.setCategory((String) spinnerCategory.getSelectedItem());


        /*
         * Save new or existing ticket
         */

        if (ticket.getId() == null) {
            Log.i(TAG, "Sauver le nouveau ticket: " + ticket.getSubject());

            ticketRepository.insert(ticket, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    finish();
                }

                @Override
                public void onFailure(Exception e) {
                    displayMessage(getString(R.string.toast_createTicketError),1);
                }
            });
        }
        else {
            Log.i(TAG, "Modifier ticket: " + ticket.toString());

            ticketRepository.update(ticket, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    finish();
                }

                @Override
                public void onFailure(Exception e) {
                    displayMessage(getString(R.string.toast_updateTicketError),1);
                }
            });
        }
    }


    /**
     * Load ticket to display from database
     *
     * @param ticketId Id of the ticket to display
     */
    private void loadTicket(String ticketId, String ticketUid, int ticketStatus) {

        // Get TicketViewModel
        TicketViewModel ticketViewModel;

        TicketViewModel.Factory factory = new TicketViewModel.Factory(getApplication(), ticketId, ticketUid, ticketStatus);
        ViewModelProvider provider = new ViewModelProvider(this, factory);
        ticketViewModel = provider.get(TicketViewModel.class);
        ticketViewModel.getTicket().observe(this, ticketEntity -> {
            if (ticketEntity != null) {

                Log.i(TAG, "loaded ticket " + ticketEntity.toString());

                ticket = ticketEntity;

                subject = ticket.getSubject();
                message = ticket.getMessage();
                category = ticket.getCategory();

                editTextSubject.setText(subject);
                editTextMessage.setText(message);

                showTicketCategory();
            }
        });

    }


    /**
     * Load categories and update values in adapter
     *
     * @param adapter ListAdapter for categories spinner
     */
    private void loadCategories(ListAdapter<String> adapter) {

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