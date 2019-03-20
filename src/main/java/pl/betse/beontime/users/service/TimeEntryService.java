package pl.betse.beontime.users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.betse.beontime.users.bo.TimeEntryBo;
import pl.betse.beontime.users.exception.TimeEntryForUserWeekNotFound;
import pl.betse.beontime.users.mapper.TimeEntryMapper;
import pl.betse.beontime.users.repository.TimeEntryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final TimeEntryMapper timeEntryMapper;

    public TimeEntryService(TimeEntryRepository timeEntryRepository, TimeEntryMapper timeEntryMapper) {
        this.timeEntryRepository = timeEntryRepository;
        this.timeEntryMapper = timeEntryMapper;
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
}
