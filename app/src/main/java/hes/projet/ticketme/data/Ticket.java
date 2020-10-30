package hes.projet.ticketme.data;

public class Ticket {

    private int ticketNumber;
    private String category;
    private String subject;
    private String message;


    public Ticket(int ticketNumber, String category, String subject, String message){
        this.ticketNumber=ticketNumber;
        this.category=category;
        this.subject=subject;
        this.message=message;
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

<<<<<<< HEAD
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
=======
    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String ticketToString(){

        return "Ticket n°"+ticketNumber+" - "+subject;
>>>>>>> ben
    }
}
