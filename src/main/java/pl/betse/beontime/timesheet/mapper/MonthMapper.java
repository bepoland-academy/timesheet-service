package pl.betse.beontime.timesheet.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import pl.betse.beontime.timesheet.bo.MonthBo;
import pl.betse.beontime.timesheet.entity.MonthEntity;
import pl.betse.beontime.timesheet.model.MonthBody;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class MonthMapper {

    public abstract MonthBo fromEntityToBo(MonthEntity monthEntity);

    public abstract MonthBody fromBoToBody(MonthBo monthBo);
}
