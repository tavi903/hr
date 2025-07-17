package github.tavi903.hr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class RegionDto {
    Long id;
    @NotBlank(message = "Region Name Cannot Be Blank!")
    @Pattern(regexp = "[a-zA-Z]+", message = "Region Name Should Have Only Characters!")
    @Size(min = 3, max = 25, message = "Region Name Should Be Of Length 3-25!")
    String regionName;
}
