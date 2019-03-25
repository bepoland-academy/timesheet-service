package pl.betse.beontime.timesheet.mapper;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.UUID;

@Named("guidMapper")
@Mapper(componentModel = "spring")
abstract class GuidMapper {

    @Named("mapGuid")
    String mapGuid(String guid) {
        if (StringUtils.isBlank(guid)) {
            return UUID.randomUUID().toString();
        }
        return guid;
    }
}
