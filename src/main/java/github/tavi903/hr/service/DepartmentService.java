package github.tavi903.hr.service;

import github.tavi903.hr.dto.DepartmentDto;
import github.tavi903.hr.entity.Department;
import github.tavi903.hr.mapper.IDepartmentMapper;
import github.tavi903.hr.repository.IDepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService extends BaseService<Long, Department, DepartmentDto> {

    public DepartmentService(IDepartmentRepository repository, IDepartmentMapper mapper) {
        super(repository, mapper);
    }

}
