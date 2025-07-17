package github.tavi903.hr.http.client;

import github.tavi903.hr.dto.JobDto;
import github.tavi903.hr.dto.PercentageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "jobClient", url="${hr.backend.url}", path="/job")
public interface JobClient {
    @PutMapping
    JobDto createOrUpdate(@RequestBody JobDto jobDto);

    @GetMapping("get-all")
    Page<JobDto> getAll(Pageable pageable);

    @PostMapping("modify/minSalary")
    void changeMinSalaryByPercentage(@RequestBody PercentageDto percentageDto);

    @PostMapping("modify/maxSalary")
    void changeMaxSalaryByPercentage(@RequestBody PercentageDto percentageDto);
}
