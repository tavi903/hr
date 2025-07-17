package github.tavi903.hr.entity;

import github.tavi903.hr.dto.EmployeeSearchDto;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;

public class EmployeeSpecs {
    private static Specification<Employee> joinFetch() {
        return (root, query, builder) -> {
            root.fetch(Employee_.department, JoinType.LEFT);
            root.fetch(Employee_.job, JoinType.LEFT);
            return null;
        };
    }
    public static Specification<Employee> firstNameLike(String firstName) {
        return (root, query, builder) -> builder.like(root.get(Employee_.firstName), "%" + firstName + "%");
    }
    public static Specification<Employee> lastNameLike(String lastName) {
        return (root, query, builder) -> builder.like(root.get(Employee_.lastName), "%" + lastName + "%");
    }
    public static Specification<Employee> emailLike(String email) {
        return (root, query, builder) -> builder.like(root.get(Employee_.email), "%" + email + "%");
    }
    public static Specification<Employee> hireDateLessThanOrEqual(Date toDate) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(Employee_.hireDate), toDate);
    }
    public static Specification<Employee> hireDateGreaterThanOrEqual(Date toDate) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(Employee_.hireDate), toDate);
    }
    public static Specification<Employee> isJob(String jobId) {
        return (root, query, builder) -> builder.equal(root.get(Employee_.job).get(Job_.ID), jobId);
    }
    public static Specification<Employee> salaryGreaterThanOrEqual(Double fromSalary) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(Employee_.salary), fromSalary);
    }
    public static Specification<Employee> salaryLessThanOrEqual(Double toSalary) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(Employee_.salary), toSalary);
    }
    public static Specification<Employee> isDepartment(Long departmentId) {
        return (root, query, builder) -> builder.equal(root.get(Employee_.department).get(Department_.ID), departmentId);
    }

    public static Specification<Employee> fromSearchDto(EmployeeSearchDto searchDto) {
        Specification<Employee> spec = joinFetch();
        return fromSearchDto(spec, searchDto);
    }

    public static Specification<Employee> fromSearchDto(Specification<Employee> spec, EmployeeSearchDto searchDto) {
        if (spec == null)
            spec = Specification.not(null);
        if (searchDto == null)
            return spec;
        if (searchDto.getFirstNameLike() != null)
            spec = spec.and(firstNameLike(searchDto.getFirstNameLike()));
        if (searchDto.getLastNameLike() != null)
            spec = spec.and(lastNameLike(searchDto.getLastNameLike()));
        if (searchDto.getEmailLike() != null)
            spec = spec.and(emailLike(searchDto.getEmailLike()));
        if (searchDto.getHireDateLessThanOrEqual() != null)
            spec = spec.and(hireDateLessThanOrEqual(new Date(searchDto.getHireDateLessThanOrEqual().getTime())));
        if (searchDto.getHireDateGreaterThanOrEqual() != null)
            spec = spec.and(hireDateGreaterThanOrEqual(new Date(searchDto.getHireDateGreaterThanOrEqual().getTime())));
        if (searchDto.getJobId() != null)
            spec = spec.and(isJob(searchDto.getJobId()));
        if (searchDto.getSalaryLessThanOrEqual() != null)
            spec = spec.and(salaryLessThanOrEqual(searchDto.getSalaryLessThanOrEqual()));
        if (searchDto.getSalaryGreaterThanOrEqual() != null)
            spec = spec.and(salaryGreaterThanOrEqual(searchDto.getSalaryGreaterThanOrEqual()));
        if (searchDto.getDepartmentId() != null)
            spec = spec.and(isDepartment(searchDto.getDepartmentId()));
        return spec;
    }

}
