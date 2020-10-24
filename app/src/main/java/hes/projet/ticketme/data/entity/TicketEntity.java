package hes.projet.ticketme.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;


@Entity(tableName = "tickets", primaryKeys = {"id"})
public class TicketEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String subject;

    private String message;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
