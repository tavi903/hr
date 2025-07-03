package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.LocationDto;
import github.tavi903.hr.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ILocationMapper extends IBaseMapper<Location, LocationDto> {
    @Override
    @Mapping(target = "country.id", source = "countryId")
    Location fromDto(LocationDto dto);
    @Override
    @Mapping(target = "countryId", source = "country.id")
    LocationDto fromEntity(Location entity);
}
