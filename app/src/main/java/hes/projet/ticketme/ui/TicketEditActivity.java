package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import hes.projet.ticketme.R;

public class TicketEditActivity extends OptionsMenuActivity {

//    private Toolbar menuToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_edit);

        initMenu();



//        //Utilisation de l action bar
//        menuToolBar = findViewById(R.id.toolbar);
//        setTitle(null);
//        setSupportActionBar(menuToolBar);
//
//        //Afficher et utiliser le bouton retour
//        menuToolBar.setNavigationIcon(R.drawable.ic_return);
//        menuToolBar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
}