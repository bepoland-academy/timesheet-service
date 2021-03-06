package pl.betse.beontime.timesheet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.betse.beontime.timesheet.bo.MonthBo;
import pl.betse.beontime.timesheet.bo.TimeEntryBo;
import pl.betse.beontime.timesheet.mapper.MonthMapper;
import pl.betse.beontime.timesheet.mapper.TimeEntryMapper;
import pl.betse.beontime.timesheet.model.MonthBody;
import pl.betse.beontime.timesheet.model.MonthTimeEntryBody;
import pl.betse.beontime.timesheet.model.StatsBody;
import pl.betse.beontime.timesheet.service.TimeEntryService;
import pl.betse.beontime.timesheet.service.TimeEntryStatus;
import pl.betse.beontime.timesheet.utils.DateChecker;
import pl.betse.beontime.timesheet.validation.CreateTimeEntry;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@RestController
@Validated(CreateTimeEntry.class)
@RequestMapping("/consultants")
public class MonthConsultantTimeEntryController {

    private final TimeEntryMapper timeEntryMapper;
    private final TimeEntryService timeEntryService;
    private final DateChecker dateChecker;
    private final MonthMapper monthMapper;

    @Value("${api-prefix}")
    private String API_PREFIX;

    public MonthConsultantTimeEntryController(TimeEntryMapper timeEntryMapper, TimeEntryService timeEntryService, DateChecker dateChecker, MonthMapper monthMapper) {
        this.timeEntryMapper = timeEntryMapper;
        this.timeEntryService = timeEntryService;
        this.dateChecker = dateChecker;
        this.monthMapper = monthMapper;
    }

    @GetMapping("{consultantGuid}/months/{monthNumber}")
    public ResponseEntity<Resources<MonthTimeEntryBody>> getMonthForConsultant(@PathVariable("consultantGuid") String userGuid,
                                                                               @PathVariable("monthNumber") String month) {
        List<List<TimeEntryBo>> timeEntryBoList = timeEntryService.findByUserGuidAndMonth(userGuid,
                dateChecker.prepareRequestDateForService(month));
        List<MonthTimeEntryBody> monthTimeEntryBodyList = timeEntryBoList.stream()
                .map(timeEntryMapper::fromTimeEntryBoToMonthTimeEntryBody)
                .collect(Collectors.toList());

        monthTimeEntryBodyList.forEach(item -> item.setApprovedHours(timeEntryService.getStatistics(item.getConsultantId(), TimeEntryStatus.APPROVED.name())));
        monthTimeEntryBodyList.forEach(item -> item.setOffSite(true));

        Link link = constructLink(userGuid, month);
        return ResponseEntity.ok(new Resources<>(monthTimeEntryBodyList, link));
    }

    @GetMapping("{consultantGuid}/manager/{managerGuid}/months/status/{statusName}")
    public ResponseEntity<List<MonthBody>> getStatusByMonth(@PathVariable("consultantGuid") String userGuid,
                                                            @PathVariable("managerGuid") String managerGuid,
                                                            @PathVariable("statusName") String statusName) {
        List<MonthBo> monthBoList = timeEntryService.getMonthForUserByStatus(userGuid, statusName);
        List<MonthBody> monthBodyList = monthBoList.stream()
                .map(monthMapper::fromBoToBody)
                .collect(Collectors.toList());
        return ResponseEntity.ok(monthBodyList);
    }

    @GetMapping("{consultantGuid}/statistics")
    public ResponseEntity<StatsBody> getStatistics(
            @PathVariable("consultantGuid") String consultantGuid,
            @RequestParam("statusName") String statusName) {
        Integer hours = timeEntryService.getStatistics(consultantGuid, statusName);
        StatsBody statsBody = StatsBody.builder().hours(hours).build();

        return ResponseEntity.ok(statsBody);
    }

    private Link constructLink(String userGuid, String monthNumber) {
        URI location = linkTo(methodOn(MonthConsultantTimeEntryController.class).getMonthForConsultant(userGuid, monthNumber)).toUri();
        return new Link(API_PREFIX + location.getPath()).withSelfRel();
    }

//    private Link construckLinkForStatus(String userGuid, String statusName) {
//        URI location = linkTo(methodOn(MonthConsultantTimeEntryController.class).getStatusByMonth(userGuid, statusName)).toUri();
//        return new Link(API_PREFIX + location.getPath()).withSelfRel();
//    }

}
