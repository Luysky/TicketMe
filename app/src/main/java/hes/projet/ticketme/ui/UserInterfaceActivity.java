package hes.projet.ticketme.ui;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import hes.projet.ticketme.R;

public class UserInterfaceActivity extends BaseActivity {

    private Toolbar menuToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

       initMenu();
       initReturn();
    }
}