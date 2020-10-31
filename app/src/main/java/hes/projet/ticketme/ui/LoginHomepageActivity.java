package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hes.projet.ticketme.R;


public class LoginHomepageActivity extends OptionsMenuActivity {

    Button login;
    EditText mail,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_homepage);

        mail = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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