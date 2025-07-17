package github.tavi903.hr.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class JobHistoryDto {
    Long employeeId;
    Date startDate;
    Date endDate;
}
