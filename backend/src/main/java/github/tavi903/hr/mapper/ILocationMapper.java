package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.LocationDto;
import github.tavi903.hr.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ReferenceMapper.class)
public interface ILocationMapper extends IBaseMapper<Location, LocationDto> {
    @Override
    @Mapping(target = "country", source = "countryId")
    Location fromDto(LocationDto dto);
    @Override
    @Mapping(target = "countryId", source = "country.id")
    @Mapping(target = "countryName", source = "country.countryName")
    LocationDto fromEntity(Location entity);
}
