package hes.projet.ticketme.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import hes.projet.ticketme.data.dao.UserDao;

//@Entity(tableName = "users")
@Entity(tableName = "users", indices = {@Index(value = {"username"}, unique = true)})
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String username;

    private String password;

    private boolean admin;

    public UserEntity(String username, String password, boolean admin){
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String toString() {

        return username + (getAdmin() ? " (admin)" : "");
    }
}
