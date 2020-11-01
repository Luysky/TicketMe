package hes.projet.ticketme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.data.repository.UserRepository;
import hes.projet.ticketme.viewmodel.LoginViewModel;
import hes.projet.ticketme.viewmodel.TicketViewModel;
import hes.projet.ticketme.viewmodel.UserViewModel;


public class LoginHomepageActivity extends OptionsMenuActivity {

    private Button login;
    private EditText mail,password;
    private LoginViewModel viewModel;

    private final static String TAG = "Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_homepage);

        mail =  findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginViewModel.Factory factory = new LoginViewModel.Factory(getApplication(), mail.getText().toString());
                ViewModelProvider provider = new ViewModelProvider(LoginHomepageActivity.this, factory);


                viewModel = provider.get(LoginViewModel.class);

                viewModel.getUser().observe(LoginHomepageActivity.this, userEntity -> {
                    if (userEntity != null) {
                        UserEntity user = userEntity;

                        Log.i(TAG, "loaded user " + user.toString());
                        //On recupere l idTicket et le subjet pour l affichage dans la liste de message.
                    }
                    else {
                        Log.i(TAG, "User is null");
                    }
                });


            }
        });




    }



    public void clickNewUser(View view){

        Intent intent = new Intent(this,LoginNewActivity.class);
        startActivity(intent);
    }



    public void clickForgotPassword(View view) {
        Toast.makeText(LoginHomepageActivity.this,"Veuillez envoyer un mail à admin@ticketme.ch",Toast.LENGTH_LONG).show();
    }


}