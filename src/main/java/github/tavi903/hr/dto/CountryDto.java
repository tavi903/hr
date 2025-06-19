package github.tavi903.hr.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CountryDto {
    private String id;
    private String countryName;
}
