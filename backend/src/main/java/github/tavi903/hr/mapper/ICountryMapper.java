package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.CountryDto;
import github.tavi903.hr.dto.EmployeeDto;
import github.tavi903.hr.entity.Country;
import github.tavi903.hr.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ReferenceMapper.class)
public interface ICountryMapper extends IBaseMapper<Country, CountryDto> {
    @Override
    @Mapping(target = "regionId", source = "region.id")
    @Mapping(target = "regionName", source = "region.regionName")
    CountryDto fromEntity(Country entity);

    @Override
    @Mapping(target = "region", source = "regionId")
    Country fromDto(CountryDto dto);
}
