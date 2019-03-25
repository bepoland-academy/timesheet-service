package pl.betse.beontime.timesheet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.betse.beontime.timesheet.exception.IncorrectMonthFormatException;
import pl.betse.beontime.timesheet.mapper.TimeEntryMapper;
import pl.betse.beontime.timesheet.model.MonthTimeEntryBody;
import pl.betse.beontime.timesheet.validation.CreateTimeEntry;

import java.time.LocalDate;

@Slf4j
@RestController
@Validated(CreateTimeEntry.class)
@RequestMapping("/manager")
public class MonthTimeEntryController {

    private final TimeEntryMapper timeEntryMapper;

    public MonthTimeEntryController(TimeEntryMapper timeEntryMapper) {
        this.timeEntryMapper = timeEntryMapper;
    }

    @GetMapping("/managers/{managerGuid}/consultant/{userGuid}/month/{monthNumber}")
    public ResponseEntity<MonthTimeEntryBody> getMonthForUser(@PathVariable("managerGuid") String managerGuid,
                                                              @PathVariable("userGuid") String userGuid,
                                                              @PathVariable("monthNumber") String month) {
        month += "-01";
        checkMonthNumberFormat(month);
        LocalDate requestDate = LocalDate.parse(month);


        return ResponseEntity.ok().build();
    }

    private void checkMonthNumberFormat(String month) {
        if (!month.matches("[1-3]\\d{3}-[0-1]\\d")) {
            log.error("Incorrect week format or number");
            throw new IncorrectMonthFormatException();
        }
    }
}


