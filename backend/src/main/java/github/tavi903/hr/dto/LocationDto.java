package github.tavi903.hr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class LocationDto {
    Long id;
    @NotBlank(message = "Street Address Cannot Be Empty!")
    @NotNull(message = "Street Address Cannot Be Empty!")
    String streetAddress;
    @NotBlank(message = "Postal Code Cannot Be Empty!")
    @NotNull(message = "Postal Code Cannot Be Empty!")
    String postalCode;
    @NotBlank(message = "City Cannot Be Empty!")
    @NotNull(message = "City Cannot Be Empty!")
    String city;
    @NotBlank(message = "State Province Cannot Be Empty!")
    @NotNull(message = "State Province Be Empty!")
    String stateProvince;
    String countryId;
    String countryName;
}
