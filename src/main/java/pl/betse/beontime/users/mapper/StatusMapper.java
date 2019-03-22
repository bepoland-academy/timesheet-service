package pl.betse.beontime.users.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import pl.betse.beontime.users.bo.StatusBo;
import pl.betse.beontime.users.entity.StatusEntity;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class StatusMapper {

    public abstract StatusBo fromEntityToBo(StatusEntity statusEntity);

    public abstract StatusEntity fromBoToEntity(StatusBo statusBo);

}
