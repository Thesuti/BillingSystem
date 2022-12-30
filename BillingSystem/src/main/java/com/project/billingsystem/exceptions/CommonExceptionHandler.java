package com.project.billingsystem.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import com.project.billingsystem.dtos.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(EmailIsMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmailIsMissingException(){
        return new ErrorResponse("Email is missing");
    }

    @ExceptionHandler(UsernameIsMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUsernameIsMissingException(){
        return new ErrorResponse("Username is missing");
    }

    @ExceptionHandler(PasswordIsMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePasswordIsMissingException(){
        return new ErrorResponse("Password is missing");
    }

    @ExceptionHandler(EmailIsAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmailIsAlreadyUsedException(){
        return new ErrorResponse("You already have an account assigned to the given email");
    }

    @ExceptionHandler(UsernameIsAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUsernameIsAlreadyTakenException(){
        return new ErrorResponse("Username is already taken");
    }

    @ExceptionHandler(PasswordNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePasswordNotValidException(){
        return new ErrorResponse("Password is not valid. Password must contain 1 uppercase letter,1 number and at least be 8 character long ");
    }

}
