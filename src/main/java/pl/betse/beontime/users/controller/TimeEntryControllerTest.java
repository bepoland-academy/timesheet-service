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

import java.util.ArrayList;
import java.util.Collections;
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
    public ResponseEntity<WeekTimeEntryBody> getTime() {


        return ResponseEntity.ok().build();
    }

    @GetMapping("/entity-to-weekbody")
    public ResponseEntity test12() {

        WeekDayBody weekDayBody =
                timeEntryMapper.fromBoToWeekDayBody(
                        timeEntryMapper.fromEntityToBo(
                                timeEntryRepository.findById(1L).get()));

        return ResponseEntity.ok(weekDayBody);
    }

    @GetMapping("/takeTimeByGuidAndWeek")
    public ResponseEntity<WeekTimeEntryBody> test43() {
        List<TimeEntryEntity> timeEntryEntityList = timeEntryRepository.findByUserGuidAndWeek("7041cb03-200d-457c-84a9-a4881527448f", "2019-W11");
        List<TimeEntryBo> timeEntryBoList = timeEntryEntityList.stream()
                .map(timeEntryMapper::fromEntityToBo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(timeEntryMapper.fromTimeEntryBosToWeekTimeEntryBody(timeEntryBoList));
    }

    @PostMapping("/weekBody-to-entry/{userGuid}")
    public ResponseEntity<WeekTimeEntryBody> test13(@RequestBody WeekTimeEntryBody weekTimeEntryBody, @PathVariable("userGuid") String userGuid) {
        List<TimeEntryBo> timeEntryBoList = weekTimeEntryBody.getWeekDays()
                .stream()
                .map(entry -> timeEntryMapper.fromWeekDayBodyToBo(entry, weekTimeEntryBody, userGuid))
                .collect(Collectors.toList());


        return ResponseEntity.ok(timeEntryMapper.fromTimeEntryBosToWeekTimeEntryBody(timeEntryBoList));
//        return ResponseEntity.ok(timeEntryBoList
//                .stream()
//                .map(entry -> timeEntryMapper.fromBoToEntity(entry)).collect(Collectors.toList()));
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
