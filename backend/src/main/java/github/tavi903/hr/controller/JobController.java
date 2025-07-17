package github.tavi903.hr.controller;

import github.tavi903.hr.dto.JobDto;
import github.tavi903.hr.dto.PercentageDto;
import github.tavi903.hr.service.JobService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static github.tavi903.hr.config.AppConstants.MANAGER_ROLE;
import static github.tavi903.hr.config.AppConstants.HR_ROLE;

@RestController
@RequestMapping("/api/v1/job")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @RolesAllowed(MANAGER_ROLE)
    @PutMapping
    public ResponseEntity<JobDto> createOrUpdate(@Validated @RequestBody JobDto jobDto) {
        return ResponseEntity.ok(jobService.createOrUpdate(jobDto));
    }

    @RolesAllowed(HR_ROLE)
    @GetMapping("get-all")
    public Page<JobDto> getAll(Pageable pageable) {
        return jobService.findAll(pageable);
    }

    @RolesAllowed(MANAGER_ROLE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("modify/minSalary")
    public void changeMinSalaryByPercentage(@Validated @RequestBody PercentageDto percentageDto) {
        jobService.changeMinSalary(percentageDto);
    }

    @RolesAllowed(MANAGER_ROLE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("modify/maxSalary")
    public void changeMaxSalaryByPercentage(@Validated @RequestBody PercentageDto percentageDto) {
        jobService.changeMaxSalary(percentageDto);
    }

}
