package pl.betse.beontime.users.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.betse.beontime.users.bo.TimeEntryBo;
import pl.betse.beontime.users.entity.TimeEntryEntity;
import pl.betse.beontime.users.model.MonthDayBody;
import pl.betse.beontime.users.model.MonthTimeEntryBody;
import pl.betse.beontime.users.model.WeekDayBody;
import pl.betse.beontime.users.model.WeekTimeEntryBody;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Mapper(componentModel = "spring", uses = {GuidMapper.class})
public abstract class TimeEntryMapper {

    @Mapping(source = "guid", target = "timeEntryId")
    @Mapping(source = "statusEntity.status", target = "status")
    public abstract TimeEntryBo fromEntityToBo(TimeEntryEntity timeEntryEntity);

    @Mapping(source = "timeEntryId", target = "guid", qualifiedByName = "mapGuid")
    @Mapping(source = "status", target = "statusEntity.status")
    public abstract TimeEntryEntity fromBoToEntity(TimeEntryBo timeEntryBo);


    public WeekDayBody fromBoToWeekDayBody(TimeEntryBo timeEntryBo) {

        return WeekDayBody.builder()
                .day(timeEntryBo.getEntryDate().getDayOfWeek().name())
                .date(timeEntryBo.getEntryDate().toString())
                .comment(timeEntryBo.getComment())
                .hours(timeEntryBo.getHoursNumber())
                .status(timeEntryBo.getStatus())
                .build();

    }

    public TimeEntryBo fromWeekDayBodyToBo(WeekDayBody weekDayBody, WeekTimeEntryBody weekTimeEntryBody, String userGuid, String weekNumber) {
        return TimeEntryBo.builder()
                .userGuid(userGuid)
                .projectGuid(weekTimeEntryBody.getProjectId())
                .entryDate(LocalDate.parse(weekDayBody.getDate()))
                .hoursNumber(weekDayBody.getHours())
                .comment(weekDayBody.getComment())
                .week(weekNumber)
                .status(weekDayBody.getStatus())
                .build();
    }

    public WeekTimeEntryBody fromTimeEntryBoToWeekTimeEntryBody(List<TimeEntryBo> timeEntryBoList) {
        return WeekTimeEntryBody.builder()
                .week(!timeEntryBoList.isEmpty() ? timeEntryBoList.get(0).getWeek() : "no week - mapper!")
                .projectId(!timeEntryBoList.isEmpty() ? timeEntryBoList.get(0).getProjectGuid() : "no guid - mapper!")
                .weekDays(timeEntryBoList.stream().map(entry -> this.fromBoToWeekDayBody(entry)).collect(Collectors.toList()))
                .build();
    }

    public TimeEntryBo fromMonthDayBodyToBo(MonthDayBody monthDayBody, MonthTimeEntryBody monthTimeEntryBody) {
        return TimeEntryBo.builder()
                .userGuid(monthTimeEntryBody.getConsultantId())
                .projectGuid(monthTimeEntryBody.getProjectId())
                .entryDate(LocalDate.parse(monthDayBody.getDate()))
                .hoursNumber(monthDayBody.getHours())
                .status(monthDayBody.getStatus())
                .comment(monthDayBody.getComment())
                .build();

    }

}
