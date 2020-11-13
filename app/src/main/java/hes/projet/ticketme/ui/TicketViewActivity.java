package hes.projet.ticketme.ui;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.async.ticket.DeleteTicket;
import hes.projet.ticketme.data.async.ticket.UpdateTicket;
import hes.projet.ticketme.data.entity.CategoryEntity;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.util.OnAsyncEventListener;
import hes.projet.ticketme.viewmodel.CategoryViewModel;
import hes.projet.ticketme.viewmodel.TicketViewModel;

public class TicketViewActivity extends BaseActivity {

//    private Toolbar menuToolBar;

    private static final String TAG = "TicketView";

    private TextView category;
    private TextView subject;
    private TextView message;

    private TicketEntity ticket;
    private CategoryEntity categoryEntity;
    private TicketViewModel viewModel;
    private CategoryViewModel catViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long userId = getLoggedInUserId();

        if (userId == 0) {
            Intent intent = new Intent(this, LoginHomepageActivity.class);
            startActivity(intent);
        }

        if (isAdministrator())
            userId = 0;

        setContentView(R.layout.activity_ticket_view);

        initMenu();
        initReturn();

        category = findViewById(R.id.category);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);

        Intent intent = getIntent();

        Long ticketId = intent.getLongExtra("ticketId", 0);

        /**
         * Get the viewModel
         */
        showTicket(ticketId);



    }

    private void showTicket(Long ticketId) {
        TicketViewModel.Factory factory = new TicketViewModel.Factory(getApplication(), ticketId);
        ViewModelProvider provider = new ViewModelProvider(this, factory);


        viewModel = provider.get(TicketViewModel.class);

        viewModel.getTicket().observe(this, ticketEntity -> {
            if (ticketEntity != null) {
                ticket = ticketEntity;

                Log.i(TAG, "loaded ticket " + ticket.toString());
                //On recupere l idTicket et le subjet pour l affichage dans la liste de message.
//                category.setText(ticket.getCategoryId().toString());
                subject.setText(ticket.getSubject());
                message.setText(ticket.getMessage());

                showCategory(ticket.getCategoryId());
            }
            else {
                Log.i(TAG, "Ticket is null");
            }
        });


    }


    private void showCategory(Long categoryId) {
        CategoryViewModel.Factory factory = new CategoryViewModel.Factory(getApplication(), categoryId);
        ViewModelProvider provider = new ViewModelProvider(this, factory);


        catViewModel = provider.get(CategoryViewModel.class);

        catViewModel.getCategory().observe(this, catEntity -> {
            if (catEntity != null) {
                categoryEntity = catEntity;

//                Log.i(TAG, "loaded ticket " + ticket.toString());
                //On recupere l idTicket et le subjet pour l affichage dans la liste de message.
                category.setText(categoryEntity.getName());
            }
            else {
                Log.i(TAG, "Ticket is null");
            }
        });


    }


    public void clickTicketEdit(View view) {
        Log.i(TAG, "clicked on  " + ticket.toString());

        Intent intent = new Intent(this, TicketEditActivity.class);
        intent.putExtra("ticketId", ticket.getId());
        startActivity(intent);
    }

    public void clickTicketDelete(View view) {

        new DeleteTicket(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                /**
                 * TODO alert in case of an error
                 */
            }
        }).execute(ticket);
    }

    public void clickTicketClose(View view) {

        ticket.setStatus(1);

        new UpdateTicket(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                /**
                 * TODO alert in case of an error
                 */
            }
        }).execute(ticket);
    }
}