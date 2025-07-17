package github.tavi903.hr.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Value;

@Value
public class PercentageDto {
    @Min(value = -50)
    @Max(value = 50)
    Integer percentage;
}
