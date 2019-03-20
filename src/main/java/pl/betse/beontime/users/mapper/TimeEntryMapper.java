package pl.betse.beontime.users.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.betse.beontime.users.bo.TimeEntryBo;
import pl.betse.beontime.users.entity.TimeEntryEntity;
import pl.betse.beontime.users.model.WeekDayBody;
import pl.betse.beontime.users.model.WeekTimeEntryBody;

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
                .date(timeEntryBo.getEntryDate())
                .comment(timeEntryBo.getComment())
                .hours(timeEntryBo.getHoursNumber())
                .status(timeEntryBo.getStatus())
                .build();

    }

    public TimeEntryBo fromWeekDayBodyToBo(WeekDayBody weekDayBody, WeekTimeEntryBody weekTimeEntryBody, String userGuid) {
        return TimeEntryBo.builder()
                .userGuid(userGuid)
                .projectGuid(weekTimeEntryBody.getProjectId())
                .entryDate(weekDayBody.getDate())
                .hoursNumber(weekDayBody.getHours())
                .comment(weekDayBody.getComment())
                .week(weekTimeEntryBody.getWeek())
                .status(weekDayBody.getStatus())
                .build();
    }


}
