package hes.projet.ticketme.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;


@Entity(tableName = "tickets")
public class TicketEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String category;

    private String subject;

    private String message;


    @Ignore
    public TicketEntity() {
    }

    public TicketEntity(@NonNull String category,
                        @NonNull String subject,
                        @NonNull String message) {

        this.category = category;
        this.subject = subject;
        this.message = message;
    }
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
