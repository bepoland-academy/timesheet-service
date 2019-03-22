package pl.betse.beontime.users.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.betse.beontime.users.bo.TimeEntryBo;
import pl.betse.beontime.users.exception.TimeEntryForUserWeekNotFound;
import pl.betse.beontime.users.exception.WeekDaysListIsEmptyException;
import pl.betse.beontime.users.mapper.TimeEntryMapper;
import pl.betse.beontime.users.model.WeekTimeEntryBody;
import pl.betse.beontime.users.service.TimeEntryService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/consultants")
public class UserTimeEntryController {
    private final TimeEntryService timeEntryService;
    private final TimeEntryMapper timeEntryMapper;

    public UserTimeEntryController(TimeEntryService timeEntryService, TimeEntryMapper timeEntryMapper) {
        this.timeEntryService = timeEntryService;
        this.timeEntryMapper = timeEntryMapper;
    }

    @GetMapping("/{userGuid}/week/{weekNumber}")
    public ResponseEntity<WeekTimeEntryBody> getWeekForUser(
            @PathVariable("userGuid") String userGuid,
            @PathVariable("weekNumber") String weekNumber) {
        List<TimeEntryBo> timeEntryBoList = timeEntryService.findByUserGuidAndWeak(userGuid, weekNumber);
        WeekTimeEntryBody weekTimeEntryBody = timeEntryMapper.fromTimeEntryBoToWeekTimeEntryBody(timeEntryBoList);
        return ResponseEntity.ok(weekTimeEntryBody);
    }

    @PostMapping("/{userGuid}/week/{weekNumber}")
    public ResponseEntity createWeekForUser(
            @RequestBody WeekTimeEntryBody weekTimeEntryBody,
            @PathVariable("userGuid") String userGuid,
            @PathVariable("weekNumber") String weekNumber) {

        if (weekTimeEntryBody.getWeekDays().isEmpty()) {
            log.error("Week days list is empty!");
            throw new WeekDaysListIsEmptyException();
        }

        List<TimeEntryBo> timeEntryBoList = weekTimeEntryBody.getWeekDays()
                .stream()
                .map(entry -> timeEntryMapper.fromWeekDayBodyToBo(entry, weekTimeEntryBody, userGuid, weekNumber))
                .collect(Collectors.toList());

        timeEntryService.verifyAllFileds(timeEntryBoList, weekNumber);
        timeEntryService.saveWholeWeekForUser(timeEntryBoList);


        return ResponseEntity.created(URI.create("asd")).build();
    }
}
