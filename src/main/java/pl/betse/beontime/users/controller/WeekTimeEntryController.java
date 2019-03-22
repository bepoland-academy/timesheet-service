package pl.betse.beontime.users.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.betse.beontime.users.bo.TimeEntryBo;
import pl.betse.beontime.users.exception.IncorrectWeekFormatException;
import pl.betse.beontime.users.mapper.TimeEntryMapper;
import pl.betse.beontime.users.model.WeekTimeEntryBody;
import pl.betse.beontime.users.service.TimeEntryService;
import pl.betse.beontime.users.validation.CreateTimeEntry;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
    public ResponseEntity<Resources<WeekTimeEntryBody>> getWeekForUser(
            @PathVariable("userGuid") String userGuid,
            @PathVariable("weekNumber") String weekNumber) {
        checkWeekNumberFormat(weekNumber);
        List<List<TimeEntryBo>> timeEntryBoList = timeEntryService.findByUserGuidAndWeek(userGuid, weekNumber);
        List<WeekTimeEntryBody> weekTimeEntryBodyList = new ArrayList<>();
        timeEntryBoList.forEach(week -> {
            weekTimeEntryBodyList.add(timeEntryMapper.fromTimeEntryBoToWeekTimeEntryBody(week));
        });
        URI location = linkTo(methodOn(WeekTimeEntryController.class).getWeekForUser(userGuid, weekNumber)).toUri();
        Link link = new Link(location.toString(), "self");
        return ResponseEntity.ok(new Resources<>(weekTimeEntryBodyList, link));
    }

    @PostMapping("/{userGuid}/week/{weekNumber}")
    public ResponseEntity createWeekForUser(
            @RequestBody @Validated(CreateTimeEntry.class) WeekTimeEntryBody weekTimeEntryBody,
            @PathVariable("userGuid") String userGuid,
            @PathVariable("weekNumber") String weekNumber) {
        List<TimeEntryBo> timeEntryBoList = prepareDataForService(weekTimeEntryBody, userGuid, weekNumber);
        timeEntryService.saveWeekForUser(timeEntryBoList);
        URI location = linkTo(methodOn(WeekTimeEntryController.class).getWeekForUser(userGuid, weekNumber)).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{userGuid}/week/{weekNumber}")
    public ResponseEntity editWeekForUser(
            @RequestBody @Validated(CreateTimeEntry.class) WeekTimeEntryBody weekTimeEntryBody,
            @PathVariable("userGuid") String userGuid,
            @PathVariable("weekNumber") String weekNumber) {
        List<TimeEntryBo> timeEntryBoList = prepareDataForService(weekTimeEntryBody, userGuid, weekNumber);
        timeEntryService.editWeekHoursAndStatuses(timeEntryBoList, userGuid, weekNumber);
        URI location = linkTo(methodOn(WeekTimeEntryController.class).getWeekForUser(userGuid, weekNumber)).toUri();
        return ResponseEntity.created(location).build();
    }

    private List<TimeEntryBo> prepareDataForService(@Validated(CreateTimeEntry.class) @RequestBody WeekTimeEntryBody weekTimeEntryBody, @PathVariable("userGuid") String userGuid, @PathVariable("weekNumber") String weekNumber) {
        checkWeekNumberFormat(weekNumber);
        List<TimeEntryBo> timeEntryBoList = weekTimeEntryBody.getWeekDays().stream()
                .map(entry -> timeEntryMapper.fromWeekDayBodyToBo(entry, weekTimeEntryBody, userGuid, weekNumber))
                .collect(Collectors.toList());
        timeEntryService.checkIfTimeEntriesExist(timeEntryBoList, weekNumber);
        return timeEntryBoList;
    }

    private void addLinks(WeekTimeEntryBody weekTimeEntryBody, String userGuid) {
        weekTimeEntryBody.add(linkTo(methodOn(WeekTimeEntryController.class).getWeekForUser(userGuid, weekTimeEntryBody.getWeek())).withSelfRel());
    }

    private void checkWeekNumberFormat(String week) {
        if (!week.matches("[1-3]\\d{3}-W[1-5]\\d")) {
            log.error("Incorrect week format or number");
            throw new IncorrectWeekFormatException();
        }
    }


}
