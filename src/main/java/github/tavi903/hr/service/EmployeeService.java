package github.tavi903.hr.service;

import github.tavi903.hr.dto.EmployeeDto;
import github.tavi903.hr.entity.Employee;
import github.tavi903.hr.mapper.IEmployeeMapper;
import github.tavi903.hr.repository.IEmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends BaseService<Long, Employee, EmployeeDto> {

    public EmployeeService(IEmployeeRepository repository, IEmployeeMapper mapper) {
        super(repository, mapper);
    }

}
