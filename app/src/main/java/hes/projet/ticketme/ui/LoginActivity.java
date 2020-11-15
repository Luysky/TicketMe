package hes.projet.ticketme.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.repository.UserRepository;
import hes.projet.ticketme.util.Constants;


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


        repository.getUserByUsername(email, getApplication()).observe(LoginActivity.this, user -> {

            if (user == null) {
                emailView.setError(getString(R.string.error_invalid_email));
                emailView.requestFocus();
                passwordView.setText("");
                return;
            }

            Log.i(TAG, "loaded user " + user.toString());

            if (!user.getPassword().equals(password)) {
                passwordView.setError(getString(R.string.error_incorrect_password));
                passwordView.requestFocus();
                passwordView.setText("");
                return;
            }


            /*
             * Store logged in user as global var in application
             */

            SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_FILE, 0).edit();

            editor.putLong(Constants.PREF_USER_ID, user.getId());
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

            Intent intent = new Intent(this, TicketListActivity.class);
            startActivity(intent);
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