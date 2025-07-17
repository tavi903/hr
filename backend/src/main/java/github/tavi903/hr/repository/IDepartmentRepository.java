package github.tavi903.hr.repository;

import github.tavi903.hr.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IDepartmentRepository extends JpaRepository<Department, Long> {
    Page<Department> findByLocation_Country_Id(String countryId, Pageable pageable);
}
