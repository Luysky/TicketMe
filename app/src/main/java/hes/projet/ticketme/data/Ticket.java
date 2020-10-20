package hes.projet.ticketme.data;

public class Ticket {

    private int ticketNumber;
    private String subject;
    private String message;

    public Ticket(int ticketNumber,String subject,String message){
        this.ticketNumber=ticketNumber;
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

    public String ticketToString(){

        return "Ticket n°"+ticketNumber+" - "+subject;
    }
}
