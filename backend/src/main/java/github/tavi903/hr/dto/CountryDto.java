package github.tavi903.hr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CountryDto {
    @NotBlank
    @Size(max = 2)
    @Pattern(regexp = "[A-Z]+")
    String id;
    @NotBlank
    @Pattern(regexp = "[A-Za-z ]+")
    String countryName;
    @NotNull
    Long regionId;
    String regionName;
}
