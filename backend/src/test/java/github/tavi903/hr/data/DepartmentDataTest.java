package github.tavi903.hr.data;

import github.tavi903.hr.entity.Department;
import github.tavi903.hr.repository.IDepartmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class DepartmentDataTest {
    @Autowired
    private IDepartmentRepository departmentRepository;

    @Test
    void given_aCountryId_when_searched_thenRetrievedDepartmentsOnlyForThatCountry() {
        Page<Department> gbDepartments = departmentRepository.findByLocation_Country_Id("GB", Pageable.unpaged());
        Assertions.assertEquals(2, gbDepartments.getTotalElements());
    }
}
