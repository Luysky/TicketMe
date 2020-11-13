package hes.projet.ticketme.ui;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.async.ticket.UpdateTicket;
import hes.projet.ticketme.data.async.user.UpdateUser;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.util.OnAsyncEventListener;
import hes.projet.ticketme.viewmodel.UserViewModel;

public class UserInterfaceActivity extends OptionsMenuActivity {

    private TextView username;
    private EditText editTextPassword;
    private CheckBox checkBoxAdmin;
    private UserViewModel viewModel;
    private UserEntity user;
    private String password;
    private boolean admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

       initMenu();
       initReturn();

       username = findViewById(R.id.editTextTextPersonName);
       editTextPassword = findViewById(R.id.editTextTextPassword2);
       checkBoxAdmin = findViewById(R.id.checkBox);

        Intent intent = getIntent();

        Long userId = intent.getLongExtra("userId", 0);

        showUser(userId);

    }

    private void showUser(Long userId) {
        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), userId);
        ViewModelProvider provider = new ViewModelProvider(this, factory);


        viewModel = provider.get(UserViewModel.class);

        viewModel.getUser().observe(this, userEntity -> {
            if (userEntity != null) {
                user = userEntity;

                Log.i(TAG, "loaded ticket " + user.toString());

                username.setText(user.getUsername());
                editTextPassword.setText(user.getPassword());
                checkBoxAdmin.setChecked(user.getAdmin());
            }
            else {
                Log.i(TAG, "User is null");
            }
        });


    }

    public void clickSaveUser(View viev){

        password = editTextPassword.getText().toString();
        admin = checkBoxAdmin.isChecked();

        user.setAdmin(admin);

        if(password.equals("")){
            Toast.makeText(UserInterfaceActivity.this,"Mot de passe non valide",Toast.LENGTH_SHORT).show();
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

            }
        }).execute(user);
    }


    @Override
    public void onReturn(View view){

        //Creation d un message d alerte en cas d utilisation du bouton retour
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInterfaceActivity.this);

        builder.setCancelable(true);
        builder.setTitle("Attention!");
        builder.setMessage("Les modifications ne seront pas enregistrées. Voulez-vous quitter la gestion utilisateur?");

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();

    }
}