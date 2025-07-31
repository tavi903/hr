package github.tavi903.hr.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class SummaryDataDto {
    Integer numEmployees;
    Integer numCountries;
    Integer numCities;
    List<Integer> numEmployeesPerCountry;
    List<String> countryLabels;
    List<Integer> numEmployeesPerCity;
    List<String> cityLabels;
}
