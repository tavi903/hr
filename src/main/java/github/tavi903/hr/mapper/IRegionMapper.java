package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.RegionDto;
import github.tavi903.hr.entity.Region;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRegionMapper extends IBaseMapper<Region, RegionDto> {
}
