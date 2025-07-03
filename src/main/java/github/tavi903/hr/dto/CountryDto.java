package github.tavi903.hr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class CountryDto {
    @NotBlank
    @Pattern(regexp = "[A-Z]+")
    private String id;
    @NotBlank
    @Pattern(regexp = "[A-Za-z ]+")
    private String countryName;
    @NotNull
    private Long regionId;
}
