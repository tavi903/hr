package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.DepartmentDto;
import github.tavi903.hr.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ReferenceMapper.class)
public interface IDepartmentMapper extends IBaseMapper<Department, DepartmentDto> {
    @Override
    @Mapping(target = "location", source = "locationId")
    @Mapping(target = "manager", source = "managerId")
    Department fromDto(DepartmentDto dto);
    @Override
    @Mapping(target = "locationId", source = "location.id")
    @Mapping(target = "managerId", source = "manager.id")
    @Mapping(target = "city", source = "location.city")
    @Mapping(target = "countryName", source = "location.country.countryName")
    DepartmentDto fromEntity(Department entity);
}
