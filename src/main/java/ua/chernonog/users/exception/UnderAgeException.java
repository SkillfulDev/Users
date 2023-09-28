package ua.chernonog.users.exception;

public class UnderAgeException extends RuntimeException{
    public UnderAgeException() {
        super("Input Age less than 18");
    }
}
