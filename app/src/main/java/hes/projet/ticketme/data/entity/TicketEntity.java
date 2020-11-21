package hes.projet.ticketme.data.entity;


import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class TicketEntity {

    private String id;

    private String userId;

    private String category;

    private String subject;

    private String message;
    private int status;

    public TicketEntity() {
    }

    public TicketEntity(@NonNull String category,
                        @NonNull String subject,
                        @NonNull String message) {

        this.category = category;
        this.subject = subject;
        this.message = message;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String toString() {
        return subject;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("category",category);
        result.put("subject",subject);
        result.put("message",message);
        return result;
    }

    @Exclude
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
