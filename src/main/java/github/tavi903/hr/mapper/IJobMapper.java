package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.JobDto;
import github.tavi903.hr.entity.Job;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IJobMapper extends IBaseMapper<Job, JobDto> {
}
