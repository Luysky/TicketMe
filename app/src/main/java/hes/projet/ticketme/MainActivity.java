package hes.projet.ticketme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import hes.projet.ticketme.ui.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}