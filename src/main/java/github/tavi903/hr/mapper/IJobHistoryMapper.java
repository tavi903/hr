package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.JobHistoryDto;
import github.tavi903.hr.entity.JobHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IJobHistoryMapper extends IBaseMapper<JobHistory, JobHistoryDto> {
    @Override
    @Mapping(target = "employeeId", source = "primaryKey.employeeId")
    @Mapping(target = "startDate", source = "primaryKey.startDate")
    JobHistoryDto fromEntity(JobHistory entity);
}
