package github.tavi903.hr.entity;

import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecs {
    public static Specification<Employee> firstNameLike(String firstName) {
        return (root, query, builder) -> builder.like(root.get(Employee_.firstName), "%" + firstName + "%");
    }
    public static Specification<Employee> lastNameLike(String lastName) {
        return (root, query, builder) -> builder.like(root.get(Employee_.lastName), "%" + lastName + "%");
    }
    public static Specification<Employee> emailLike(String email) {
        return (root, query, builder) -> builder.like(root.get(Employee_.email), "%" + email + "%");
    }
}
