package github.tavi903.hr.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegionDto {
    private Long id;
    private String regionName;
}
