package github.tavi903.hr.service;

import github.tavi903.hr.exception.HrException;
import github.tavi903.hr.dto.DepartmentDto;
import github.tavi903.hr.entity.Country;
import github.tavi903.hr.entity.Department;
import github.tavi903.hr.entity.Location;
import github.tavi903.hr.mapper.IDepartmentMapper;
import github.tavi903.hr.repository.IDepartmentRepository;
import github.tavi903.hr.repository.IEmployeeRepository;
import github.tavi903.hr.repository.ILocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService extends BaseService<Long, Department, DepartmentDto> {
    private final IDepartmentRepository departmentRepository;
    private final ILocationRepository locationRepository;
    private final IEmployeeRepository employeeRepository;

    public DepartmentService(
            IDepartmentRepository repository,
            IDepartmentMapper mapper,
            ILocationRepository locationRepository,
            IEmployeeRepository employeeRepository
    ) {
        super(repository, mapper);
        this.departmentRepository = repository;
        this.locationRepository = locationRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public DepartmentDto createOrUpdate(DepartmentDto departmentDto) {
        if (departmentDto.getId() != null && !departmentRepository.existsById(departmentDto.getId()))
            throw new HrException("Department with id " + departmentDto.getId() + " does not exist.");
        if (departmentDto.getManagerId() != null && !employeeRepository.existsById(departmentDto.getManagerId()))
            throw new HrException("Manager with id " + departmentDto.getManagerId() + " does not exist.");
        if (!locationRepository.existsById(departmentDto.getLocationId()))
            throw new HrException("Location with id " + departmentDto.getLocationId() + " does not exist.");
        return super.createOrUpdate(departmentDto);
    }

    public Page<DepartmentDto> findDepartmentsByCountry(String countryId, Pageable pageable) {
        Page<Department> page;
        Pageable pageRequestWithSortByCountryAndCityAndDepartmentName =
                PageRequest.of(
                        pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.sort(Department.class)
                                .by(Department::getLocation)
                                .by(Location::getCountry)
                                .by(Country::getCountryName)
                                .and(
                                        Sort.sort(Department.class)
                                                .by(Department::getLocation)
                                                .by(Location::getCity)
                                )
                                .and(
                                        Sort.sort(Department.class)
                                                .by(Department::getDepartmentName)
                                )
                );
        if (countryId != null && !countryId.isEmpty()) {
            page = departmentRepository.findByLocation_Country_Id(countryId, pageRequestWithSortByCountryAndCityAndDepartmentName);
        } else {
            page = departmentRepository.findAll(pageRequestWithSortByCountryAndCityAndDepartmentName);
        }
        return new PageImpl<>(
                super.mapper.fromEntities(page.getContent()),
                pageRequestWithSortByCountryAndCityAndDepartmentName,
                page.getTotalElements()
        );
    }
}
