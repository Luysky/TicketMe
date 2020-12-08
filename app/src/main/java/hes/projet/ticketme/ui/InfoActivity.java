package hes.projet.ticketme.ui;

import android.os.Bundle;

import hes.projet.ticketme.R;

public class InfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(this, R.layout.activity_info, "About");
        initReturn();
    }

}
