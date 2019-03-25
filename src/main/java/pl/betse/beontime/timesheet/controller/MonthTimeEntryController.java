package pl.betse.beontime.timesheet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.betse.beontime.timesheet.bo.TimeEntryBo;
import pl.betse.beontime.timesheet.exception.IncorrectMonthFormatException;
import pl.betse.beontime.timesheet.mapper.TimeEntryMapper;
import pl.betse.beontime.timesheet.model.MonthTimeEntryBody;
import pl.betse.beontime.timesheet.service.TimeEntryService;
import pl.betse.beontime.timesheet.validation.CreateTimeEntry;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@RestController
@Validated(CreateTimeEntry.class)
@RequestMapping("/managers")
public class MonthTimeEntryController {

    private final TimeEntryMapper timeEntryMapper;
    private final TimeEntryService timeEntryService;

    public MonthTimeEntryController(TimeEntryMapper timeEntryMapper, TimeEntryService timeEntryService) {
        this.timeEntryMapper = timeEntryMapper;
        this.timeEntryService = timeEntryService;
    }

    @GetMapping("/{managerGuid}/consultant/{userGuid}/month/{monthNumber}")
    public ResponseEntity<Resources<MonthTimeEntryBody>> getMonthForUser(@PathVariable("managerGuid") String managerGuid,
                                                                         @PathVariable("userGuid") String userGuid,
                                                                         @PathVariable("monthNumber") String month) {
        List<List<TimeEntryBo>> timeEntryBoList = timeEntryService.findByUserAndMonth(userGuid, prepareRequestDateForService(month));
        List<MonthTimeEntryBody> monthTimeEntryBodyList = new ArrayList<>();
        timeEntryBoList.forEach(monthEntry ->
            monthTimeEntryBodyList.add(timeEntryMapper.fromTimeEntryBoToMonthTimeEntryBody(monthEntry))
        );
        URI location = linkTo(methodOn(MonthTimeEntryController.class).getMonthForUser(managerGuid, userGuid, month)).toUri();
        Link link = new Link(location.toString(), "self");
        return ResponseEntity.ok(new Resources<>(monthTimeEntryBodyList, link));
    }

    @PutMapping("/{managerGuid}/consultant/{userGuid}/month/{monthNumber}")
    public ResponseEntity editMonthForUser(@RequestBody @Validated(CreateTimeEntry.class) MonthTimeEntryBody monthTimeEntryBody,
                                           @PathVariable("managerGuid") String managerGuid,
                                           @PathVariable("userGuid") String userGuid,
                                           @PathVariable("monthNumber") String month,
                                           HttpServletRequest httpServletRequest) {
        LocalDate requestedDate = prepareRequestDateForService(month);
        List<TimeEntryBo> timeEntryBoList = prepareDataForService(monthTimeEntryBody);
        timeEntryService.checkIfDateHasCorrectMonth(timeEntryBoList, requestedDate);
        timeEntryService.checkIfTimeEntriesExist(timeEntryBoList, httpServletRequest.getMethod());
        timeEntryService.editMonthStatusesAndComments(timeEntryBoList, userGuid, requestedDate);
        return ResponseEntity.ok().build();
    }

    private List<TimeEntryBo> prepareDataForService(MonthTimeEntryBody monthTimeEntryBody) {
        return monthTimeEntryBody.getMonthDays().stream()
                .map(entry -> timeEntryMapper.fromMonthDayBodyToBo(entry, monthTimeEntryBody))
                .collect(Collectors.toList());
    }

    private void checkMonthNumberFormat(String month) {
        if (!month.matches("[1-3]\\d{3}-[0-1]\\d-01")) {
            log.error("Incorrect month format or number");
            throw new IncorrectMonthFormatException();
        }
    }

    private LocalDate prepareRequestDateForService(String month) {
        month += "-01";
        checkMonthNumberFormat(month);
        return LocalDate.parse(month);
    }
}


