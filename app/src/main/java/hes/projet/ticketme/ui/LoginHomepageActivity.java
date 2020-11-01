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
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.data.repository.UserRepository;
import hes.projet.ticketme.viewmodel.LoginViewModel;
import hes.projet.ticketme.viewmodel.TicketViewModel;
import hes.projet.ticketme.viewmodel.UserViewModel;


public class LoginHomepageActivity extends OptionsMenuActivity {

    private Button login;
    private EditText mail,password;
    private LoginViewModel viewModel;
    private boolean check = false;


    private EditText emailView;
    private EditText passwordView;
    private ProgressBar progressBar;

    private UserRepository repository;

    private final static String TAG = "Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_homepage);

        /*

        mail =  findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            checkEmail();

            }
        });

         */

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

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            passwordView.setText("");
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            repository.getUserByUsername(email, getApplication()).observe(LoginHomepageActivity.this, UserEntity -> {
                if (UserEntity != null) {
                    if (UserEntity.getPassword().equals(password)) {

                        //Toast.makeText(LoginHomepageActivity.this,"Check email et password ok",Toast.LENGTH_LONG).show();
                        /*
                        LA FAMEUSE CLASSE EN PLUS QU IL UTILISE.

                        SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
                        editor.putString(BaseActivity.PREFS_USER, clientEntity.getEmail());
                        editor.apply();
                         */


                        Intent intent = new Intent(this, UserManagementActivity.class);
                        startActivity(intent);

                        emailView.setText("");
                        passwordView.setText("");



                    } else {
                        passwordView.setError(getString(R.string.error_incorrect_password));
                        passwordView.requestFocus();
                        passwordView.setText("");
                    }
                    progressBar.setVisibility(View.GONE);
                } else {
                    emailView.setError(getString(R.string.error_invalid_email));
                    emailView.requestFocus();
                    passwordView.setText("");
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 2;
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean checkEmail(){


        LoginViewModel.Factory factory = new LoginViewModel.Factory(getApplication(), mail.getText().toString());
        ViewModelProvider provider = new ViewModelProvider(LoginHomepageActivity.this, factory);

        viewModel = provider.get(LoginViewModel.class);

        viewModel.getUser().observe(LoginHomepageActivity.this, userEntity -> {
            if (userEntity != null) {
                UserEntity user = userEntity;

                Log.i(TAG, "loaded user " + user.toString());
                //On recupere l idTicket et le subjet pour l affichage dans la liste de message.

                check=true;
            }
            else {
                Log.i(TAG, "User is null");
                check=false;
            }
        });

        return check;
    }



    public void clickNewUser(View view){

        Intent intent = new Intent(this,LoginNewActivity.class);
        startActivity(intent);
    }



    public void clickForgotPassword(View view) {
        Toast.makeText(LoginHomepageActivity.this,"Veuillez envoyer un mail à admin@ticketme.ch",Toast.LENGTH_LONG).show();
    }


}