package pl.betse.beontime.timesheet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.betse.beontime.timesheet.bo.TimeEntryBo;
import pl.betse.beontime.timesheet.mapper.TimeEntryMapper;
import pl.betse.beontime.timesheet.model.WeekTimeEntryBody;
import pl.betse.beontime.timesheet.model.WeekTimeEntryBodyList;
import pl.betse.beontime.timesheet.service.TimeEntryService;
import pl.betse.beontime.timesheet.service.TimeEntryStatus;
import pl.betse.beontime.timesheet.utils.DateChecker;
import pl.betse.beontime.timesheet.validation.CreateTimeEntry;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
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
    private final DateChecker dateChecker;

    @Value("${api-prefix}")
    private String API_PREFIX;

    public WeekTimeEntryController(TimeEntryService timeEntryService, TimeEntryMapper timeEntryMapper, DateChecker dateChecker) {
        this.timeEntryService = timeEntryService;
        this.timeEntryMapper = timeEntryMapper;
        this.dateChecker = dateChecker;
    }

    /**
     * This method return week time sheet of chosen user
     *
     * @param userGuid   user identifier
     * @param weekNumber number of week
     * @return user time sheet
     */
    @GetMapping("/{userGuid}/weeks/{weekNumber}")
    public ResponseEntity<Resources<WeekTimeEntryBody>> getWeekForUser(
            @PathVariable("userGuid") String userGuid,
            @PathVariable("weekNumber") String weekNumber) {
        dateChecker.checkWeekNumberFormat(weekNumber);
        List<List<TimeEntryBo>> timeEntryBoList = timeEntryService.findByUserGuidAndWeek(userGuid, weekNumber);
        List<WeekTimeEntryBody> weekTimeEntryBodyList = timeEntryBoList.stream()
                .map(timeEntryMapper::fromTimeEntryBoToWeekTimeEntryBody)
                .collect(Collectors.toList());

        weekTimeEntryBodyList.forEach(item -> item.setOffSite(true));
        Link link = constructLink(userGuid, weekNumber);
        weekTimeEntryBodyList.forEach(item -> item.add(constructDeleteLink(userGuid, weekNumber, item.getProjectId())));
        return ResponseEntity.ok(new Resources<>(weekTimeEntryBodyList, link));
    }

    /**
     * This method create for user new week time sheet
     *
     * @param weekTimeEntryBodyList representation list of time objects
     * @param userGuid              user identifier
     * @param weekNumber            number of week
     * @param httpServletRequest    http request
     * @return this method return new time sheet for week
     */
    @PostMapping("/{userGuid}/weeks/{weekNumber}")
    public ResponseEntity createWeekForUser(
            @RequestBody @Validated(CreateTimeEntry.class) WeekTimeEntryBodyList weekTimeEntryBodyList,
            @PathVariable("userGuid") String userGuid,
            @PathVariable("weekNumber") String weekNumber,
            HttpServletRequest httpServletRequest) throws URISyntaxException {
        weekTimeEntryBodyList.getWeekTimeEntryBodyList().forEach(weekTimeEntryBody -> {
            List<TimeEntryBo> timeEntryBoList = getTimeEntryBosWithBasicVerification(userGuid,
                    weekNumber,
                    httpServletRequest,
                    weekTimeEntryBody);
            timeEntryService.verifyStatusesBeforeCreatingNewEntry(timeEntryBoList);
        });
        weekTimeEntryBodyList.getWeekTimeEntryBodyList().forEach(weekTimeEntryBody -> {
            List<TimeEntryBo> timeEntryBoList = mapWeekTimeEntryBodyToBoList(weekTimeEntryBody,
                    userGuid,
                    weekNumber);
            timeEntryService.saveWeekForUser(timeEntryBoList);
        });
        URI location = linkTo(methodOn(WeekTimeEntryController.class).getWeekForUser(userGuid, weekNumber)).toUri();
        return ResponseEntity.created(new URI(API_PREFIX + location.getPath())).build();
    }

    /**
     * this method update week time sheet for user
     *
     * @param weekTimeEntryBodyList representation list of time objects
     * @param userGuid              user identifier
     * @param weekNumber            number of week
     * @param httpServletRequest    http request
     * @return this method return updated time sheet for week
     */
    @PutMapping("{userGuid}/weeks/{weekNumber}")
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

    /**
     * this method delete week time entry for user
     *
     * @param userGuid    user identifier
     * @param weekNumber  number of week
     * @param projectGuid project identifier
     * @return deleted time entry
     */
    @DeleteMapping("/{userGuid}/weeks/{weekNumber}")
    public ResponseEntity deleteWeekForUser(@PathVariable("userGuid") String userGuid,
                                            @PathVariable("weekNumber") String weekNumber,
                                            @RequestParam(name = "projectGuid", required = false, defaultValue = "") String projectGuid) {
        if (!projectGuid.isEmpty()) {
            timeEntryService.deleteWeekTimeEntriesFromProject(userGuid, weekNumber, projectGuid);
        } else {
            timeEntryService.deleteWeekTimeEntries(userGuid, weekNumber);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * @param userGuid
     * @param weekNumber
     * @param httpServletRequest
     * @param weekTimeEntryBody
     * @return
     */
    private List<TimeEntryBo> getTimeEntryBosWithBasicVerification(String userGuid,
                                                                   String weekNumber,
                                                                   HttpServletRequest httpServletRequest,
                                                                   WeekTimeEntryBody weekTimeEntryBody) {
        List<TimeEntryBo> timeEntryBoList = mapWeekTimeEntryBodyToBoList(weekTimeEntryBody, userGuid, weekNumber);
        timeEntryService.checkIfDateIsInCorrectWeekOfYear(timeEntryBoList, weekNumber);
        timeEntryService.checkIfTimeEntriesExist(timeEntryBoList, httpServletRequest.getMethod());
        timeEntryService.verifyThatWeekDatesAreUnique(timeEntryBoList);
        return timeEntryBoList;
    }


    private List<TimeEntryBo> mapWeekTimeEntryBodyToBoList(WeekTimeEntryBody weekTimeEntryBody,
                                                           String userGuid,
                                                           String weekNumber) {
        dateChecker.checkWeekNumberFormat(weekNumber);
        return weekTimeEntryBody.getWeekDays().stream()
                .map(entry -> timeEntryMapper.fromWeekDayBodyToBo(entry, weekTimeEntryBody, userGuid, weekNumber))
                .collect(Collectors.toList());
    }

    private Link constructLink(String userGuid, String weekNumber) {
        URI location = linkTo(methodOn(WeekTimeEntryController.class).getWeekForUser(userGuid, weekNumber)).toUri();
        return new Link(API_PREFIX + location.getPath()).withSelfRel();
    }

    private Link constructDeleteLink(String userGuid, String weekNumber, String projectGuid) {
        URI location = linkTo(methodOn(WeekTimeEntryController.class).deleteWeekForUser(userGuid, weekNumber, projectGuid)).toUri();
        return new Link(API_PREFIX + location.getPath() + "?projectGuid=" + projectGuid).withRel("DELETE");
    }

}