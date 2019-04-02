package pl.betse.beontime.timesheet.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.betse.beontime.timesheet.exception.IncorrectMonthFormatException;
import pl.betse.beontime.timesheet.exception.IncorrectWeekFormatException;

import java.time.LocalDate;

@Slf4j
@Component
public class DateChecker {

    public void checkWeekNumberFormat(String week) {
        if (!week.matches("[1-3]\\d{3}-W[0-5]\\d")) {
            log.error("Incorrect week format or number");
            throw new IncorrectWeekFormatException();
        }
    }

    public void checkMonthNumberFormat(String month) {
        if (!month.matches("[1-3]\\d{3}-[0-1]\\d-01")) {
            log.error("Incorrect month format or number");
            throw new IncorrectMonthFormatException();
        }
    }

    public LocalDate prepareRequestDateForService(String month) {
        month += "-01";
        checkMonthNumberFormat(month);
        return LocalDate.parse(month);
    }
}
