package github.tavi903.hr.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Builder
@Value
public class EmployeeSearchDto {
    String firstNameLike;
    String lastNameLike;
    String emailLike;
    Date hireDateLessThanOrEqual;
    Date hireDateGreaterThanOrEqual;
    String jobId;
    Double salaryLessThanOrEqual;
    Double salaryGreaterThanOrEqual;
    Long departmentId;
}
