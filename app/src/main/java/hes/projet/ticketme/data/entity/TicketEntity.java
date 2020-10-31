package hes.projet.ticketme.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;


@Entity(tableName = "tickets",
        foreignKeys = @ForeignKey(
                entity = CategoryEntity.class,
                parentColumns = "id",
                childColumns = "category",
                onDelete = ForeignKey.CASCADE
        ))
public class TicketEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "category")
    private Long categoryId;

    @Ignore
    private String categoryName;

    private String subject;

    private String message;


    @Ignore
    public TicketEntity() {
    }

    public TicketEntity(@NonNull Long categoryId,
                        @NonNull String subject,
                        @NonNull String message) {

        this.categoryId = categoryId;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long category) {
        this.categoryId = categoryId;
    }

    public String toString() {
        return "Ticket #" + id + " - " + subject;
    }
}