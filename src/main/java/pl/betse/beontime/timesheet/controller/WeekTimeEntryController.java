package pl.betse.beontime.timesheet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.betse.beontime.timesheet.bo.TimeEntryBo;
import pl.betse.beontime.timesheet.exception.IncorrectWeekFormatException;
import pl.betse.beontime.timesheet.mapper.TimeEntryMapper;
import pl.betse.beontime.timesheet.model.WeekTimeEntryBody;
import pl.betse.beontime.timesheet.model.WeekTimeEntryBodyList;
import pl.betse.beontime.timesheet.service.TimeEntryService;
import pl.betse.beontime.timesheet.validation.CreateTimeEntry;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/consultants")
public class WeekTimeEntryController {
    private final TimeEntryService timeEntryService;
    private final TimeEntryMapper timeEntryMapper;

    public WeekTimeEntryController(TimeEntryService timeEntryService, TimeEntryMapper timeEntryMapper) {
        this.timeEntryService = timeEntryService;
        this.timeEntryMapper = timeEntryMapper;
    }

    @GetMapping("/{userGuid}/weeks/{weekNumber}")
    public ResponseEntity<Resources<WeekTimeEntryBody>> getWeekForUser(
            @PathVariable("userGuid") String userGuid,
            @PathVariable("weekNumber") String weekNumber) {
        checkWeekNumberFormat(weekNumber);
        List<List<TimeEntryBo>> timeEntryBoList = timeEntryService.findByUserGuidAndWeek(userGuid, weekNumber);
        List<WeekTimeEntryBody> weekTimeEntryBodyList = timeEntryBoList.stream()
                .map(timeEntryMapper::fromTimeEntryBoToWeekTimeEntryBody)
                .collect(Collectors.toList());
        URI location = linkTo(methodOn(WeekTimeEntryController.class).getWeekForUser(userGuid, weekNumber)).toUri();
        Link link = new Link(location.toString(), "self");
        return ResponseEntity.ok(new Resources<>(weekTimeEntryBodyList, link));
    }

    @PostMapping("/{userGuid}/weeks/{weekNumber}")
    public ResponseEntity createWeekForUser(
            @RequestBody @Validated(CreateTimeEntry.class) WeekTimeEntryBodyList weekTimeEntryBodyList,
            @PathVariable("userGuid") String userGuid,
            @PathVariable("weekNumber") String weekNumber,
            HttpServletRequest httpServletRequest) {
        weekTimeEntryBodyList.getWeekTimeEntryBodyList().forEach(weekTimeEntryBody -> {
            List<TimeEntryBo> timeEntryBoList = getTimeEntryBosWithBasicVerification(
                    userGuid,
                    weekNumber, httpServletRequest, weekTimeEntryBody);
            timeEntryService.verifyStatusesBeforeCreatingNewEntry(timeEntryBoList);
        });
        weekTimeEntryBodyList.getWeekTimeEntryBodyList().forEach(weekTimeEntryBody -> {
            List<TimeEntryBo> timeEntryBoList = mapWeekTimeEntryBodyToBoList(weekTimeEntryBody, userGuid, weekNumber);
            timeEntryService.saveWeekForUser(timeEntryBoList);
        });
        URI location = linkTo(methodOn(WeekTimeEntryController.class).getWeekForUser(userGuid, weekNumber)).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{userGuid}/weeks/{weekNumber}")
    public ResponseEntity editWeekForUser(
            @RequestBody @Validated(CreateTimeEntry.class) WeekTimeEntryBodyList weekTimeEntryBodyList,
            @PathVariable("userGuid") String userGuid,
            @PathVariable("weekNumber") String weekNumber,
            HttpServletRequest httpServletRequest) {
        weekTimeEntryBodyList.getWeekTimeEntryBodyList().forEach(weekTimeEntryBody -> {
            List<TimeEntryBo> timeEntryBoList = getTimeEntryBosWithBasicVerification(userGuid,
                    weekNumber,
                    httpServletRequest,
                    weekTimeEntryBody);
            timeEntryService.verifyStatusesBeforeAddingComment(timeEntryBoList);
        });
        weekTimeEntryBodyList.getWeekTimeEntryBodyList().forEach(weekTimeEntryBody -> {
            List<TimeEntryBo> timeEntryBoList = mapWeekTimeEntryBodyToBoList(weekTimeEntryBody, userGuid, weekNumber);
            timeEntryService.editWeekHoursAndStatuses(timeEntryBoList, userGuid, weekNumber);
        });
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userGuid}/weeks/{weekNumber}")
    public ResponseEntity deleteWeekForUser(@RequestBody @Validated(CreateTimeEntry.class) WeekTimeEntryBodyList weekTimeEntryBodyList,
                                            @PathVariable("userGuid") String userGuid,
                                            @PathVariable("weekNumber") String weekNumber,
                                            HttpServletRequest httpServletRequest) {
        weekTimeEntryBodyList.getWeekTimeEntryBodyList().forEach(weekTimeEntryBody -> {
            List<TimeEntryBo> timeEntryBoList = getTimeEntryBosWithBasicVerification(userGuid,
                    weekNumber,
                    httpServletRequest,
                    weekTimeEntryBody);
            timeEntryService.verifyStatusesBeforeDeleting(timeEntryBoList);
        });
        weekTimeEntryBodyList.getWeekTimeEntryBodyList().forEach(weekTimeEntryBody -> {
            List<TimeEntryBo> timeEntryBoList = mapWeekTimeEntryBodyToBoList(weekTimeEntryBody, userGuid, weekNumber);
            timeEntryService.deleteWeekTimeEntry(timeEntryBoList, userGuid, weekNumber);
        });
        return ResponseEntity.ok().build();
    }

    private List<TimeEntryBo> getTimeEntryBosWithBasicVerification(
            String userGuid,
            String weekNumber,
            HttpServletRequest httpServletRequest, WeekTimeEntryBody weekTimeEntryBody) {
        List<TimeEntryBo> timeEntryBoList = mapWeekTimeEntryBodyToBoList(weekTimeEntryBody, userGuid, weekNumber);
        timeEntryService.checkIfDateIsInCorrectWeekOfYear(timeEntryBoList, weekNumber);
        timeEntryService.checkIfTimeEntriesExist(timeEntryBoList, httpServletRequest.getMethod());
        timeEntryService.verifyThatWeekDatesAreUnique(timeEntryBoList);
        return timeEntryBoList;
    }


    private List<TimeEntryBo> mapWeekTimeEntryBodyToBoList(WeekTimeEntryBody weekTimeEntryBody, String userGuid, String weekNumber) {
        checkWeekNumberFormat(weekNumber);
        return weekTimeEntryBody.getWeekDays().stream()
                .map(entry -> timeEntryMapper.fromWeekDayBodyToBo(entry, weekTimeEntryBody, userGuid, weekNumber))
                .collect(Collectors.toList());
    }


    private void checkWeekNumberFormat(String week) {
        if (!week.matches("[1-3]\\d{3}-W[0-5]\\d")) {
            log.error("Incorrect week format or number");
            throw new IncorrectWeekFormatException();
        }
    }
}
