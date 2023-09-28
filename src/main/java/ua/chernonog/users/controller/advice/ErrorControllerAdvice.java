package ua.chernonog.users.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.chernonog.users.exception.EmailFormatException;
import ua.chernonog.users.exception.UnderAgeException;
import ua.chernonog.users.exception.UserNotFoundException;

@RestControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler(UnderAgeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDTO error(UnderAgeException ex) {
        return new ErrorDTO(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorDTO error(UserNotFoundException ex) {
        return new ErrorDTO(ex.getMessage());
    }
    @ExceptionHandler(EmailFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDTO error(EmailFormatException ex) {
        return new ErrorDTO(ex.getMessage());
    }

    record ErrorDTO(String error) {
    }

}
