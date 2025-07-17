package github.tavi903.hr.service;

import github.tavi903.hr.exception.HrException;
import github.tavi903.hr.dto.CountryDto;
import github.tavi903.hr.entity.Country;
import github.tavi903.hr.mapper.ICountryMapper;
import github.tavi903.hr.repository.ICountryRepository;
import github.tavi903.hr.repository.IRegionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService extends BaseService<String, Country, CountryDto> {
    private final IRegionRepository regionRepository;
    private final ICountryRepository countryRepository;

    public CountryService(
            IRegionRepository regionRepository,
            ICountryRepository countryRepository,
            ICountryMapper mapper
    ) {
        super(countryRepository, mapper);
        this.countryRepository = countryRepository;
        this.regionRepository = regionRepository;
    }

    public CountryDto createOrUpdate(CountryDto countryDto) {
        if (regionRepository.findById(countryDto.getRegionId()).isEmpty())
            throw new HrException("Region with id " + countryDto.getRegionId() + " does not exist.");
        return super.createOrUpdate(countryDto);
    }

    public List<CountryDto> findByRegionId(Long regionId) {
        Sort sort = Sort.sort(Country.class).by(Country::getCountryName).ascending();
        if (regionId == null) {
            return mapper.fromEntities(countryRepository.findAll(sort));
        }
        return mapper.fromEntities(countryRepository.findAllByRegion_id(regionId, sort));
    }
}
