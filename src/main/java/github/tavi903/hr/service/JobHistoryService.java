package github.tavi903.hr.service;

import github.tavi903.hr.dto.JobHistoryDto;
import github.tavi903.hr.entity.JobHistory;
import github.tavi903.hr.mapper.IJobHistoryMapper;
import github.tavi903.hr.repository.IJobHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class JobHistoryService extends BaseService<JobHistory.PrimaryKey, JobHistory, JobHistoryDto> {

    public JobHistoryService(IJobHistoryRepository repository, IJobHistoryMapper mapper) {
        super(repository, mapper);
    }

}
