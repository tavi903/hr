package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.RegionDto;
import github.tavi903.hr.entity.Region;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ReferenceMapper.class)
public interface IRegionMapper extends IBaseMapper<Region, RegionDto> {
}
