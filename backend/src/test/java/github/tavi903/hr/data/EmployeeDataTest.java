package github.tavi903.hr.data;

import github.tavi903.hr.dto.EmployeeSearchDto;
import github.tavi903.hr.entity.Department;
import github.tavi903.hr.entity.Employee;
import github.tavi903.hr.entity.EmployeeSpecs;
import github.tavi903.hr.entity.Employee_;
import github.tavi903.hr.entity.Job;
import github.tavi903.hr.entity.JobHistory;
import github.tavi903.hr.repository.IEmployeeRepository;
import jakarta.persistence.RollbackException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.TestPropertySource;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@DataJpaTest
@TestPropertySource(properties = { "logging.level.org.springframework=ERROR" })
public class EmployeeDataTest {
    @Autowired
    private IEmployeeRepository employeeRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    void given_aSearch_when_Searched_thenEmployeesAreRetrieved() {
        employeeRepository.findAll(
                    EmployeeSpecs.isJob("IT_PROG")
//                            .and(EmployeeSpecs.firstNameLike("D"))
                            .and(
                                    EmployeeSpecs.hireDateGreaterThanOrEqual(new Date(getMillisSecondsFrom1970(2017, 1, 1)))
                                            .or(EmployeeSpecs.hireDateLessThanOrEqual(new Date(getMillisSecondsFrom1970(2016, 1, 1))))
                            )
                        ,
                        Sort.sort(Employee.class).by(Employee::getFirstName)
                )
                .forEach(
                employee -> System.out.println(employee.getFirstName())
        );
    }

    @Test
    void given_aSearchWithoutSpecification_when_retrieved_thenAllResults() {
        employeeRepository.findAll(
                        (root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(Employee_.id)),
                        Sort.sort(Employee.class).by(Employee::getFirstName)
                )
                .forEach(
                        employee -> System.out.println(employee.getFirstName())
                );
    }

    @Test
    void given_joinFetch_when_retrieved_thenDoesntMultipleQueries() {
        employeeRepository.findAll(joinFetch(), Pageable.unpaged()).forEach(employee -> System.out.println(employee.getFirstName()));
    }

    @Test
    void given_SearchDto_when_Retrieved_thenIsOk() {
        EmployeeSearchDto itProg = EmployeeSearchDto.builder().jobId("SH_CLERK").build();
        Pageable pageable = PageRequest.of(0, 20, Sort.sort(Employee.class).by(Employee::getLastName).ascending());
        employeeRepository.findAll(EmployeeSpecs.fromSearchDto(itProg), EmployeeSpecs.countFromSearchDto(itProg), pageable)
                .forEach(employee -> System.out.println(employee.getFirstName()));
    }

    private Specification<Employee> joinFetch() {
        return (root, query, builder) -> {
            root.fetch(Employee_.department);
            root.fetch(Employee_.job);
            return null;

//                        .on(builder.equal(root.get(Employee_.department).get(Department_.id), query.from(Department.class).get(Department_.id)))

        };

    }

    private long getMillisSecondsFrom1970(int year, int month, int day) {
        return java.util.Date.from(LocalDateTime.of(year, month, day, 0, 0).toInstant(ZoneOffset.UTC)).getTime();
    }

    @Test
    void given_AnEmployeeWithoutEmail_when_saved_thenError() {
        Employee employee = Employee.builder()
                .department(Department.builder().id(210L).build())
                .job(Job.builder().id("IT_PROG").build())
                .firstName("Pippo")
                .lastName("Topolino")
                .salary(4500D).build();
        em.persist(employee);
        Assertions.assertThrows(RollbackException.class, () -> em.getEntityManager().getTransaction().commit());
    }

    @Test
    void given_aJobHistory_when_saved_thenError() {
        JobHistory jobHistory = JobHistory.builder()
                .primaryKey(JobHistory.createPK(100L, new Date(System.currentTimeMillis())))
                .department(em.getEntityManager().getReference(Department.class, 210L)) // Department.builder().id(210L).build()
                .job(em.getEntityManager().getReference(Job.class, "IT_PROG")) // Job.builder().id("IT_PROG").build()
                .build();
        em.persist(jobHistory);
        em.getEntityManager().getTransaction().commit();
    }
}
