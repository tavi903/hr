package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.JobHistoryDto;
import github.tavi903.hr.entity.JobHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IJobHistoryMapper extends IBaseMapper<JobHistory, JobHistoryDto> {
}
