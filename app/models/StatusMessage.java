package models;

public class StatusMessage {

    public String status;
    public String message;

    public StatusMessage(Integer statusCode, String message) {
        this.status = Integer.toString(statusCode);
        this.message = message;
    }

}
