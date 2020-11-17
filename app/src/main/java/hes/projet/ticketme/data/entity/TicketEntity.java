package hes.projet.ticketme.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;


@Entity(tableName = "tickets",
    foreignKeys = {
        @ForeignKey(
                entity = CategoryEntity.class,
                parentColumns = "id",
                childColumns = "category",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = UserEntity.class,
            parentColumns = "id",
            childColumns = "user",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class TicketEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "category")
    private Long categoryId;

    private String subject;

    private String message;

    @ColumnInfo(name = "user")
    private Long userId;

    private int status;

    @Ignore
    public TicketEntity() {
    }

    public TicketEntity(@NonNull Long userId,
                        @NonNull Long categoryId,
                        @NonNull String subject,
                        @NonNull String message) {

        this.userId = userId;
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

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String toString() {
        return subject;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
