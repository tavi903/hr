package github.tavi903.hr.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JobDto {
    private String id;
    private String jobTitle;
    private Double minSalary;
    private Double maxSalary;
}
