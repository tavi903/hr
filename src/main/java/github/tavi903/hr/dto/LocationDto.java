package github.tavi903.hr.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LocationDto {
    private Long id;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;
}
