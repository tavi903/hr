package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.CountryDto;
import github.tavi903.hr.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICountryMapper extends IBaseMapper<Country, CountryDto> {
    @Override
    @Mapping(target = "regionId", source = "region.id")
    CountryDto fromEntity(Country entity);
}
