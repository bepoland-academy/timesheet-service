package pl.betse.beontime.timesheet.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.betse.beontime.timesheet.bo.TimeEntryBo;
import pl.betse.beontime.timesheet.entity.TimeEntryEntity;
import pl.betse.beontime.timesheet.model.MonthDayBody;
import pl.betse.beontime.timesheet.model.MonthTimeEntryBody;
import pl.betse.beontime.timesheet.model.WeekDayBody;
import pl.betse.beontime.timesheet.model.WeekTimeEntryBody;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Mapper(componentModel = "spring", uses = {GuidMapper.class})
public abstract class TimeEntryMapper {

    @Mapping(source = "guid", target = "timeEntryId")
    @Mapping(source = "statusEntity.name", target = "status")
    public abstract TimeEntryBo fromEntityToBo(TimeEntryEntity timeEntryEntity);

    @Mapping(source = "timeEntryId", target = "guid", qualifiedByName = "mapGuid")
    @Mapping(source = "status", target = "statusEntity.name")
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

    public MonthTimeEntryBody fromTimeEntryBoToMonthTimeEntryBody(List<TimeEntryBo> timeEntryBoList){
        return MonthTimeEntryBody.builder()
                .month(!timeEntryBoList.isEmpty() ? timeEntryBoList.get(0).getEntryDate().toString().substring(0,7) : "no month - mapper!")
                .projectId(!timeEntryBoList.isEmpty() ? timeEntryBoList.get(0).getProjectGuid(): "noProjectGuid = mapper!")
                .consultantId(!timeEntryBoList.isEmpty() ? timeEntryBoList.get(0).getUserGuid() : "noConsultantGuid = mapper")
                .monthDays(timeEntryBoList.stream().map(entry -> this.fromBoToMonthDayBody(entry)).collect(Collectors.toList()))
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

    public MonthDayBody fromBoToMonthDayBody(TimeEntryBo timeEntryBo) {

        return MonthDayBody.builder()
                .date(timeEntryBo.getEntryDate().toString())
                .hours(timeEntryBo.getHoursNumber())
                .status(timeEntryBo.getStatus())
                .comment(timeEntryBo.getComment())
                .build();
    }
}
