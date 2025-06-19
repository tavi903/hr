package github.tavi903.hr.repository;

import github.tavi903.hr.entity.JobHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IJobHistoryRepository extends JpaRepository<JobHistory, JobHistory.PrimaryKey> {
    List<JobHistory> findByPrimaryKey_EmployeeId(Long employeeId);
}
