package pl.betse.beontime.users.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.betse.beontime.users.bo.TimeEntryBo;
import pl.betse.beontime.users.mapper.TimeEntryMapper;
import pl.betse.beontime.users.model.WeekDayBody;
import pl.betse.beontime.users.model.WeekTimeEntryBody;
import pl.betse.beontime.users.service.TimeEntryService;
import pl.betse.beontime.users.validation.CreateTimeEntry;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/consultants")
@Validated(CreateTimeEntry.class)
public class WeekTimeEntryController {
    private final TimeEntryService timeEntryService;
    private final TimeEntryMapper timeEntryMapper;

    public WeekTimeEntryController(TimeEntryService timeEntryService, TimeEntryMapper timeEntryMapper) {
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
            @RequestBody @Validated(CreateTimeEntry.class) WeekTimeEntryBody weekTimeEntryBody,
            @PathVariable("userGuid") String userGuid,
            @PathVariable("weekNumber") String weekNumber) {



        return ResponseEntity.created(URI.create("asd")).build();
    }


}
