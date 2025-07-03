package github.tavi903.hr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LocationDto {
    private Long id;
    @NotBlank
    @NotNull
    private String streetAddress;
    @NotBlank
    @NotNull
    private String postalCode;
    @NotBlank
    @NotNull
    private String city;
    @NotBlank
    @NotNull
    private String stateProvince;
    @NotBlank
    @NotNull
    private String countryId;
}
