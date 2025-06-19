package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.DepartmentDto;
import github.tavi903.hr.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDepartmentMapper extends IBaseMapper<Department, DepartmentDto> {
}
