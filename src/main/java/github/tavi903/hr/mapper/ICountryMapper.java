package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.CountryDto;
import github.tavi903.hr.entity.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICountryMapper extends IBaseMapper<Country, CountryDto> {
}
