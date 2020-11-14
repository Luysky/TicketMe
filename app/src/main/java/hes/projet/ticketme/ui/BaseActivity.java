package hes.projet.ticketme.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import hes.projet.ticketme.R;
import hes.projet.ticketme.util.Constants;


public class BaseActivity extends AppCompatActivity {
    public static final String TAG = "OptionsMenuActivity";

    protected Toolbar menuToolBar;
    DrawerLayout drawer;

    public void initView(BaseActivity currActivity, int viewId, String title)
    {
        setContentView(viewId);

        initMainMenu(currActivity);
        initMenu(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu, menu);

        return true;
    }


    public void initMenu(String title) {
        menuToolBar = findViewById(R.id.toolbar);

        if (menuToolBar == null)
            return;

        setTitle(null);
        setSupportActionBar(menuToolBar);

        TextView titleView = findViewById(R.id.toolbar_title);
        titleView.setText(title);
    }


    public void initDrawer(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if (navigationView == null)
            return;

        //Afficher et utiliser le bouton retour
        menuToolBar.setNavigationIcon(R.drawable.ic_manager);
        menuToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout mDrawerLayout = (DrawerLayout) navigationView.getParent();
                mDrawerLayout.openDrawer(Gravity.LEFT);
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


    public void initMainMenu(BaseActivity currActivity) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if (navigationView == null)
            return;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_view).getParent();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.menu_user_list) {
                    Intent intent = new Intent(currActivity, UserListActivity.class);
                    startActivity(intent);
                } else if(id == R.id.menu_ticket_list_open) {
                    Intent intent = new Intent(currActivity, TicketListActivity.class);
                    startActivity(intent);
                } else if(id == R.id.menu_ticket_list_closed) {
                    Intent intent = new Intent(currActivity, TicketListActivity.class);
                    intent.putExtra("statusFilter", 1);
                    startActivity(intent);
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
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

    protected long requireLoggedInUser() {
        long uid = getLoggedInUserId();

        if (uid == 0) {
            goToLogin();
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("hes.project.ticketme.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive","Logout in progress");
                goToLogin();
            }
        }, intentFilter);

        return uid;
    }


    /**
     *
     * @param v
     */
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

                Intent intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_logout:

                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    /**
     * Show a toast message
     *
     * TODO Make it public and use it from all activities using toast messages
     * @param message Message to display in the toast
     */
    private void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Clear credentials and notify all activities by broadcast
     */
    private void logout() {

        /*
         * Remove login informations from preferences file
         */
        SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_FILE, 0).edit();
        editor.remove(Constants.PREF_USER_ID);
        editor.remove(Constants.PREF_USER_ISADMIN);
        editor.apply();

        /*
         * Broadcast the logout event so all activities are aware and no back button will work
         */
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("hes.project.ticketme.ACTION_LOGOUT");
        sendBroadcast(broadcastIntent);
    }


    /**
     * Finish current activity and go to login
     */
    private void goToLogin() {
        Intent navIntent = new Intent(BaseActivity.this, LoginActivity.class);
        finish();
        startActivity(navIntent);
    }
}
