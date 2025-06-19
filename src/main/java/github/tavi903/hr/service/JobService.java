package github.tavi903.hr.service;

import github.tavi903.hr.dto.JobDto;
import github.tavi903.hr.entity.Job;
import github.tavi903.hr.mapper.IJobMapper;
import github.tavi903.hr.repository.IJobRepository;
import org.springframework.stereotype.Service;

@Service
public class JobService extends BaseService<String, Job, JobDto> {

    public JobService(IJobRepository repository, IJobMapper mapper) {
        super(repository, mapper);
    }

}
