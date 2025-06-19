package github.tavi903.hr.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class JobHistoryDto {
    private Long employeeId;
    private Date startDate;
    private Date endDate;
}
