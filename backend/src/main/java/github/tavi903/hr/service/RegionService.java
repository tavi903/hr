package github.tavi903.hr.service;

import github.tavi903.hr.exception.HrException;
import github.tavi903.hr.dto.RegionDto;
import github.tavi903.hr.entity.Region;
import github.tavi903.hr.mapper.IRegionMapper;
import github.tavi903.hr.repository.IRegionRepository;
import org.springframework.stereotype.Service;

@Service
public class RegionService extends BaseService<Long, Region, RegionDto> {

    public RegionService(IRegionRepository repository, IRegionMapper mapper) {
        super(repository, mapper);
    }

    public RegionDto update(RegionDto regionDto) {
        if (regionDto.getId() == null || !repository.existsById(regionDto.getId())) {
            throw new HrException("Cannot update Region, id not present!");
        }
        return super.createOrUpdate(regionDto);
    }

}
