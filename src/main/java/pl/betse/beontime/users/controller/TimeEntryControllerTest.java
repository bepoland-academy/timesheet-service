package pl.betse.beontime.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.betse.beontime.users.bo.TimeEntryBo;
import pl.betse.beontime.users.entity.StatusEntity;
import pl.betse.beontime.users.entity.TimeEntryEntity;
import pl.betse.beontime.users.mapper.TimeEntryMapper;
import pl.betse.beontime.users.model.WeekDayBody;
import pl.betse.beontime.users.model.WeekTimeEntryBody;
import pl.betse.beontime.users.repository.StatusRepository;
import pl.betse.beontime.users.repository.TimeEntryRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class TimeEntryControllerTest {

    private final TimeEntryRepository timeEntryRepository;
    private final TimeEntryMapper timeEntryMapper;
    private final StatusRepository statusRepository;

    public TimeEntryControllerTest(TimeEntryRepository timeEntryRepository, TimeEntryMapper timeEntryMapper, StatusRepository statusRepository) {
        this.timeEntryRepository = timeEntryRepository;
        this.timeEntryMapper = timeEntryMapper;
        this.statusRepository = statusRepository;
    }

    @GetMapping
    public ResponseEntity<TimeEntryBo> getTime() {
        TimeEntryEntity timeEntryEntity = timeEntryRepository.findById(1L).get();
        return ResponseEntity.ok(timeEntryMapper.fromEntityToBo(timeEntryEntity));
    }

    @GetMapping("/entity-to-weekbody")
    public ResponseEntity test12() {

        WeekDayBody weekDayBody =
                timeEntryMapper.fromBoToWeekDayBody(
                        timeEntryMapper.fromEntityToBo(
                                timeEntryRepository.findById(1L).get()));

        return ResponseEntity.ok(weekDayBody);
    }

    @PostMapping("/weekBody-to-entry/{userGuid}")
    public ResponseEntity<List<TimeEntryBo>> test13(@RequestBody WeekTimeEntryBody weekTimeEntryBody, @PathVariable("userGuid") String userGuid) {
        List<TimeEntryBo> timeEntryBoList = weekTimeEntryBody.getWeekDays()
                .stream()
                .map(entry -> timeEntryMapper.fromWeekDayBodyToBo(entry, weekTimeEntryBody, userGuid))
                .collect(Collectors.toList());
        return ResponseEntity.ok(timeEntryBoList);
    }


    @GetMapping("/weekBodyList")
    public ResponseEntity<WeekTimeEntryBody> getTimeEntryBody() {

        return ResponseEntity
                .ok(WeekTimeEntryBody.builder()
                        .build());


    }

    @PostMapping
    public ResponseEntity newUser(@RequestBody TimeEntryBo timeEntryBo) {

        StatusEntity statusEntity = statusRepository.findByStatus(timeEntryBo.getStatus()).orElseThrow(RuntimeException::new);

        TimeEntryEntity timeEntryEntity = timeEntryMapper.fromBoToEntity(timeEntryBo);
        timeEntryEntity.setStatusEntity(statusEntity);


        timeEntryRepository.save(timeEntryEntity);
        return ResponseEntity.ok().build();
    }

}
