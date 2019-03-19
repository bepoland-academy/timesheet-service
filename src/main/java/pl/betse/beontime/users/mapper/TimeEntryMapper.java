package pl.betse.beontime.users.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.betse.beontime.users.bo.TimeEntryBo;
import pl.betse.beontime.users.entity.TimeEntryEntity;

@Mapper(componentModel = "spring", uses = {GuidMapper.class})
public abstract class TimeEntryMapper {

    @Mapping(source = "guid", target = "timeEntryId")
    @Mapping(source = "statusEntity.status", target = "status")
    public abstract TimeEntryBo mapEntityToBo(TimeEntryEntity timeEntryEntity);

    @Mapping(source = "timeEntryId", target = "guid")
    @Mapping(source = "status", target = "statusEntity.status")
    public abstract TimeEntryEntity mapBoToEntity(TimeEntryBo timeEntryBo);

}
