package hes.projet.ticketme.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hes.projet.ticketme.R;
import hes.projet.ticketme.data.async.ticket.DeleteTicket;
import hes.projet.ticketme.data.async.user.DeleteUser;
import hes.projet.ticketme.data.entity.UserEntity;
import hes.projet.ticketme.util.OnAsyncEventListener;
import hes.projet.ticketme.viewmodel.UserListViewModel;

public class UserListActivity extends BaseActivity {

    private Toolbar menuToolBar;
    private List<UserEntity> users = new ArrayList<>();
    private List<String> listString = new ArrayList();
    private ArrayAdapter adapter;
    private ListView listView;

    private UserListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(this, R.layout.activity_user_management, "Liste des utilisateurs");
        initDrawer();

        listView = findViewById(R.id.userManagement_listViewUser);


        UserListViewModel.Factory factory = new UserListViewModel.Factory(getApplication());
        ViewModelProvider provider = new ViewModelProvider(this, factory);
        viewModel = provider.get(UserListViewModel.class);
        viewModel.getUsers().observe(this, userEntities -> {
            if (userEntities != null) {
                users = userEntities;

                listString = new ArrayList<>();
                //On recupere l idTicket et le subjet pour l affichage dans la liste de message.
                for(int i = 0; i < users.size(); i++){
                    listString.add(users.get(i).toString());
                }

                //Ces lignes servent a la mise en place d une liste deroulante.
                adapter = new ArrayAdapter(UserListActivity.this, android.R.layout.simple_list_item_1, listString);
                listView.setAdapter(adapter);
            }
        });

        //Action lorsque l on click sur un objet de la liste.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserEntity u = users.get(position);
                Intent intent = new Intent(UserListActivity.this, UserEditActivity.class);
                intent.putExtra("userId", u.getId());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UserListActivity.this);

                builder.setCancelable(true);
                builder.setTitle(R.string.alert_titleWarning);
                builder.setMessage(R.string.alert_userDelete);

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        UserEntity userToDelete = users.get(position);
                        new DeleteUser(getApplication(), new OnAsyncEventListener() {
                            @Override
                            public void onSuccess() {
                                /*
                                 * Don't do anything, user list will be updated thanks to the view model.
                                 */
                            }

                            @Override
                            public void onFailure(Exception e) {
                                displayMessage(getString(R.string.toast_deleteUserError),1);
                            }
                        }).execute(userToDelete);
                    }
                });
                builder.show();
                return true;
            }
        });
    }
}