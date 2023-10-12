package ua.chernonog.users.exception;

public class EmailFormatException extends RuntimeException{
    public EmailFormatException() {
        super("Incorrect mail format");
    }
}
