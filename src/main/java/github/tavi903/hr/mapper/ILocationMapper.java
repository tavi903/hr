package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.LocationDto;
import github.tavi903.hr.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ILocationMapper extends IBaseMapper<Location, LocationDto> {
}
