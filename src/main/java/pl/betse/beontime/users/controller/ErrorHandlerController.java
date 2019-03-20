package pl.betse.beontime.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.betse.beontime.users.exception.TimeEntryForUserWeekNotFound;
import pl.betse.beontime.users.model.ErrorResponse;

@RestControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({TimeEntryForUserWeekNotFound.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendTimeEntryForUserNotFoundMessage() {
        return new ResponseEntity<>(new ErrorResponse("Time entry for user not found"), HttpStatus.NOT_FOUND);
    }
}
