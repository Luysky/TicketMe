package hes.projet.ticketme.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import hes.projet.ticketme.MainActivity;
import hes.projet.ticketme.R;
import hes.projet.ticketme.data.AppDatabase;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.data.repository.UserRepository;
import hes.projet.ticketme.viewmodel.LoginViewModel;
import hes.projet.ticketme.viewmodel.TicketViewModel;
import hes.projet.ticketme.viewmodel.UserViewModel;


public class LoginHomepageActivity extends OptionsMenuActivity {

    private static final String TAG = "LoginHomepageActivity";

    private EditText emailView;
    private EditText passwordView;

    private UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_homepage);

        repository = UserRepository.getInstance();

        // Set up the login form.
        emailView = findViewById(R.id.etEmail);
        passwordView = findViewById(R.id.etPassword);

        Button emailSignInButton = findViewById(R.id.btnLogin);
        emailSignInButton.setOnClickListener(view -> attemptLogin());
    }

    private void attemptLogin() {

        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            passwordView.setText("");
            passwordView.requestFocus();
            return;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            emailView.requestFocus();
            return;
        }

        if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            emailView.requestFocus();
            return;
        }


        repository.getUserByUsername(email, getApplication()).observe(LoginHomepageActivity.this, UserEntity -> {

            if (UserEntity == null) {
                emailView.setError(getString(R.string.error_invalid_email));
                emailView.requestFocus();
                passwordView.setText("");
            }

            Log.i(TAG, "loaded user " + UserEntity.toString());

            if (!UserEntity.getPassword().equals(password)) {
                passwordView.setError(getString(R.string.error_incorrect_password));
                passwordView.requestFocus();
                passwordView.setText("");
            }

            /**
             * TODO Store logged in user as global var in application
             */
            /*
            LA FAMEUSE CLASSE EN PLUS QU IL UTILISE.

            SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
            editor.putString(BaseActivity.PREFS_USER, clientEntity.getEmail());
            editor.apply();
             */


            Intent intent = new Intent(this, TicketListActivity.class);
            startActivity(intent);

            emailView.setText("");
            passwordView.setText("");
        });
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 2;
    }


    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public void clickNewUser(View view) {
        Intent intent = new Intent(this,LoginNewActivity.class);
        startActivity(intent);
    }


    public void clickForgotPassword(View view) {
        Toast.makeText(LoginHomepageActivity.this,"Veuillez envoyer un mail à admin@ticketme.ch",Toast.LENGTH_LONG).show();
    }


}