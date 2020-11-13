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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import hes.projet.ticketme.R;
import hes.projet.ticketme.util.Constants;


public class BaseActivity extends AppCompatActivity {
    public static final String TAG = "OptionsMenuActivity";

    protected Toolbar menuToolBar;
    DrawerLayout drawer;

    public void initView(BaseActivity currActivity, int viewId)
    {
        setContentView(viewId);

        initMainMenu(currActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        Log.i(TAG, "onCreateOptionsMenu");

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu, menu);

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

    public void initMenuListener(){
        //Afficher et utiliser le bouton retour
        menuToolBar.setNavigationIcon(R.drawable.ic_return);
        menuToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReturn(v);
            }
        });
    }

    protected void requireLoggedInUser() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive","Logout in progress");
                //At this point you should start the login activity and finish this one

                Intent navIntent = new Intent(BaseActivity.this, LoginHomepageActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                finish();
                startActivity(navIntent);
            }
        }, intentFilter);
    }

    public void initMainMenu(BaseActivity currActivity) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if (navigationView == null)
            return;

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.menu_user_list) {
                    Intent intent = new Intent(currActivity, UserManagementActivity.class);
                    startActivity(intent);
                } else if(id == R.id.menu_ticket_list_open) {
                    Intent intent = new Intent(currActivity, TicketListActivity.class);
                    startActivity(intent);
                } else if(id == R.id.menu_ticket_list_closed) {
                    Intent intent = new Intent(currActivity, TicketListActivity.class);
                    intent.putExtra("statusFilter", 1);
                    startActivity(intent);
                }

                //drawer.closeDrawer(GravityCompat.START);
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


    public void onReturn(View v) {
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()){

            case R.id.action_settings:
                displayMessage("Settings option selected");
                return true;

            case R.id.action_info:

                intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_logout:

                logout();
                return true;

            default:
                    return super.onOptionsItemSelected(item);

        }


    }

    private void displayMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_FILE, 0).edit();
        editor.remove(Constants.PREF_USER_ID);
        editor.remove(Constants.PREF_USER_ISADMIN);
        editor.apply();


        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
        sendBroadcast(broadcastIntent);
    }
}
