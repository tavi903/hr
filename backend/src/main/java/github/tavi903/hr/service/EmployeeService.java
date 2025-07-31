package github.tavi903.hr.service;

import github.tavi903.hr.exception.HrException;
import github.tavi903.hr.dto.EmployeeDto;
import github.tavi903.hr.dto.EmployeeSearchDto;
import github.tavi903.hr.entity.Employee;
import github.tavi903.hr.entity.EmployeeSpecs;
import github.tavi903.hr.entity.Job;
import github.tavi903.hr.entity.JobHistory;
import github.tavi903.hr.mapper.IEmployeeMapper;
import github.tavi903.hr.repository.IDepartmentRepository;
import github.tavi903.hr.repository.IEmployeeRepository;
import github.tavi903.hr.repository.IJobHistoryRepository;
import github.tavi903.hr.repository.IJobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
public class EmployeeService extends BaseService<Long, Employee, EmployeeDto> {
    private final IJobRepository jobRepository;
    private final IJobHistoryRepository jobHistoryRepository;
    private final IDepartmentRepository departmentRepository;
    private final IEmployeeRepository employeeRepository;

    public EmployeeService(
            IJobRepository jobRepository,
            IJobHistoryRepository jobHistoryRepository,
            IDepartmentRepository departmentRepository,
            IEmployeeRepository repository,
            IEmployeeMapper mapper
    ) {
        super(repository, mapper);
        this.jobRepository = jobRepository;
        this.jobHistoryRepository = jobHistoryRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = repository;
    }

    @Transactional
    public EmployeeDto create(EmployeeDto employeeDto) {
        if (employeeDto.getId() != null)
            throw new HrException("Cannot create an Employee with a predefined Id!");
        EmployeeDto createdEmployee = createOrUpdate(employeeDto);
        createNewJobHistory(createdEmployee, createdEmployee.getHireDate().getTime());
        return createdEmployee;
    }

    private void createNewJobHistory(EmployeeDto employeeDto, Long startDate) {
        JobHistory jobHistory = JobHistory.builder()
                .primaryKey(JobHistory.createPK(employeeDto.getId(), new Date(startDate)))
                .department(departmentRepository.getReferenceById(employeeDto.getDepartmentId()))
                .job(jobRepository.getReferenceById(employeeDto.getJobId()))
                .build();
        jobHistoryRepository.save(jobHistory);
    }

    @Transactional
    public EmployeeDto update(EmployeeDto employeeDto) {
        if (employeeDto.getId() == null)
            throw new HrException("Cannot update Employee without an Id!");
        Employee oldEmployeeInfo = employeeRepository.findById(employeeDto.getId())
                .orElseThrow(() -> new HrException("Employee with id " + employeeDto.getId() + " not found."));
        Long oldDepartmentId = oldEmployeeInfo.getDepartment().getId();
        String oldJobId = oldEmployeeInfo.getJob().getId();
        Date oldHireDate = oldEmployeeInfo.getHireDate();
        if (!oldHireDate.toLocalDate().equals(new Date(employeeDto.getHireDate().getTime()).toLocalDate()))
            throw new HrException("It's not possible to change Employee Hire Date!");
        EmployeeDto updatedEmployee = createOrUpdate(employeeDto);
        if (!oldDepartmentId.equals(updatedEmployee.getDepartmentId()) || !oldJobId.equals(updatedEmployee.getJobId())) {
            jobHistoryRepository.findByPrimaryKey_EmployeeIdAndEndDateIsNull(updatedEmployee.getId()).ifPresent(
                    jobHistory -> {
                        jobHistory.setEndDate(new Date(System.currentTimeMillis()));
                        jobHistoryRepository.save(jobHistory);
                    }
            );
            createNewJobHistory(updatedEmployee, System.currentTimeMillis());
        }
        return updatedEmployee;
    }

    @Override
    public EmployeeDto createOrUpdate(EmployeeDto employeeDto) {
        Job job = jobRepository.findById(employeeDto.getJobId())
                .orElseThrow(() -> new HrException("Job with id: " + employeeDto.getJobId() + " does not exist."));
        if (employeeDto.getSalary() < job.getMinSalary() || employeeDto.getSalary() > job.getMaxSalary()) {
            throw new HrException("Salary for a " + employeeDto.getJobId() + " must be between " + job.getMinSalary() + "-" + job.getMaxSalary());
        }
        if (!departmentRepository.existsById(employeeDto.getDepartmentId()))
            throw new HrException("Department with id " + employeeDto.getDepartmentId() + " does not exist.");
        if (employeeDto.getManagerId() != null && !employeeRepository.existsById(employeeDto.getManagerId()))
            throw new HrException("Manager with Id " + employeeDto.getManagerId() + " does not exist.");
        return super.createOrUpdate(employeeDto);
    }

    public Page<EmployeeDto> searchEmployees(EmployeeSearchDto searchDto, Pageable pageable) {
        Specification<Employee> employeeSpecification = EmployeeSpecs.fromSearchDto(searchDto);
        Page<Employee> employees = employeeRepository.findAll(employeeSpecification, EmployeeSpecs.countFromSearchDto(searchDto), pageable);
        return new PageImpl<>(
          mapper.fromEntities(employees.getContent()),
          pageable,
          employees.getTotalElements()
        );
    }

}
