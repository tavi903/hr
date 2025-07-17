package github.tavi903.hr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DepartmentDto {
    Long id;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z ]+")
    @Size(min = 3, max = 25)
    String departmentName;
    Long managerId;
    @NotNull
    Long locationId;
    String city;
    String countryName;
}
