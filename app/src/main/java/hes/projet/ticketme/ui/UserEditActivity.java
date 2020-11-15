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
import hes.projet.ticketme.data.async.user.UpdateUser;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.util.OnAsyncEventListener;
import hes.projet.ticketme.viewmodel.UserViewModel;

public class UserEditActivity extends BaseActivity {

    private TextView username;
    private EditText editTextPassword;
    private CheckBox checkBoxAdmin;
    private UserEntity user;
    private String password;
    private boolean admin;
    private String checkPassword;
    private String checkAdmin;

    private static final String TAG = "UserEditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(this, R.layout.activity_user_interface, "Editer un utilisateur");
        initReturn();

        username = findViewById(R.id.userInterface_editTextTextPersonName);
        editTextPassword = findViewById(R.id.userInterface_editTextTextPassword);
        checkBoxAdmin = findViewById(R.id.userInterface_checkBox);

        Intent intent = getIntent();
        Long userId = intent.getLongExtra("userId", 0);
        showUser(userId);
    }

    private void showUser(Long userId) {
        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), userId);
        ViewModelProvider provider = new ViewModelProvider(this, factory);


        UserViewModel viewModel = provider.get(UserViewModel.class);

        viewModel.getUser().observe(this, userEntity -> {
            if (userEntity != null) {
                user = userEntity;

                Log.i(TAG, "loaded user " + user.toString());

                username.setText(user.getUsername());
                editTextPassword.setText(user.getPassword());
                checkBoxAdmin.setChecked(user.getAdmin());

                checkPassword = editTextPassword.getText().toString();
                Log.i(TAG, "CheckPassword: " + checkPassword.toString());

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

        password = editTextPassword.getText().toString();
        admin = checkBoxAdmin.isChecked();

        user.setAdmin(admin);

        if(password.equals("")){
            displayMessage(getString(R.string.toast_passwordInvalid),0);
            return;
        }
        user.setPassword(password);

        Log.i(TAG, "Modifier user: " + user.toString());

        new UpdateUser(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                displayMessage(getString(R.string.toast_updateUserError),1);
            }
        }).execute(user);
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


        boolean modifPassword = false;
        boolean modifCheck = false;

        if(!checkPassword.equals(editTextPassword.getText().toString())){
            modifPassword = true;
        }

        if(!checkAdmin.equals(String.valueOf(checkBoxAdmin.isChecked()))){
            modifCheck = true;
        }


        /*
         * If no changes detected, let user go back
         */

        if (((modifCheck || modifPassword)==false)) {
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