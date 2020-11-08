package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import hes.projet.ticketme.data.entity.TicketEntity;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.viewmodel.CategoryViewModel;
import hes.projet.ticketme.viewmodel.TicketViewModel;
import hes.projet.ticketme.viewmodel.UserViewModel;

public class UserInterfaceActivity extends OptionsMenuActivity {

    private TextView username;
    private EditText password;
    private CheckBox admin;
    private UserViewModel viewModel;
    private UserEntity user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

       initMenu();
       initReturn();

       username = findViewById(R.id.editTextTextPersonName);
       password = findViewById(R.id.editTextTextPassword2);
       admin = findViewById(R.id.checkBox);

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
                password.setText(user.getPassword());
                admin.setChecked(user.getAdmin());
            }
            else {
                Log.i(TAG, "User is null");
            }
        });


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