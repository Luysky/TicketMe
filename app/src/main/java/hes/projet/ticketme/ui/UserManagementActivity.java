package hes.projet.ticketme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.User;

public class UserManagementActivity extends OptionsMenuActivity {

    private Toolbar menuToolBar;
    private List<User> listUser = new ArrayList<>();
    private List<String> listString = new ArrayList();
    private ArrayAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        listView = findViewById(R.id.list_viewUser);


        //Utilisation de l action bar
        menuToolBar = findViewById(R.id.toolbar);
        setTitle(null);
        setSupportActionBar(menuToolBar);


        //Afficher et utiliser le bouton retour
        menuToolBar.setNavigationIcon(R.drawable.ic_return);
        menuToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        User user1 = new User(1,"tom@gmail.com","1234",true);
        User user2 = new User (2,"ben@gmail.com","1234",true);
        User user3 = new User (3, "toto@gmail.com","123",false);
        User user4 = new User (4, "tata@gmail.com","123",false);
        User user5 = new User (5, "titi@gmail.com","123",false);
        User user6 = new User (6, "tutu@gmail.com","123",false);

        listUser.add(user1);
        listUser.add(user2);
        listUser.add(user3);
        listUser.add(user4);
        listUser.add(user5);
        listUser.add(user6);

        //On recupere l idUser et le username pour l affichage dans la liste de message.
        for(int i=0; i<listUser.size();i++){
            listString.add(listUser.get(i).userToString());
        }

        //Ces lignes servent a la mise en place d une liste deroulante.
        adapter = new ArrayAdapter(UserManagementActivity.this, android.R.layout.simple_list_item_1, listString);
        listView.setAdapter(adapter);


    }

    //Bouton ajouter confirm
    public void clickConfirm(View view){
        Intent intent = new Intent(this, UserManagementActivity.class);
        startActivity(intent);
    }

}