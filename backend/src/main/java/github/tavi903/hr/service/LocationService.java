package github.tavi903.hr.service;

import github.tavi903.hr.entity.Country;
import github.tavi903.hr.exception.HrException;
import github.tavi903.hr.dto.LocationDto;
import github.tavi903.hr.entity.Location;
import github.tavi903.hr.mapper.ILocationMapper;
import github.tavi903.hr.repository.ICountryRepository;
import github.tavi903.hr.repository.ILocationRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService extends BaseService<Long, Location, LocationDto> {
    private final ILocationRepository locationRepository;
    private final ICountryRepository countryRepository;

    public LocationService(ILocationRepository repository, ILocationMapper mapper, ICountryRepository countryRepository) {
        super(repository, mapper);
        this.locationRepository = repository;
        this.countryRepository = countryRepository;
    }

    public LocationDto create(LocationDto locationDto) {
        if (locationDto.getId() != null)
            throw new HrException("Id Should be Empty to create a new Location!");
        if (StringUtils.isNotBlank(locationDto.getCountryId()) && !countryRepository.existsById(locationDto.getCountryId()))
            throw new HrException("Country with id: " + locationDto.getCountryId() + " doesn't exist.");
        if (StringUtils.isNotBlank(locationDto.getCountryName()))
            locationDto = createLocationWithCountryOrRaiseHrException(locationDto);
        return super.createOrUpdate(locationDto);
    }

    private LocationDto createLocationWithCountryOrRaiseHrException(final LocationDto locationDto) {
        char initialLetter = locationDto.getCountryName().charAt(0);
        List<Country> countriesFound = countryRepository.findByInitialLetter(initialLetter);
        Optional<Country> countryOpt =
                countriesFound.stream()
                        .filter(c -> c.getCountryName().equals(locationDto.getCountryName())).findFirst();
        if (countryOpt.isPresent()) {
            return LocationDto.builder()
                    .id(locationDto.getId())
                    .countryId(countryOpt.get().getId())
                    .stateProvince(locationDto.getStateProvince())
                    .city(locationDto.getCity())
                    .postalCode(locationDto.getPostalCode())
                    .streetAddress(locationDto.getStreetAddress())
                    .build();
        }
        List<Country> countryGoodCandidates = new ArrayList<>();
        for (int i = locationDto.getCountryName().length() - 2; i > 0; i--) {
            final int j = i;
            List<Country> countryCandidates = countriesFound.stream()
                    .filter(c -> c.getCountryName().startsWith(locationDto.getCountryName().substring(0, j)))
                    .toList();
            if (!countryCandidates.isEmpty()) {
                countryGoodCandidates = countryCandidates;
                break;
            }
        }
        throw new HrException("Country " + locationDto.getCountryName() + " not found. Maybe you want one of this?\n"
                    + countryGoodCandidates.stream().map(c -> "- " + c.getCountryName()).collect(Collectors.joining("\n")));
    }

    public LocationDto update(LocationDto locationDto) {
        if (locationDto.getId() == null || !locationRepository.existsById(locationDto.getId()))
            throw new HrException("This location doesn't exist!");
        if (StringUtils.isNotBlank(locationDto.getCountryId()) && !countryRepository.existsById(locationDto.getCountryId()))
            throw new HrException("Country with id: " + locationDto.getCountryId() + " doesn't exist.");
        if (StringUtils.isNotBlank(locationDto.getCountryName()))
            locationDto = createLocationWithCountryOrRaiseHrException(locationDto);
        return super.createOrUpdate(locationDto);
    }

    public Page<LocationDto> findAll(String countryId, Pageable pageable) {
        Page<Location> page;
//        Pageable pageRequestWithSortByCity =
//                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.sort(Location.class).by(Location::getCity));
        if (countryId != null && !countryId.isEmpty())
            page = locationRepository.findByCountryId(countryId, pageable);
        else
            page = locationRepository.findAll(pageable);
        return new PageImpl<>(
                page.getContent().stream().map(mapper::fromEntity).toList(),
                pageable,
                page.getTotalElements()
        );
    }
}
