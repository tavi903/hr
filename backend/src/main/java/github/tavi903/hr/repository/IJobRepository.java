package github.tavi903.hr.repository;

import github.tavi903.hr.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IJobRepository extends JpaRepository<Job, String> {
    @Modifying
    @NativeQuery("UPDATE Jobs SET min_salary = round(min_salary * (1  + :variation))")
    void modifyMinSalary(@Param("variation") Double variation);

    /*It doesn't work without CAST because it tries to set up an Integer */
    @Modifying
    @Query("UPDATE Job j SET j.minSalary = round(j.minSalary * (1  + CAST(:variation AS Double)))")
    void modifyMinSalaryV2(@Param("variation") Double variation);

    @Modifying
    @Query("UPDATE Job j SET j.maxSalary = round(j.maxSalary * (1  + CAST(:variation AS Double)))")
    void modifyMaxSalary(@Param("variation") Double variation);

    @Query("SELECT COUNT(*) > 0 FROM Job j WHERE j.minSalary > j.maxSalary")
    boolean checkIfMinSalaryGreaterThanMaxSalary();
}
