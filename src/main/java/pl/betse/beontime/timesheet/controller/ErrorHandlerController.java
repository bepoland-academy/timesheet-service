package pl.betse.beontime.timesheet.controller;

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
import pl.betse.beontime.timesheet.exception.*;
import pl.betse.beontime.timesheet.model.ErrorResponse;

@RestControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({TimeEntryForUserWeekNotFound.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendTimeEntryForUserNotFoundMessage() {
        return new ResponseEntity<>(new ErrorResponse("TIME ENTRY FOR USER IN REQUESTED WEEK HAS BEEN NOT FOUND."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({StatusNotFoundException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendStatusNotFoundMessage() {
        return new ResponseEntity<>(new ErrorResponse("STATUS NOT FOUND."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ProjectNotFoundException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendProjectNotFoundMessage() {
        return new ResponseEntity<>(new ErrorResponse("PROJECT GUID NOT FOUND."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendUserNotFoundMessage() {
        return new ResponseEntity<>(new ErrorResponse("USER NOT FOUND."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({WrongStatusException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendWrongStatusMessage(WrongStatusException e) {
        return new ResponseEntity<>(new ErrorResponse("WRONG STATUS FOR ENTRY" + e.getAdditionalMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TimeEntryExistsInDatabase.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendTimeEntryExistsInDatabaseMessage() {
        return new ResponseEntity<>(new ErrorResponse("TIME ENTRY CURRENTLY EXISTS IN DATABASE"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({BadWeekAndDateException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendBadWeekAndDateExceptionMessage() {
        return new ResponseEntity<>(new ErrorResponse("CURRENT DATE IS NOT IN CHOSEN WEEK"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IncorrectWeekFormatException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendIncorrectWeekFormatExceptionMessage() {
        return new ResponseEntity<>(new ErrorResponse("INCORRECT WEEK FORMAT OR NUMBER."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TimeEntryNotFound.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendTimeEntryNotFoundMessage() {
        return new ResponseEntity<>(new ErrorResponse("TIME ENTRY FOR USER AND WEEK NOT FOUND."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadMonthAndDateException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendBadMonthAndDateMessage() {
        return new ResponseEntity<>(new ErrorResponse("DATE IS NOT IN THE MONTH OF THE YEAR."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IncorrectMonthFormatException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendIncorrectMonthFormatMessage() {
        return new ResponseEntity<>(new ErrorResponse("DATE FORMAT IS NOT CORRECT."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TimeEntryForUserMonthNotFound.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendTimeEntryForUserMonthNotFoundMessage() {
        return new ResponseEntity<>(new ErrorResponse("TIME ENTRY FOR USER AND MONTH NOT FOUND."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DoubledDateException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> sendDoubledDateExceptionMessage() {
        return new ResponseEntity<>(new ErrorResponse("DATE CANNOT BE DOUBLED FOR ONE MONTH OR WEEK IN ONE PROJECT DURING ENTRY CREATION OR EDITION"), HttpStatus.BAD_REQUEST);
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
