package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.EmployeeDto;
import github.tavi903.hr.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IEmployeeMapper extends IBaseMapper<Employee, EmployeeDto> {
}
