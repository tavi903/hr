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
    @NotBlank(message = "Country Id Cannot Be Blank!")
    @Size(max = 2, message = "Country Id Should Be Just two Letters.")
    @Pattern(regexp = "[A-Z]+", message = "Country Id Should be just Uppercase letters.")
    String id;
    @NotBlank
    @Pattern(regexp = "[A-Za-z ]+", message = "Country Name Should Be Just Characters with Spaces.")
    String countryName;
    @NotNull
    Long regionId;
    String regionName;
}
