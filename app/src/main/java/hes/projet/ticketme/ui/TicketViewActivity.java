/**
 * Last review by BK on 14.11.2020
 */

package hes.projet.ticketme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.data.repository.TicketRepository;
import hes.projet.ticketme.util.OnAsyncEventListener;
import hes.projet.ticketme.viewmodel.TicketViewModel;

public class TicketViewActivity extends BaseActivity {

    private static final String TAG = "TicketView";

    /**
     * TicketEntity to display in activity
     */
    private TicketEntity ticket;

    /**
     * Menu in actionBar
     */
    private Menu menu;


    /**
     * Initialize Ticket View Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        requireLoggedInUser();

        //
        initView(this, R.layout.activity_ticket_view, "Lire un ticket");
        initReturn();

        //
        Intent intent = getIntent();
        String ticketId = intent.getStringExtra("ticketId");
        String ticketUid = intent.getStringExtra("ticketUid");
        int ticketStatus = intent.getIntExtra("ticketStatus", 0);

        //
        showTicket(ticketId, ticketUid, ticketStatus);
    }


    /**
     * Add activity specific menu to actionBar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.i(TAG, "onCreateOptionsMenu");

        this.menu = menu;

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ticket_view_menu, menu);

        /*
         * If the activity is showing a closed ticket, we hide the close action button
         */

        if (ticket != null && ticket.getStatus() > 0) {
            Log.i(TAG, "hide close ticket action ");
            menu.findItem(R.id.action_close_ticket).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Detect click on custom activity menu items
     *
     * @param item Clicked item in menu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_close_ticket:
                displayAlert(getString(R.string.alert_titleInformation),getString(R.string.alert_ticketClosingStatus),this::clickTicketClose);
                break;

            case R.id.action_edit_ticket:
                clickTicketEdit();
                break;

            case R.id.action_delete_ticket:
                displayAlert(getString(R.string.alert_titleInformation),getString(R.string.alert_ticketDelete),this::clickTicketDelete);
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }


    /**
     * Load and show ticket in activity
     *
     * @param ticketId Ticket identifier
     */
    private void showTicket(String ticketId, String ticketUid, int ticketStatus) {

        /*
         * Get the viewModel for displayed ticket
         */

        TicketViewModel.Factory factory = new TicketViewModel.Factory(getApplication(), ticketId, ticketUid, ticketStatus);
        ViewModelProvider provider = new ViewModelProvider(this, factory);
        TicketViewModel viewModel = provider.get(TicketViewModel.class);


        /*
         * Display the ticket when it is loaded
         */

        viewModel.getTicket().observe(this, ticketEntity -> {
            if (ticketEntity != null) {
                ticket = ticketEntity;

                Log.i(TAG, "loaded ticket " + ticket.toString());

                //
                TextView subject = findViewById(R.id.ticketView_subject);
                TextView message = findViewById(R.id.ticketView_message);
                message.setMovementMethod(new ScrollingMovementMethod());

                //
                subject.setText(ticket.getSubject());
                message.setText(ticket.getMessage());

                if (ticket.getStatus() > 0 && menu != null) {
                    menu.findItem(R.id.action_close_ticket).setVisible(false);
                }
                TextView category = findViewById(R.id.ticketView_category);
                category.setText(ticket.getCategory());
            }
            else {
                Log.i(TAG, "Ticket is null");
            }
        });
    }


    /**
     * Handle ticket edit button click
     */
    public void clickTicketEdit() {
        Log.i(TAG, "clicked on  " + ticket.toString());

        Intent intent = new Intent(this, TicketEditActivity.class);
        intent.putExtra("ticketStatus", ticket.getStatus());
        intent.putExtra("ticketUid", ticket.getUserId());
        intent.putExtra("ticketId", ticket.getId());
        startActivity(intent);
        finish();
    }


    /**
     * Handle ticket delete button click
     */
    public void clickTicketDelete() {

        Log.i(TAG, "clicked on  delete ticket");

        TicketRepository.getInstance().delete(ticket, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                        finish();
            }

            @Override
            public void onFailure(Exception e) {
                displayMessage(getString(R.string.toast_deleteTicketError),1);
            }
        });
    }


    /**
     * Handle ticket close button click
     */
    public void clickTicketClose() {
        Log.i(TAG, "clicked on  close ticket");

        ticket.setStatus(1);

        return;
        /*
        new UpdateTicket(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                displayMessage(getString(R.string.toast_closingTicketError),1);
            }
        }).execute(ticket);*/

    }
}