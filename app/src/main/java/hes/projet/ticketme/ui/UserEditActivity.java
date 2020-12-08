package hes.projet.ticketme.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.util.OnAsyncEventListener;
import hes.projet.ticketme.viewmodel.UserViewModel;

public class UserEditActivity extends BaseActivity {

    private TextView username;
    private CheckBox checkBoxAdmin;
    private UserEntity user;
    private boolean admin;
    private String checkAdmin;
    private UserViewModel viewModel;

    private static final String TAG = "UserEditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(this, R.layout.activity_user_interface, "Editer un utilisateur");
        initReturn();

        username = findViewById(R.id.userInterface_editTextTextPersonName);
        checkBoxAdmin = findViewById(R.id.userInterface_checkBox);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        initViewModel(userId);
        showUser(userId);
    }

    private void initViewModel(String userId) {
        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), userId);
        ViewModelProvider provider = new ViewModelProvider(this, factory);


        viewModel = provider.get(UserViewModel.class);

    }

    private void showUser(String userId) {

        viewModel.getUser().observe(this, userEntity -> {
            if (userEntity != null) {
                user = userEntity;

                Log.i(TAG, "loaded user " + user.toString());

                username.setText(user.getUsername());
                checkBoxAdmin.setChecked(user.getAdmin());

                checkAdmin = String.valueOf(checkBoxAdmin.isChecked());
                Log.i(TAG, "CheckAdmin: " + checkAdmin.toString());

            }
            else {
                Log.i(TAG, "User is null");
            }
        });
    }

    /**
     * Detect click on custom activity menu items
     *
     * @param item Clicked item in menu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_save_user) {
            clickSaveUser();
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }



    public void clickSaveUser(){

        admin = checkBoxAdmin.isChecked();

        user.setAdmin(admin);


        Log.i(TAG, "Modifier user: " + user.toString());

        viewModel.updateUser(user, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                displayMessage(getString(R.string.toast_updateUserError),1);
            }
        });
    }

    /**
     * Add activity specific menu to actionBar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_edit_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onReturn(View view) {


        /*
         * Check if form values have changed
         */

        boolean modifCheck = false;


        if(!checkAdmin.equals(String.valueOf(checkBoxAdmin.isChecked()))){
            modifCheck = true;
        }


        /*
         * If no changes detected, let user go back
         */


        if ((modifCheck ==false)) {
            finish();
            return;
        }


        /*
         * There are changed values, alert user about loosing changes
         */

        Runnable run = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };

        displayAlert(getString(R.string.alert_titleWarning),getString(R.string.alert_userChangesNotSaved),run);

    }
}