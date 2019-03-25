package pl.betse.beontime.timesheet.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.betse.beontime.timesheet.bo.StatusBo;
import pl.betse.beontime.timesheet.bo.TimeEntryBo;
import pl.betse.beontime.timesheet.entity.StatusEntity;
import pl.betse.beontime.timesheet.entity.TimeEntryEntity;
import pl.betse.beontime.timesheet.exception.*;
import pl.betse.beontime.timesheet.mapper.StatusMapper;
import pl.betse.beontime.timesheet.mapper.TimeEntryMapper;
import pl.betse.beontime.timesheet.repository.StatusRepository;
import pl.betse.beontime.timesheet.repository.TimeEntryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WeekTimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final TimeEntryMapper timeEntryMapper;
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;
    private final String NEW_STATUS = "NEW";


    public WeekTimeEntryService(TimeEntryRepository timeEntryRepository, TimeEntryMapper timeEntryMapper, StatusRepository statusRepository, StatusMapper statusMapper) {
        this.timeEntryRepository = timeEntryRepository;
        this.timeEntryMapper = timeEntryMapper;
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    public List<List<TimeEntryBo>> findByUserGuidAndWeek(String userGuid, String week) {
        List<TimeEntryBo> timeEntryBoList = timeEntryRepository.findByUserGuidAndWeek(userGuid, week).stream()
                .map(timeEntryMapper::fromEntityToBo)
                .collect(Collectors.toList());

        List<String> projectNames = new ArrayList<>();
        timeEntryBoList.forEach(entry -> {
            if (!projectNames.contains(entry.getProjectGuid())) {
                projectNames.add(entry.getProjectGuid());
            }
        });
        List<List<TimeEntryBo>> allProjectsForWeekList = new ArrayList<>();
        projectNames.forEach(projectGuid -> {
            allProjectsForWeekList.add(timeEntryRepository.findByUserGuidAndProjectGuidAndWeek(userGuid, projectGuid, week).stream()
                    .map(timeEntryMapper::fromEntityToBo)
                    .collect(Collectors.toList()));
        });
        if (allProjectsForWeekList.isEmpty()) {
            log.error("Time entry for user with guid = " + userGuid + " and week = " + week + " not exists.");
            throw new TimeEntryForUserWeekNotFound();
        }
        return allProjectsForWeekList;
    }

    public void saveWeekForUser(List<TimeEntryBo> timeEntryBoList) {
        List<TimeEntryEntity> timeEntryEntityList = validateWeekTimeEntry(timeEntryBoList);
        timeEntryEntityList.forEach(entry -> {
            entry.setStatusEntity(statusRepository.findByName(entry.getStatusEntity().getName()).get());
            timeEntryRepository.save(entry);
        });
    }

    public void editWeekHoursAndStatuses(List<TimeEntryBo> timeEntryBoList, String userGuid, String weekNumber) {
        List<TimeEntryEntity> incomingList = validateWeekTimeEntry(timeEntryBoList);
        List<TimeEntryEntity> databaseList = timeEntryRepository.findByUserGuidAndWeek(userGuid, weekNumber);
        databaseList.forEach(databaseEntry -> {
            for (TimeEntryEntity incomingEntity : incomingList) {
                if (databaseEntry.getEntryDate().equals(incomingEntity.getEntryDate())) {
                    StatusEntity statusEntity = statusRepository.findByName(incomingEntity.getStatusEntity().getName()).get();
                    databaseEntry.setStatusEntity(statusEntity);
                    databaseEntry.setHoursNumber(incomingEntity.getHoursNumber());
                    timeEntryRepository.save(databaseEntry);
                }
            }
        });
    }

    public void checkIfTimeEntriesExist(List<TimeEntryBo> timeEntryBoList, String week, String httpMethod) {
        timeEntryBoList.forEach(x -> {
            TimeEntryEntity timeEntryEntity = timeEntryMapper.fromBoToEntity(x);
            checkIfDateIsInCorrectWeekOfYear(week, timeEntryEntity.getEntryDate());
            if (httpMethod.equalsIgnoreCase(RequestMethod.POST.name())) {
                if (timeEntryRepository.existsByUserGuidAndProjectGuidAndEntryDate(timeEntryEntity.getUserGuid(), timeEntryEntity.getProjectGuid(), timeEntryEntity.getEntryDate())) {
                    log.error("Time entry for user " + timeEntryEntity.getUserGuid() + " and project " + timeEntryEntity.getProjectGuid() + " with date " + timeEntryEntity.getEntryDate() + " currently exist in database!");
                    throw new TimeEntryExistsInDatabase();
                }
            } else if (httpMethod.equalsIgnoreCase(RequestMethod.PUT.name())) {
                if (!timeEntryRepository.existsByUserGuidAndProjectGuidAndEntryDate(timeEntryEntity.getUserGuid(), timeEntryEntity.getProjectGuid(), timeEntryEntity.getEntryDate())) {
                    log.error("Time entry for user " + timeEntryEntity.getUserGuid() + " and project " + timeEntryEntity.getProjectGuid() + " with date " + timeEntryEntity.getEntryDate() + " currently NOT exist in database!");
                    throw new TimeEntryNotFound();
                }
            }
        });
    }

    private void checkIfDateIsInCorrectWeekOfYear(String week, LocalDate date) {
        int weekNumber = Integer.parseInt(week.substring(6, 8));
        if (date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) != weekNumber) {
            log.error("Date is not in that week of the year.");
            throw new BadWeekAndDateException();
        }
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

    private List<TimeEntryEntity> validateWeekTimeEntry(List<TimeEntryBo> timeEntryBoList) {
        List<TimeEntryEntity> timeEntryEntityList = new ArrayList<>();
        timeEntryBoList.forEach(x -> timeEntryEntityList.add(timeEntryMapper.fromBoToEntity(x)));
        List<StatusEntity> statusEntities = statusRepository.findAll();
        timeEntryEntityList.forEach(entry -> {
            if (!timeEntryRepository.existsByProjectGuid(entry.getProjectGuid())) {
                log.error("Project with guid" + entry.getProjectGuid() + " not found.");
                throw new ProjectNotFoundException();
            }
            if (!timeEntryRepository.existsByUserGuid(entry.getUserGuid())) {
                log.error("User with guid" + entry.getUserGuid() + "  not found.");
                throw new UserNotFoundException();
            }
            verifyStatusNames(entry.getStatusEntity(), statusEntities);
            verifyStatusAndHours(entry);
        });
        return timeEntryEntityList;
    }
}
