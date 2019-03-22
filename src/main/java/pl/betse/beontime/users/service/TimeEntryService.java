package pl.betse.beontime.users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.betse.beontime.users.bo.TimeEntryBo;
import pl.betse.beontime.users.entity.StatusEntity;
import pl.betse.beontime.users.entity.TimeEntryEntity;
import pl.betse.beontime.users.exception.*;
import pl.betse.beontime.users.mapper.TimeEntryMapper;
import pl.betse.beontime.users.repository.StatusRepository;
import pl.betse.beontime.users.repository.TimeEntryRepository;

import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final TimeEntryMapper timeEntryMapper;
    private final StatusRepository statusRepository;

    public TimeEntryService(TimeEntryRepository timeEntryRepository, TimeEntryMapper timeEntryMapper, StatusRepository statusRepository) {
        this.timeEntryRepository = timeEntryRepository;
        this.timeEntryMapper = timeEntryMapper;
        this.statusRepository = statusRepository;
    }

    public List<TimeEntryBo> findByUserGuidAndWeak(String guid, String week) {
        List<TimeEntryBo> timeEntryBoList = timeEntryRepository.findByUserGuidAndWeek(guid, week).stream()
                .map(timeEntryMapper::fromEntityToBo)
                .collect(Collectors.toList());
        if (timeEntryBoList.isEmpty()) {
            log.error("Time entry for user with guid = " + guid + " and week = " + week + " does not exists.");
            throw new TimeEntryForUserWeekNotFound();
        }
        return timeEntryBoList;
    }


    public void saveWholeWeekForUser(List<TimeEntryBo> timeEntryBoList) {
        List<TimeEntryEntity> timeEntryEntityList = new ArrayList<>();
        timeEntryBoList.forEach(x -> timeEntryEntityList.add(timeEntryMapper.fromBoToEntity(x)));
        timeEntryEntityList.forEach(x -> saveOneTimeEntry(x));
    }

    private void saveOneTimeEntry(TimeEntryEntity timeEntryEntity) {
        StatusEntity statusEntity = statusRepository.findByStatus(timeEntryEntity.getStatusEntity().getStatus()).orElseThrow(StatusNotFoundException::new);
        timeEntryEntity.setStatusEntity(statusEntity);
        timeEntryRepository.save(timeEntryEntity);
    }

}
