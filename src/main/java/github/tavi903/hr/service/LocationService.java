package github.tavi903.hr.service;

import github.tavi903.hr.dto.LocationDto;
import github.tavi903.hr.entity.Location;
import github.tavi903.hr.mapper.ILocationMapper;
import github.tavi903.hr.repository.ILocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService extends BaseService<Long, Location, LocationDto> {

    public LocationService(ILocationRepository repository, ILocationMapper mapper) {
        super(repository, mapper);
    }

}
