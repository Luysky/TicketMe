package hes.projet.ticketme.data.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserEntity {

    private String id;

    private String username;

    private String password;

    private boolean admin;

    public UserEntity(){

    }

    public UserEntity(boolean admin){
        this.admin = admin;
    }

    public UserEntity(String username, String password, boolean admin){
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {

        return username + (getAdmin() ? " (admin)" : "");
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        //result.put("password", password);
        result.put("admin", admin);
        return result;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
