package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.Ticket;


public class TicketCreateActivity extends AppCompatActivity {

    private Spinner spinner;
    private String category;
    private String subject;
    private String message;
    private Toolbar menuToolBar;

    //Il va falloir gerer la generation auto des id des Tickets actuellement le prochain chiffre serait le 14.
    private int idTicket = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_create);

        //Utilisation de l action bar
        menuToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(menuToolBar);

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
            Toast.makeText(TicketCreateActivity.this,"Veuillez remplir le sujet!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(message.equals("")){
            Toast.makeText(TicketCreateActivity.this,"Veuillez remplir le message!",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Ticket brandNew = new Ticket(idTicket,category,subject,message);
            Toast.makeText(TicketCreateActivity.this,"Nouveau ticket créé!",Toast.LENGTH_SHORT).show();

            //Ici il faudra rajouter le nouveau ticket a la listTicket et penser a actualiser la liste dans TicketListActivity
        }
    }
}