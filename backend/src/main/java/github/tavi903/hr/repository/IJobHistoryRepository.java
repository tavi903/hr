package github.tavi903.hr.repository;

import github.tavi903.hr.entity.JobHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IJobHistoryRepository extends JpaRepository<JobHistory, JobHistory.PrimaryKey> {
    List<JobHistory> findByPrimaryKey_EmployeeId(Long employeeId);
    Optional<JobHistory> findByPrimaryKey_EmployeeIdAndEndDateIsNull(Long employeeId);
}
