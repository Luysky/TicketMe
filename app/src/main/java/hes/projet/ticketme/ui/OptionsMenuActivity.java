package hes.projet.ticketme.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import hes.projet.ticketme.R;
import hes.projet.ticketme.util.Constants;


public class OptionsMenuActivity extends AppCompatActivity {
    public static final String TAG = "OptionsMenuActivity";

    protected Toolbar menuToolBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        Log.i(TAG, "onCreateOptionsMenu");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu,menu);

        return true;
    }

    public void initMenu() {
        menuToolBar = findViewById(R.id.toolbar);
        setTitle(null);
        setSupportActionBar(menuToolBar);

    }

    public void initManager(){

        //Afficher et utiliser le bouton retour
        menuToolBar.setNavigationIcon(R.drawable.ic_manager);
        menuToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_view).getParent();
                mDrawerLayout.openDrawer(Gravity.LEFT);
//                finish();
            }
        });
    }

    public void initReturn(){
        //Afficher et utiliser le bouton retour
        menuToolBar.setNavigationIcon(R.drawable.ic_return);
        menuToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReturn(v);
            }
        });
    }

    public long getLoggedInUserId() {
        SharedPreferences settings = getSharedPreferences(Constants.PREF_FILE, 0);
        return settings.getLong(Constants.PREF_USER_ID, 0);
    }

    public boolean isAdministrator() {
        SharedPreferences settings = getSharedPreferences(Constants.PREF_FILE, 0);
        return settings.getBoolean(Constants.PREF_USER_ISADMIN, false);
    }


    public void onReturn(View v) {
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_settings:
                    displayMessage("Settings option selected");
                    return true;

            case R.id.action_info:

                    Intent intent = new Intent(this,InfoActivity.class);
                    startActivity(intent);
                    return true;

            default:
                    return super.onOptionsItemSelected(item);

        }


    }

    private void displayMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
