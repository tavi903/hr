package github.tavi903.hr.service;

import github.tavi903.hr.exception.HrException;
import github.tavi903.hr.dto.CountryDto;
import github.tavi903.hr.entity.Country;
import github.tavi903.hr.mapper.ICountryMapper;
import github.tavi903.hr.repository.ICountryRepository;
import github.tavi903.hr.repository.IRegionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        if (!regionRepository.existsById(countryDto.getRegionId()))
            throw new HrException("Region with id " + countryDto.getRegionId() + " does not exist.");
        return super.createOrUpdate(countryDto);
    }

    public Page<CountryDto> findByRegionId(Long regionId, Pageable pageable) {
        if (regionId == null) {
            Page<Country> countriesPage = countryRepository.findAll(pageable);
            return new PageImpl<>(
                    mapper.fromEntities(countriesPage.getContent()),
                    pageable,
                    countriesPage.getTotalElements()
            );
        }
        Page<Country> countriesByRegion = countryRepository.findAllByRegion_id(regionId, pageable);
        return new PageImpl<>(
                mapper.fromEntities(countriesByRegion.getContent()),
                pageable,
                countriesByRegion.getTotalElements()
        );
    }
}
