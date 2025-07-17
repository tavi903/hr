package github.tavi903.hr.mapper;

import github.tavi903.hr.dto.JobDto;
import github.tavi903.hr.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ReferenceMapper.class)
public interface IJobMapper extends IBaseMapper<Job, JobDto> {
}
