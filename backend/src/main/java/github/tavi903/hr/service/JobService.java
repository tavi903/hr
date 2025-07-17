package github.tavi903.hr.service;

import github.tavi903.hr.exception.HrException;
import github.tavi903.hr.dto.JobDto;
import github.tavi903.hr.dto.PercentageDto;
import github.tavi903.hr.entity.Job;
import github.tavi903.hr.mapper.IJobMapper;
import github.tavi903.hr.repository.IJobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobService extends BaseService<String, Job, JobDto> {
    private final IJobRepository jobRepository;

    public JobService(IJobRepository repository, IJobMapper mapper) {
        super(repository, mapper);
        this.jobRepository = repository;
    }

    public JobDto createOrUpdate(JobDto jobDto) {
        if (jobDto.getMinSalary() > jobDto.getMaxSalary())
            throw new HrException("MinSalary should always be less than MaxSalary");
        return super.createOrUpdate(jobDto);
    }

    @Transactional
    public void changeMinSalary(PercentageDto percentageDto) {
        jobRepository.modifyMinSalaryV2(percentageDto.getPercentage() / 100d);
        if( jobRepository.checkIfMinSalaryGreaterThanMaxSalary() )
            throw new HrException("Cannot update! At least one MaxSalary would be below its MinSalary.");
    }

    @Transactional
    public void changeMaxSalary(PercentageDto percentageDto) {
        jobRepository.modifyMaxSalary(percentageDto.getPercentage() / 100d);
        if( jobRepository.checkIfMinSalaryGreaterThanMaxSalary() )
            throw new HrException("Cannot update! At least one MaxSalary would be below its MinSalary.");
    }

}
