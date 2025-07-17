package github.tavi903.hr.repository;

import github.tavi903.hr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
//    @Query("SELECT e FROM Employee e JOIN FETCH e.department d JOIN FETCH e.job j")
//    Page<Employee> findAll(Specification<Employee> spec, Pageable pageable);
}
