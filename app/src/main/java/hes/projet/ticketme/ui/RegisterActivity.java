package hes.projet.ticketme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.data.repository.UserRepository;
import hes.projet.ticketme.util.OnAsyncEventListener;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "LoginNewActivity";
    private EditText email,password;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        email = findViewById(R.id.loginNew_editTextTextEmailAddress);
        password = findViewById(R.id.loginNew_editTextTextPassword);
        button = findViewById(R.id.loginNew_btnValider);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailInput = email.getText().toString();
                String passInput = password.getText().toString();


                if(validateEmailAddress(email)==true){
                    Log.i(TAG, "valid email ");
                    if (validatePassword(password)==true){
                        Log.i(TAG, "valid password ");

                        UserEntity user = new UserEntity(emailInput, passInput,false);

                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                                user.getUsername(),
                                user.getPassword()
                        ).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {

                                Log.i(TAG, "Longueur chaine " + FirebaseAuth.getInstance().getCurrentUser().getUid().length() );
                                Log.i(TAG, "Id " + FirebaseAuth.getInstance().getCurrentUser().getUid() );

                                user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                insertUser(user);
                            } else {

                                onRegisterFailure(user);
                            }
                        });
                    }
                }
            }
        });
    }

    private void insertUser(UserEntity user) {
        UserRepository.getInstance().insert(user, new OnAsyncEventListener(){

            @Override
            public void onSuccess() {
                Log.i(TAG, "User created " + user.toString());

                Log.i(TAG, "Longueur chaine " + user.getId().length() );
                Log.i(TAG, "Longueur chaine " + FirebaseAuth.getInstance().getCurrentUser().getUid().length() );

                Intent intent = new Intent(RegisterActivity.this, TicketListActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Exception e) {
                onRegisterFailure(user);
            }
        });

    }

    private void onRegisterFailure(UserEntity user) {
        Log.i(TAG, "User not created " + user.toString());
        displayMessage(getString(R.string.toast_createUserError),1);

    }

    private boolean validateEmailAddress (EditText email){
        String emailInput = email.getText().toString();

        if(!emailInput.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            displayMessage(getString(R.string.toast_emailCorrect),0);
            return true;
        }else {
            displayMessage(getString(R.string.toast_emailIncorrect),0);
            return false;
        }
    }

    private boolean validatePassword (EditText password){
        String pass = password.getText().toString();

        if(pass.length()<8){
            displayMessage(getString(R.string.toast_emailTooShort),0);
            return false;
        }
        if(!pass.matches(".*[!@#$%^&*+?-]*")){
            displayMessage(getString(R.string.toast_emailNotConform),0);
            return false;
        }
        return true;

    }
}
