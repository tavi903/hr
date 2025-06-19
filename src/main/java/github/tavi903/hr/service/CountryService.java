package github.tavi903.hr.service;

import github.tavi903.hr.dto.CountryDto;
import github.tavi903.hr.entity.Country;
import github.tavi903.hr.mapper.ICountryMapper;
import github.tavi903.hr.repository.ICountryRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryService extends BaseService<String, Country, CountryDto> {

    public CountryService(ICountryRepository repository, ICountryMapper mapper) {
        super(repository, mapper);
    }

}
