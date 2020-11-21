package hes.projet.ticketme.data.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserEntity {

    private String id;

    private String username;

    private boolean admin;

    public UserEntity(boolean admin){
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

        return (getAdmin() ? " (admin)" : "");
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("admin",admin);
        return result;
    }
}
