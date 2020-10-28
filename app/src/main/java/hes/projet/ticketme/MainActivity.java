package hes.projet.ticketme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.app.ActionBar;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import hes.projet.ticketme.data.Ticket;
import hes.projet.ticketme.ui.*;

public class MainActivity extends AppCompatActivity {

    private Toolbar menuToolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Utilisation de l action bar
        menuToolBar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(menuToolBar);
    }

    private void onCLick() {

    }

    public void clickTicketList(View view) {
        Intent intent = new Intent(this, TicketListActivity.class);
        startActivity(intent);
    }

    public void clickTicketView(View view) {
        Intent intent = new Intent(this, TicketViewActivity.class);
        startActivity(intent);
    }

    public void clickTicketEdit(View view) {
        Intent intent = new Intent(this, TicketEditActivity.class);
        startActivity(intent);
    }

    public void clickLoginHomepage(View view){
        Intent intent = new Intent(this, LoginHomepageActivity.class);
        startActivity(intent);
    }

    public void clickUserInterface(View view){
        Intent intent = new Intent(this, UserInterfaceActivity.class);
        startActivity(intent);
    }

    public void clickUserManagement(View view){
        Intent intent = new Intent(this, UserManagementActivity.class);
        startActivity(intent);
    }


}