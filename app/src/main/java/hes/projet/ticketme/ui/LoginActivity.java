package hes.projet.ticketme.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.data.repository.UserRepository;
import hes.projet.ticketme.util.Constants;
import hes.projet.ticketme.viewmodel.UserListViewModel;


public class LoginActivity extends BaseActivity {

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

        UserListViewModel.Factory factory = new UserListViewModel.Factory(getApplication(),getLoggedInUserId());
        ViewModelProvider provider = new ViewModelProvider(this, factory);
        UserListViewModel viewModel = provider.get(UserListViewModel.class);
        viewModel.getUsers().observe(this, users -> {
            if (users != null) {

                // listString = new ArrayList<>();
                //On recupere l idTicket et le subjet pour l affichage dans la liste de message.
                for(int i = 0; i < users.size(); i++){
                    UserEntity u = users.get(i);
                    if (u.getUsername().equals(email)) {
                        if (u.getPassword().equals(password)) {
                            // ok
                            login(u);
                        } else {
                            // password error
                            passwordView.setError(getString(R.string.error_incorrect_password));
                            passwordView.requestFocus();
                            passwordView.setText("");
                            return;
                        }
                    }
                }

                // User not found
                emailView.setError(getString(R.string.error_invalid_email));
                emailView.requestFocus();
                passwordView.setText("");
                return;
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