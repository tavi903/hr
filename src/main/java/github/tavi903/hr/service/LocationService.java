package github.tavi903.hr.service;

import github.tavi903.hr.HrException;
import github.tavi903.hr.dto.LocationDto;
import github.tavi903.hr.entity.Location;
import github.tavi903.hr.mapper.ILocationMapper;
import github.tavi903.hr.repository.ICountryRepository;
import github.tavi903.hr.repository.ILocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        if (countryRepository.findById(locationDto.getCountryId()).isEmpty())
            throw new HrException("Country with id: " + locationDto.getCountryId() + " doesn't exist.");
        return super.createOrUpdate(locationDto);
    }

    public LocationDto update(LocationDto locationDto) {
        if (locationDto.getId() == null || locationRepository.findById(locationDto.getId()).isEmpty())
            throw new HrException("This location doesn't exist!");
        if (countryRepository.findById(locationDto.getCountryId()).isEmpty())
            throw new HrException("Country with id: " + locationDto.getCountryId() + " doesn't exist.");
        return super.createOrUpdate(locationDto);
    }

    public Page<LocationDto> findAll(String countryId, Pageable pageable) {
        Page<Location> page;
        Pageable pageRequestWithSortByCity =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.sort(Location.class).by(Location::getCity));
        if (countryId != null && !countryId.isEmpty())
            page = locationRepository.findByCountryId(countryId, pageRequestWithSortByCity);
        else
            page = locationRepository.findAll(pageRequestWithSortByCity);
        return new PageImpl<>(
                page.getContent().stream().map(mapper::fromEntity).toList(),
                pageRequestWithSortByCity,
                page.getTotalElements()
        );
    }
}
