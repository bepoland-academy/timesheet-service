package pl.betse.beontime.users.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.betse.beontime.users.exception.*;
import pl.betse.beontime.users.model.ErrorResponse;

@RestControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({TimeEntryForUserWeekNotFound.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendTimeEntryForUserNotFoundMessage() {
        return new ResponseEntity<>(new ErrorResponse("Time entry for user in requested week has been not found."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({WeekDaysListIsEmptyException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendWeekDaysListIsEmptyMessage() {
        return new ResponseEntity<>(new ErrorResponse("Week days shouldn't be an empty list!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({StatusNotFoundException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendStatusNotFoundMessage() {
        return new ResponseEntity<>(new ErrorResponse("Status not found!"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ProjectNotFoundException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendProjectNotFoundMessage() {
        return new ResponseEntity<>(new ErrorResponse("PROJECT GUID NOT FOUND"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({WeekNotFoundException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendWeekListNotFoundMessage() {
        return new ResponseEntity<>(new ErrorResponse("LIST OF WEEK NOT FOUND"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendUserNotFoundMessage() {
        return new ResponseEntity<>(new ErrorResponse("USER NOT FOUND"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IncorectWeekDaysException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendIncorectNUmberOfDaysMessage() {
        return new ResponseEntity<>(new ErrorResponse("NUMBER OF DAYS IN WEEK IS INCORRECT"), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder errors = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.append(error.getDefaultMessage()).append("\n");
        }
        return new ResponseEntity<>(new ErrorResponse(errors.toString()), HttpStatus.BAD_REQUEST);
    }
}
