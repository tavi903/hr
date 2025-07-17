package github.tavi903.hr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class LocationDto {
    Long id;
    @NotBlank
    @NotNull
    String streetAddress;
    @NotBlank
    @NotNull
    String postalCode;
    @NotBlank
    @NotNull
    String city;
    @NotBlank
    @NotNull
    String stateProvince;
    @NotBlank
    @NotNull
    String countryId;
    String countryName;
}
