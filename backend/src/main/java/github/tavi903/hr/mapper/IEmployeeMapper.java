package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.EmployeeDto;
import github.tavi903.hr.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ReferenceMapper.class)
public interface IEmployeeMapper extends IBaseMapper<Employee, EmployeeDto> {
    @Override
    @Mapping(target = "job", source = "jobId")
    @Mapping(target = "department", source = "departmentId")
    @Mapping(target = "manager", source = "managerId")
    Employee fromDto(EmployeeDto dto);

    @Override
    @Mapping(target = "jobTitle", source = "job.jobTitle")
    @Mapping(target = "departmentName", source = "department.departmentName")
    @Mapping(target = "jobId", source = "job.id")
    @Mapping(target = "departmentId", source = "department.id")
    EmployeeDto fromEntity(Employee employee);
}
