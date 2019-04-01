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

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final TimeEntryMapper timeEntryMapper;
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;
    private final String SAVED_STATUS = "SAVED";
    private final String REJECTED_STATUS = "REJECTED";
    private final String SUBMITTED_STATUS = "SUBMITTED";


    public TimeEntryService(TimeEntryRepository timeEntryRepository, TimeEntryMapper timeEntryMapper, StatusRepository statusRepository, StatusMapper statusMapper) {
        this.timeEntryRepository = timeEntryRepository;
        this.timeEntryMapper = timeEntryMapper;
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    public List<List<TimeEntryBo>> findByUserGuidAndWeek(String userGuid, String week) {
        List<List<TimeEntryBo>> allProjectsForWeekList = new ArrayList<>();
        List<TimeEntryBo> timeEntryBoList = timeEntryRepository.findByUserGuidAndWeekOrderByEntryDate(userGuid, week).stream()
                .map(timeEntryMapper::fromEntityToBo)
                .collect(Collectors.toList());
        List<String> projectGuidList = resolveProjectGuidFromBoList(timeEntryBoList);
        for (String projectGuid : projectGuidList) {
            allProjectsForWeekList.add(
                    timeEntryRepository.findByUserGuidAndProjectGuidAndWeekOrderByEntryDate(userGuid, projectGuid, week).stream()
                            .map(timeEntryMapper::fromEntityToBo)
                            .collect(Collectors.toList())
            );
        }
        if (allProjectsForWeekList.isEmpty()) {
            log.error("Time entry for user with guid = " + userGuid + " and week = " + week + " not exists.");
            throw new TimeEntryForUserWeekNotFound();
        }
        return allProjectsForWeekList;
    }

    public boolean checkIfProjectHasAnyEntries(String projectGuid){
        return timeEntryRepository.existsByProjectGuid(projectGuid);
    }

    public List<List<TimeEntryBo>> findByUserGuidAndMonth(String userGuid, LocalDate requestDate) {
        List<List<TimeEntryBo>> allProjectForMonth = new ArrayList<>();
        List<TimeEntryBo> timeEntryBoList = timeEntryRepository.findByUserGuidAndMonth(userGuid, requestDate).stream()
                .map(timeEntryMapper::fromEntityToBo)
                .collect(Collectors.toList());
        List<String> projectsGuidList = resolveProjectGuidFromBoList(timeEntryBoList);
        for (String projectGuid : projectsGuidList) {
            allProjectForMonth.add(
                    timeEntryRepository.findByUserGuidAndProjectGuidAndMonth(userGuid, projectGuid, requestDate).stream()
                            .map(timeEntryMapper::fromEntityToBo)
                            .collect(Collectors.toList())
            );
        }
        if (allProjectForMonth.isEmpty()) {
            log.error("Time entry for user with guid = " + userGuid + "and month " + requestDate + "not exists.");
            throw new TimeEntryForUserMonthNotFound();
        }
        return allProjectForMonth;
    }

    public void saveWeekForUser(List<TimeEntryBo> timeEntryBoList) {
        List<TimeEntryEntity> timeEntryEntityList = validateWeekTimeEntry(timeEntryBoList);
        for (TimeEntryEntity entry : timeEntryEntityList) {
            entry.setStatusEntity(statusRepository.findByName(entry.getStatusEntity().getName()).get());
            timeEntryRepository.save(entry);
        }
    }

    public void editWeekHoursAndStatuses(List<TimeEntryBo> timeEntryBoList, String userGuid, String weekNumber) {
        List<TimeEntryEntity> incomingList = validateWeekTimeEntry(timeEntryBoList);
        List<TimeEntryEntity> databaseList = timeEntryRepository.findByUserGuidAndWeekOrderByEntryDate(userGuid, weekNumber);
        for (TimeEntryEntity databaseEntry : databaseList) {
            for (TimeEntryEntity incomingEntity : incomingList) {
                if (databaseEntry.getEntryDate().equals(incomingEntity.getEntryDate()) && databaseEntry.getProjectGuid().equals(incomingEntity.getProjectGuid())) {
                    StatusEntity statusEntity = statusRepository.findByName(incomingEntity.getStatusEntity().getName()).get();
                    databaseEntry.setStatusEntity(statusEntity);
                    databaseEntry.setHoursNumber(incomingEntity.getHoursNumber());
                    timeEntryRepository.save(databaseEntry);
                }
            }
        }
    }

    public void deleteWeekTimeEntry(List<TimeEntryBo> timeEntryBoList, String userGuid, String weekNumber) {
        List<TimeEntryEntity> incomingList = validateWeekTimeEntry(timeEntryBoList);
        List<TimeEntryEntity> databaseList = timeEntryRepository.findByUserGuidAndWeekOrderByEntryDate(userGuid, weekNumber);
        for (TimeEntryEntity databaseEntry : databaseList) {
            for (TimeEntryEntity incomingEntity : incomingList) {
                if (databaseEntry.getEntryDate().equals(incomingEntity.getEntryDate())) {
                    timeEntryRepository.delete(databaseEntry);
                }
            }
        }
    }

    public void editMonthStatusesAndComments(List<TimeEntryBo> timeEntryBoList, String userGuid, LocalDate localDate) {
        List<TimeEntryEntity> incomingList = validateWeekTimeEntry(timeEntryBoList);
        List<TimeEntryEntity> databaseList = timeEntryRepository.findByUserGuidAndMonth(userGuid, localDate);
        for (TimeEntryEntity databaseEntry : databaseList) {
            for (TimeEntryEntity incomingEntity : incomingList) {
                if (databaseEntry.getEntryDate().equals(incomingEntity.getEntryDate()) && databaseEntry.getProjectGuid().equals(incomingEntity.getProjectGuid())) {
                    StatusEntity statusEntity = statusRepository.findByName(incomingEntity.getStatusEntity().getName()).get();
                    databaseEntry.setStatusEntity(statusEntity);
                    databaseEntry.setComment(incomingEntity.getComment());
                    timeEntryRepository.save(databaseEntry);
                }
            }
        }
    }

    public void checkIfTimeEntriesExist(List<TimeEntryBo> timeEntryBoList, String httpMethod) {
        for (TimeEntryBo timeEntryBo : timeEntryBoList) {
            TimeEntryEntity timeEntryEntity = timeEntryMapper.fromBoToEntity(timeEntryBo);
            if (httpMethod.equalsIgnoreCase(RequestMethod.POST.name())) {
                if (timeEntryRepository.existsByUserGuidAndProjectGuidAndEntryDate(timeEntryEntity.getUserGuid(), timeEntryEntity.getProjectGuid(), timeEntryEntity.getEntryDate())) {
                    log.error("Time entry for user " + timeEntryEntity.getUserGuid() + " and project " + timeEntryEntity.getProjectGuid() + " with date " + timeEntryEntity.getEntryDate() + " currently exist in database!");
                    throw new TimeEntryExistsInDatabase();
                }
            } else if (httpMethod.equalsIgnoreCase(RequestMethod.PUT.name()) || httpMethod.equalsIgnoreCase(RequestMethod.DELETE.name())) {
                if (!timeEntryRepository.existsByUserGuidAndProjectGuidAndEntryDate(timeEntryEntity.getUserGuid(), timeEntryEntity.getProjectGuid(), timeEntryEntity.getEntryDate())) {
                    log.error("Time entry for user " + timeEntryEntity.getUserGuid() + " and project " + timeEntryEntity.getProjectGuid() + " with date " + timeEntryEntity.getEntryDate() + " currently NOT exist in database!");
                    throw new TimeEntryNotFound();
                }
            }
        }
    }

    public void checkIfDateIsInCorrectWeekOfYear(List<TimeEntryBo> timeEntryBoList, String week) {
        int weekNumber = Integer.parseInt(week.substring(6, 8));
        for (TimeEntryBo timeEntryBo : timeEntryBoList) {
            if (timeEntryBo.getEntryDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) != weekNumber) {
                log.error("Date is not in that week of the year.");
                throw new BadWeekAndDateException();
            }
        }
    }

    public void checkIfDateHasCorrectMonth(List<TimeEntryBo> timeEntryBoList, LocalDate requestedDate) {
        int monthNumber = requestedDate.getMonthValue();
        for (TimeEntryBo timeEntryBo : timeEntryBoList) {
            if (timeEntryBo.getEntryDate().getMonthValue() != monthNumber) {
                log.error("Date is not in that month of the year.");
                throw new BadMonthAndDateException();
            }
        }
    }

    public void verifyStatusesBeforeCreatingNewEntry(List<TimeEntryBo> timeEntryBoList) {
        for (TimeEntryBo timeEntryBo : timeEntryBoList) {
            if (!timeEntryBo.getStatus().equalsIgnoreCase(SAVED_STATUS) && !timeEntryBo.getStatus().equalsIgnoreCase(SUBMITTED_STATUS)) {
                String message = ": Only entries with '" + SAVED_STATUS + "' or '" + SUBMITTED_STATUS + "' status can be created as new.";
                log.error(message);
                throw new WrongStatusException(message);
            }
        }
    }

    public void verifyStatusesBeforeDeleting(List<TimeEntryBo> timeEntryBoList) {
        for (TimeEntryBo timeEntryBo : timeEntryBoList) {
            if (!timeEntryBo.getStatus().equalsIgnoreCase(SAVED_STATUS)) {
                String message = ": Only entries with '" + SAVED_STATUS + "' status can be deleted.";
                log.error(message);
                throw new WrongStatusException(message);
            }
        }
    }

    public void verifyStatusesBeforeAddingComment(List<TimeEntryBo> timeEntryBoList) {
        for (TimeEntryBo timeEntryBo : timeEntryBoList) {
            if (!timeEntryBo.getStatus().equalsIgnoreCase(REJECTED_STATUS) && !timeEntryBo.getComment().isEmpty()) {
                String message = ": Only entries with '" + REJECTED_STATUS + "' status can be commented.";
                log.error(message);
                throw new WrongStatusException(message);
            }
        }
    }

    public void verifyThatWeekDatesAreUnique(List<TimeEntryBo> timeEntryBoList) {
        for (TimeEntryBo timeEntryBo : timeEntryBoList) {
            if (Collections.frequency(timeEntryBoList, timeEntryBo) > 1) {
                log.error("Date cannot be doubled for one week in one project!");
                throw new DoubledDateException();
            }
        }
    }

    private List<String> resolveProjectGuidFromBoList(List<TimeEntryBo> timeEntryBoList) {
        return timeEntryBoList.stream()
                .map(TimeEntryBo::getProjectGuid)
                .distinct()
                .collect(Collectors.toList());
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

    private List<TimeEntryEntity> validateWeekTimeEntry(List<TimeEntryBo> timeEntryBoList) {
        List<TimeEntryEntity> timeEntryEntityList = timeEntryBoList.stream()
                .map(timeEntryMapper::fromBoToEntity)
                .collect(Collectors.toList());
        List<StatusEntity> statusEntities = statusRepository.findAll();
        for (TimeEntryEntity entry : timeEntryEntityList) {
            if (timeEntryRepository.existsByProjectGuid(entry.getProjectGuid())) {
                log.error("Project with guid" + entry.getProjectGuid() + " not found.");
                throw new ProjectNotFoundException();
            }
            if (!timeEntryRepository.existsByUserGuid(entry.getUserGuid())) {
                log.error("User with guid" + entry.getUserGuid() + "  not found.");
                throw new UserNotFoundException();
            }
            verifyStatusNames(entry.getStatusEntity(), statusEntities);
        }
        return timeEntryEntityList;
    }
}
