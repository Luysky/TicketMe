package hes.projet.ticketme.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.data.repository.UserRepository;
import hes.projet.ticketme.util.Constants;
import hes.projet.ticketme.viewmodel.UserViewModel;


public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private EditText emailView;
    private EditText passwordView;
    private String password;
    private String email;

    private UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_homepage);

        repository = UserRepository.getInstance();

        // Set up the login form.
        emailView = findViewById(R.id.login_email);
        passwordView = findViewById(R.id.login_password);

        Button emailSignInButton = findViewById(R.id.login_btnLogin);
        emailSignInButton.setOnClickListener(view -> attemptLogin());
    }

    private void attemptLogin() {

        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        //String email = emailView.getText().toString();
        //String password = passwordView.getText().toString();
        email = emailView.getText().toString();
        password = passwordView.getText().toString();


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

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        /*
                         *  Email et mot de passe corrects, authentifié sur  FirebaseAuth
                         */
                        String userId = getLoggedInUserId();
                        storeLoggedInUser(userId);
                    } else {
                        displayMessage("Adresse email ou mot de passe incorrect - échec de l'identification!",1);
                    }
                });
    }

    private void storeLoggedInUser(String userId) {

        Log.i(TAG, "UserID " + userId);

        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), userId);
        ViewModelProvider provider = new ViewModelProvider(this, factory);

        UserViewModel viewModel = provider.get(UserViewModel.class);

        viewModel.getUser().observe(this, user -> {
            if (user != null) {

                if (! user.getActive()) {
                    displayMessage("Votre compte a été supprimé",0);
                    return;
                }

                Log.i(TAG, "loaded user " + user.toString());

                SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_FILE, 0).edit();
                editor.putBoolean(Constants.PREF_USER_ISADMIN, user.getAdmin());
                editor.apply();


                /*
                 * Go to ticket list
                 */

                Intent intent = new Intent(LoginActivity.this, TicketListActivity.class);
                startActivity(intent);


                /*
                 * Should not be necessary, but  just in case for security let's clear values in form
                 */

                emailView.setText("");
                passwordView.setText("");

            }
            else {
                Log.i(TAG, "User is null");
            }
        });
    }

    private void login(UserEntity user) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(user.getUsername(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        /*
                         * Store logged in user as global var in application
                         */

                        SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_FILE, 0).edit();

                        editor.putBoolean(Constants.PREF_USER_ISADMIN, user.getAdmin());
                        editor.apply();


                        /*
                         * Should not be necessary, but  just in case for security let's clear values in form
                         */

                        emailView.setText("");
                        passwordView.setText("");


                        /*
                         * Go to ticket list
                         */

                        Intent intent = new Intent(LoginActivity.this, TicketListActivity.class);
                        startActivity(intent);
                    }
                });


    }

    private boolean isPasswordValid(@NotNull String password) {
        return password.length() > 2;
    }


    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public void clickNewUser(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


    public void clickForgotPassword(View view) {
        displayMessage(getString(R.string.toast_emailToAdmin),1);
    }


}