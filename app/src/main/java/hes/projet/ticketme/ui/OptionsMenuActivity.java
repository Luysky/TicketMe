package hes.projet.ticketme.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import hes.projet.ticketme.R;


public class OptionsMenuActivity extends AppCompatActivity {


    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_settings:
                    displayMessage("Settings option selected");
                    return true;

            case R.id.action_info:
                    displayMessage("Info option selected");
                    return true;

            default:
                    return super.onOptionsItemSelected(item);

        }


    }

    private void displayMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}