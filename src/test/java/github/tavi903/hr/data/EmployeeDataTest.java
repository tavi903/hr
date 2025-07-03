package github.tavi903.hr.data;

import github.tavi903.hr.entity.Employee;
import github.tavi903.hr.entity.EmployeeSpecs;
import github.tavi903.hr.repository.IEmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

@DataJpaTest
public class EmployeeDataTest {
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Test
    void given_aSearch_when_Searched_thenEmployeesAreRetrieved() {
        employeeRepository.findAll(EmployeeSpecs.firstNameLike("Al%"), Sort.sort(Employee.class).by(Employee::getFirstName)).forEach(
                employee -> System.out.println(employee.getFirstName())
        );

    }
}
