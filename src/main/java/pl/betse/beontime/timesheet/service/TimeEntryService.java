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

import static pl.betse.beontime.timesheet.service.TimeEntryStatus.*;

@Slf4j
@Service
public class TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final TimeEntryMapper timeEntryMapper;
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    public TimeEntryService(
            TimeEntryRepository timeEntryRepository,
            TimeEntryMapper timeEntryMapper,
            StatusRepository statusRepository,
            StatusMapper statusMapper) {
        this.timeEntryRepository = timeEntryRepository;
        this.timeEntryMapper = timeEntryMapper;
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    /**
     * Finds week time sheet for chosen user and week number.
     *
     * @param userGuid user identifier
     * @param week     number of week
     * @return week time sheet for user
     */
    public List<List<TimeEntryBo>> findByUserGuidAndWeek(String userGuid, String week) {
        List<List<TimeEntryBo>> allProjectsForWeekList = new ArrayList<>();
        List<TimeEntryBo> timeEntryBoList =
                timeEntryRepository.findByUserGuidAndWeekOrderByEntryDate(userGuid, week)
                        .stream()
                        .map(timeEntryMapper::fromEntityToBo)
                        .collect(Collectors.toList());

        List<String> projectGuidList = resolveProjectGuidFromBoList(timeEntryBoList);
        for (String projectGuid : projectGuidList) {
            allProjectsForWeekList.add(
                    timeEntryRepository.findByUserGuidAndProjectGuidAndWeekOrderByEntryDate(userGuid, projectGuid, week)
                            .stream()
                            .map(timeEntryMapper::fromEntityToBo)
                            .collect(Collectors.toList())
            );
        }
        if (allProjectsForWeekList.isEmpty()) {
            //log.error("Time entry for user with guid = " + userGuid + " and week = " + week + " not exists.");
            log.error("Time entry for user with guid = {} and week = {} not exists.", userGuid, week);
            throw new TimeEntryForUserWeekNotFound();
        }
        return allProjectsForWeekList;
    }

    /**
     * Return month time sheet for chosen user.
     *
     * @param userGuid    user identifier
     * @param requestDate date identifier
     * @return month time sheet for user
     */
    public List<List<TimeEntryBo>> findByUserGuidAndMonth(String userGuid, LocalDate requestDate) {
        List<List<TimeEntryBo>> allProjectForMonth = new ArrayList<>();
        List<TimeEntryBo> timeEntryBoList = timeEntryRepository.findByUserGuidAndMonth(userGuid, requestDate)
                .stream()
                .map(timeEntryMapper::fromEntityToBo)
                .collect(Collectors.toList());
        List<String> projectsGuidList = resolveProjectGuidFromBoList(timeEntryBoList);
        for (String projectGuid : projectsGuidList) {
            allProjectForMonth.add(
                    timeEntryRepository.findByUserGuidAndProjectGuidAndMonth(userGuid, projectGuid, requestDate)
                            .stream()
                            .map(timeEntryMapper::fromEntityToBo)
                            .collect(Collectors.toList())
            );
        }
        if (allProjectForMonth.isEmpty()) {
            log.error("Time entry for user with guid = {} and month = {} not exists.", userGuid, requestDate);
            throw new TimeEntryForUserMonthNotFound();
        }
        return allProjectForMonth;
    }

    /**
     * Save new week time sheet for user.
     *
     * @param timeEntryBoList representation of time entry object list
     */
    public void saveWeekForUser(List<TimeEntryBo> timeEntryBoList) {
        List<TimeEntryEntity> timeEntryEntityList = validateWeekTimeEntry(timeEntryBoList);
        for (TimeEntryEntity entry : timeEntryEntityList) {
            entry.setStatusEntity(statusRepository.findByName(entry.getStatusEntity().getName()).get());
            timeEntryRepository.save(entry);
        }
    }

    /**
     * Updating week time sheet for user.
     *
     * @param timeEntryBoList representation of time entry object list
     * @param userGuid        user identifier
     * @param weekNumber      number of week
     */
    public void editWeekHoursAndStatuses(
            List<TimeEntryBo> timeEntryBoList,
            String userGuid,
            String weekNumber) {
        List<TimeEntryEntity> incomingList = validateWeekTimeEntry(timeEntryBoList);
        List<TimeEntryEntity> databaseList = timeEntryRepository.findByUserGuidAndWeekOrderByEntryDate(
                userGuid, weekNumber);
        databaseList.forEach(databaseEntry -> updateTimeEntries(databaseEntry, incomingList));
    }

    /**
     * Delete week time sheet for chosen user.
     *
     * @param timeEntryBoList representation of time entry object list
     * @param userGuid        user identifier
     * @param weekNumber      number of week
     */
    public void deleteWeekTimeEntries(List<TimeEntryBo> timeEntryBoList, String userGuid, String weekNumber) {
        List<TimeEntryEntity> incomingList =
                validateWeekTimeEntry(timeEntryBoList);
        List<TimeEntryEntity> databaseList =
                timeEntryRepository.findByUserGuidAndWeekOrderByEntryDate(userGuid, weekNumber);
        databaseList.forEach(databaseEntry -> deleteIncomingList(databaseEntry, incomingList));
    }

    /**
     * Update monthly time sheet for chosen user.
     *
     * @param timeEntryBoList representation of time entry object list
     * @param userGuid        user identifier
     * @param localDate       date represent required month
     */
    public void editMonthStatusesAndComments(List<TimeEntryBo> timeEntryBoList, String userGuid, LocalDate localDate) {
        List<TimeEntryEntity> incomingList = validateWeekTimeEntry(timeEntryBoList);
        List<TimeEntryEntity> databaseList = timeEntryRepository.findByUserGuidAndMonth(userGuid, localDate);
        for (TimeEntryEntity databaseEntry : databaseList) {
            for (TimeEntryEntity incomingEntity : incomingList) {
                if (databaseEntry.getEntryDate().equals(incomingEntity.getEntryDate())) {
                    StatusEntity statusEntity = statusRepository.findByName(incomingEntity.getStatusEntity().getName()).get();
                    databaseEntry.setStatusEntity(statusEntity);
                    databaseEntry.setComment(incomingEntity.getComment());
                    timeEntryRepository.save(databaseEntry);
                }
            }
        }
    }

    /**
     * Checking if required date exists.
     *
     * @param timeEntryBoList representation of time entry object list
     * @param httpMethod      http request method
     */
    public void checkIfTimeEntriesExist(List<TimeEntryBo> timeEntryBoList, String httpMethod) {
        for (TimeEntryBo timeEntryBo : timeEntryBoList) {
            TimeEntryEntity timeEntryEntity = timeEntryMapper.fromBoToEntity(timeEntryBo);
            if (httpMethod.equalsIgnoreCase(RequestMethod.POST.name())) {
                if (timeEntryRepository.existsByUserGuidAndProjectGuidAndEntryDate(timeEntryEntity.getUserGuid(),
                        timeEntryEntity.getProjectGuid(),
                        timeEntryEntity.getEntryDate())) {
                    log.error("Time entry for user {} and project {} with date {} currently exist in database!",
                            timeEntryEntity.getUserGuid(),
                            timeEntryEntity.getProjectGuid(),
                            timeEntryEntity.getEntryDate());
                    throw new TimeEntryExistsInDatabase();
                }
            } else if (httpMethod.equalsIgnoreCase(RequestMethod.PUT.name()) ||
                    httpMethod.equalsIgnoreCase(RequestMethod.DELETE.name())) {
                if (!timeEntryRepository.existsByUserGuidAndProjectGuidAndEntryDate(timeEntryEntity.getUserGuid(),
                        timeEntryEntity.getProjectGuid(),
                        timeEntryEntity.getEntryDate())) {
                    log.error("Time entry for user {} and project {} with date {} currently NOT exist in database!",
                            timeEntryEntity.getUserGuid(),
                            timeEntryEntity.getProjectGuid(),
                            timeEntryEntity.getEntryDate());
                    throw new TimeEntryNotFound();
                }
            }
        }
    }

    /**
     * Checking chosen week exists in based year.
     *
     * @param timeEntryBoList representation of time entry object list
     * @param week            week number
     */
    public void checkIfDateIsInCorrectWeekOfYear(List<TimeEntryBo> timeEntryBoList, String week) {
        int weekNumber = Integer.parseInt(week.substring(6, 8));
        for (TimeEntryBo timeEntryBo : timeEntryBoList) {
            if (timeEntryBo.getEntryDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) != weekNumber) {
                log.error("Date is not in that week of the year.");
                throw new BadWeekAndDateException();
            }
        }
    }

    /**
     * Check if chosen month is correct.
     *
     * @param timeEntryBoList representation of time entry object list
     * @param requestedDate   representation of required date
     */
    public void checkIfDateHasCorrectMonth(List<TimeEntryBo> timeEntryBoList, LocalDate requestedDate) {
        int monthNumber = requestedDate.getMonthValue();
        for (TimeEntryBo timeEntryBo : timeEntryBoList) {
            if (timeEntryBo.getEntryDate().getMonthValue() != monthNumber) {
                log.error("Date is not in that month of the year.");
                throw new BadMonthAndDateException();
            }
        }
    }

    /**
     * Checking correct status to create new time sheet.
     *
     * @param timeEntryBoList representation of time entry object list
     */
    public void verifyStatusesBeforeCreatingNewEntry(List<TimeEntryBo> timeEntryBoList) {
        for (TimeEntryBo timeEntryBo : timeEntryBoList) {
            if (!timeEntryBo.getStatus().equalsIgnoreCase(SAVED.name()) &&
                    !timeEntryBo.getStatus().equalsIgnoreCase(SUBMITTED.name())) {
                String message = ": Only entries with '" +
                        SAVED.name() +
                        "' or '" +
                        SUBMITTED.name() +
                        "' status can be created as new.";
                log.error(message);
                throw new WrongStatusException(message);
            }
        }
    }

    /**
     * Checking status before deleting time sheet.
     *
     * @param timeEntryBoList representation of time entry object list
     */
    public void verifyStatusesBeforeDeleting(List<TimeEntryBo> timeEntryBoList) {
        for (TimeEntryBo timeEntryBo : timeEntryBoList) {
            if (!timeEntryBo.getStatus().equalsIgnoreCase(SAVED.name())) {
                String message = ": Only entries with '" + SAVED.name() + "' status can be deleted.";
                log.error(message);
                throw new WrongStatusException(message);
            }
        }
    }

    /**
     * Checking status before add mew comment to time sheet.
     *
     * @param timeEntryBoList representation of time entry object list
     */
    public void verifyStatusesBeforeAddingComment(List<TimeEntryBo> timeEntryBoList) {
        for (TimeEntryBo timeEntryBo : timeEntryBoList) {
            if (!timeEntryBo.getStatus().equalsIgnoreCase(REJECTED.name()) && !timeEntryBo.getComment().isEmpty()) {
                String message = ": Only entries with '" + REJECTED.name() + "' status can be commented.";
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

    /**
     * @param timeEntryBoList
     * @return
     */
    private List<String> resolveProjectGuidFromBoList(List<TimeEntryBo> timeEntryBoList) {
        return timeEntryBoList.stream()
                .map(TimeEntryBo::getProjectGuid)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Checking a correct status name.
     *
     * @param statusEntity   representation of status in database
     * @param statusEntities representation of status
     */
    private void verifyStatusNames(StatusEntity statusEntity, List<StatusEntity> statusEntities) {
        List<StatusBo> statusBos = statusEntities.stream()
                .map(statusMapper::fromEntityToBo)
                .collect(Collectors.toList());
        if (!statusBos.contains(statusMapper.fromEntityToBo(statusEntity))) {
            log.error("Status has been not found!");
            throw new StatusNotFoundException();
        }
    }

    /**
     * Checking if time sheet for week is correct.
     *
     * @param timeEntryBoList representation of time entry object list
     * @return validated time sheet for week
     */
    private List<TimeEntryEntity> validateWeekTimeEntry(List<TimeEntryBo> timeEntryBoList) {
        List<TimeEntryEntity> timeEntryEntityList = timeEntryBoList.stream()
                .map(timeEntryMapper::fromBoToEntity)
                .collect(Collectors.toList());
        List<StatusEntity> statusEntities = statusRepository.findAll();
        for (TimeEntryEntity entry : timeEntryEntityList) {
            verifyStatusNames(entry.getStatusEntity(), statusEntities);
        }
        return timeEntryEntityList;
    }

    private void updateTimeEntries(
            TimeEntryEntity databaseEntry,
            List<TimeEntryEntity> incomingEntityList) {
        incomingEntityList
                .stream()
                .filter(incomingEntity -> databaseEntry.getEntryDate().equals(incomingEntity.getEntryDate()))
                .forEach(incomingEntity -> updateTimeEntry(databaseEntry, incomingEntity));
    }

    private void updateTimeEntry(
            TimeEntryEntity databaseEntry,
            TimeEntryEntity incomingEntity) {
        StatusEntity statusEntity = statusRepository.findByName(
                incomingEntity.getStatusEntity().getName()).get();
        databaseEntry.setStatusEntity(statusEntity);
        databaseEntry.setHoursNumber(incomingEntity.getHoursNumber());
        timeEntryRepository.save(databaseEntry);
    }

    private void deleteIncomingList(TimeEntryEntity databaseEntry, List<TimeEntryEntity> incomingList) {
        incomingList
                .stream()
                .filter(incomingEntity -> databaseEntry.getEntryDate().equals(incomingEntity.getEntryDate()))
                .map(incomingEntity -> databaseEntry)
                .forEach(timeEntryRepository::delete);
    }
}
