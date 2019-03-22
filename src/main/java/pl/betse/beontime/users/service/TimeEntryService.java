package pl.betse.beontime.users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.betse.beontime.users.bo.StatusBo;
import pl.betse.beontime.users.bo.TimeEntryBo;
import pl.betse.beontime.users.entity.StatusEntity;
import pl.betse.beontime.users.entity.TimeEntryEntity;
import pl.betse.beontime.users.exception.*;
import pl.betse.beontime.users.mapper.StatusMapper;
import pl.betse.beontime.users.mapper.TimeEntryMapper;
import pl.betse.beontime.users.repository.StatusRepository;
import pl.betse.beontime.users.repository.TimeEntryRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final TimeEntryMapper timeEntryMapper;
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;
    private final String NEW_STATUS = "NEW";


    public TimeEntryService(TimeEntryRepository timeEntryRepository, TimeEntryMapper timeEntryMapper, StatusRepository statusRepository, StatusMapper statusMapper) {
        this.timeEntryRepository = timeEntryRepository;
        this.timeEntryMapper = timeEntryMapper;
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
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


    public void saveWeekForUser(List<TimeEntryBo> timeEntryBoList) {
        List<TimeEntryEntity> timeEntryEntityList = new ArrayList<>();
        timeEntryBoList.forEach(x -> timeEntryEntityList.add(timeEntryMapper.fromBoToEntity(x)));
        List<StatusEntity> statusEntities = statusRepository.findAll();
        timeEntryEntityList.forEach(entry -> {
            if (!timeEntryRepository.existsByProjectGuid(entry.getProjectGuid())) {
                log.error("Project with guid" + entry.getProjectGuid() + " has been not found");
                throw new ProjectNotFoundException();
            }
            if (!timeEntryRepository.existsByUserGuid(entry.getUserGuid())) {
                log.error("User with guid" + entry.getUserGuid() + " has been not found");
                throw new UserNotFoundException();
            }
            verifyStatusNames(entry.getStatusEntity(), statusEntities);
            verifyStatusAndHours(entry);
        });
        saveWeekTimeEntry(timeEntryEntityList);
    }

    private void saveWeekTimeEntry(List<TimeEntryEntity> timeEntryEntityList) {
        timeEntryEntityList.forEach(entry -> {
            entry.setStatusEntity(statusRepository.findByName(entry.getStatusEntity().getName()).get());
            timeEntryRepository.save(entry);
        });
    }

    private void verifyStatusNames(StatusEntity statusEntity, List<StatusEntity> statusEntities) {
        List<StatusBo> statusBos = statusEntities.stream()
                .map(statusMapper::fromEntityToBo)
                .collect(Collectors.toList());
        if (!statusBos.contains(statusMapper.fromEntityToBo(statusEntity))) {
            log.error("Status has been not found!");
            throw new StatusNotFoundException();
        }
    }

    private void verifyStatusAndHours(TimeEntryEntity timeEntryEntity) {
        if (timeEntryEntity.getStatusEntity().getName().equalsIgnoreCase(NEW_STATUS) && timeEntryEntity.getHoursNumber().compareTo(new BigDecimal(0)) > 0) {
            String message = ": " + NEW_STATUS + " should have 0 hours ONLY!";
            log.error(message);
            throw new WrongStatusException(message);
        }
    }

}
