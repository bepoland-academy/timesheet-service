package pl.betse.beontime.timesheet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.betse.beontime.timesheet.service.TimeEntryService;

@Slf4j
@RestController
@RequestMapping("/timeEntry")
public class VerificationController {

    private final TimeEntryService timeEntryService;

    public VerificationController(TimeEntryService timeEntryService) {
        this.timeEntryService = timeEntryService;
    }

    @GetMapping("/projectExist")
    public boolean checkIfProjectInTimeEntryExists(@RequestParam("guid") String projectGuid) {
        return timeEntryService.checkIfProjectHasAnyEntries(projectGuid);
    }


}