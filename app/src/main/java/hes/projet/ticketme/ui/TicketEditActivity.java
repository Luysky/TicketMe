package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.viewmodel.CategoryViewModel;
import hes.projet.ticketme.viewmodel.TicketViewModel;

public class TicketEditActivity extends OptionsMenuActivity {

//    private Toolbar menuToolBar;

    private Spinner spinner;
    private String category;
    private String subject;
    private String message;

    private TicketEntity ticket;

    /*
     * ViewModels
     */
    private TicketViewModel ticketViewModel;
    private CategoryViewModel categoryViewModel;

    private EditText editTextSubject;
    private EditText editTextMessage;

//    private Toolbar menuToolBar;

    //Il va falloir gerer la generation auto des id des Tickets actuellement le prochain chiffre serait le 14.
//    private int idTicket = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_edit);

        initMenu();



        //Utilisation de l action bar
//        menuToolBar = findViewById(R.id.toolbar);
//        setTitle(null);
//        setSupportActionBar(menuToolBar);

        //Afficher et utiliser le bouton retour
        menuToolBar.setNavigationIcon(R.drawable.ic_return);
        menuToolBar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

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
        });

        editTextSubject = findViewById(R.id.editTextSubject);
        editTextMessage = findViewById(R.id.editTextTextMultiLine);



        Intent intent = getIntent();
        Long ticketId = intent.getLongExtra("ticketId", 0);


        // Get TicketViewModel
        TicketViewModel.Factory factory = new TicketViewModel.Factory(getApplication(), ticketId);
        ViewModelProvider provider = new ViewModelProvider(this, factory);
        ticketViewModel = provider.get(TicketViewModel.class);


        if (ticketId.equals(0)) {
            ticket = new TicketEntity();
        }
        else {
            ticketViewModel.getTicket().observe(this, ticketEntity -> {
                if (ticketEntity != null) {
                    ticket = ticketEntity;

                    Log.i(TAG, "loaded ticket " + ticket.toString());
                    //On recupere l idTicket et le subjet pour l affichage dans la liste de message.
//                category.setText(ticket.getCategoryId().toString());
                    editTextSubject.setText(ticket.getSubject());
                    editTextMessage.setText(ticket.getMessage());

//                    showCategory(ticket.getCategoryId());
                }
                else {
                    Log.i(TAG, "Ticket is null");
                }
            });

        }






        //Utilisation d un spinner pour le choix de la categorie.
        spinner = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.listCategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //On va recuperer la selection du spinner. Si l utilisateur ne choisit rien on definit la rubrique category avec Help
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = "Help";
            }
        });
    }


    public void clickNewTicket(View view){

        EditText editTextSub = findViewById(R.id.editTextSubject);
        EditText editTextMes = findViewById(R.id.editTextTextMultiLine);

        subject = editTextSub.getText().toString();
        message = editTextMes.getText().toString();

        if(subject.equals("")){
            Toast.makeText(TicketEditActivity.this,"Veuillez remplir le sujet!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(message.equals("")){
            Toast.makeText(TicketEditActivity.this,"Veuillez remplir le message!",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
//            TicketEntity brandNew = new TicketEntity(idTicket,category,subject,message);
//            Toast.makeText(TicketCreateActivity.this,"Nouveau ticket créé!",Toast.LENGTH_SHORT).show();

            //Ici il faudra rajouter le nouveau ticket a la listTicket et penser a actualiser la liste dans TicketListActivity
        }
    }

}