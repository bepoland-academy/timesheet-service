package pl.betse.beontime.timesheet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.betse.beontime.timesheet.bo.MonthBo;
import pl.betse.beontime.timesheet.bo.TimeEntryBo;
import pl.betse.beontime.timesheet.mapper.TimeEntryMapper;
import pl.betse.beontime.timesheet.model.MonthTimeEntryBody;
import pl.betse.beontime.timesheet.service.TimeEntryService;
import pl.betse.beontime.timesheet.utils.DateChecker;
import pl.betse.beontime.timesheet.validation.CreateTimeEntry;

import java.net.URI;
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

    @Value("${api-prefix}")
    private String API_PREFIX;

    public MonthConsultantTimeEntryController(TimeEntryMapper timeEntryMapper,
                                              TimeEntryService timeEntryService,
                                              DateChecker dateChecker) {
        this.timeEntryMapper = timeEntryMapper;
        this.timeEntryService = timeEntryService;
        this.dateChecker = dateChecker;
    }

    @GetMapping("{consultantGuid}/months/{monthNumber}")
    public ResponseEntity<Resources<MonthTimeEntryBody>> getMonthForConsultant(@PathVariable("consultantGuid") String userGuid,
                                                                               @PathVariable("monthNumber") String month) {
        List<List<TimeEntryBo>> timeEntryBoList = timeEntryService.findByUserGuidAndMonth(userGuid,
                dateChecker.prepareRequestDateForService(month));
        List<MonthTimeEntryBody> monthTimeEntryBodyList = timeEntryBoList.stream()
                .map(timeEntryMapper::fromTimeEntryBoToMonthTimeEntryBody)
                .collect(Collectors.toList());
        Link link = constructLink(userGuid,month);
        return ResponseEntity.ok(new Resources<>(monthTimeEntryBodyList, link));
    }
    @GetMapping("{consultantGuid}/months/status/{statusName}")
    public ResponseEntity<Resources<MonthBo>> getStatusByMonth (@PathVariable("consultantGuid") String userGuid,
                                                     @PathVariable("statusName") String statusName){
        List<MonthBo> monthBoList = timeEntryService.getMonthForUserByStatus(userGuid,statusName);
        Link link = construckLinkForStatus(userGuid,statusName);
        return ResponseEntity.ok(new Resources<>(monthBoList, link));
    }

    private Link constructLink( String userGuid, String monthNumber) {
        URI location = linkTo(methodOn(MonthConsultantTimeEntryController.class).getMonthForConsultant(userGuid,monthNumber)).toUri();
        return new Link(API_PREFIX + location.getPath()).withSelfRel();
    }

    private Link construckLinkForStatus(String userGuid, String statusName){
        URI location = linkTo(methodOn(MonthConsultantTimeEntryController.class).getStatusByMonth(userGuid,statusName)).toUri();
        return new Link(API_PREFIX + location.getPath()).withSelfRel();
    }

}
