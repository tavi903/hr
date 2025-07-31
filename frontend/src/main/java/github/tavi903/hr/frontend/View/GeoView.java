package github.tavi903.hr.frontend.View;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.tavi903.hr.dto.CountryDto;
import github.tavi903.hr.dto.LocationDto;
import github.tavi903.hr.dto.RegionDto;
import github.tavi903.hr.frontend.components.Form;
import github.tavi903.hr.frontend.components.Table;
import github.tavi903.hr.http.client.CountryClient;
import github.tavi903.hr.http.client.LocationClient;
import github.tavi903.hr.http.client.RegionClient;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GeoView {
    private final RegionClient regionClient;
    private final CountryClient countryClient;
    private final LocationClient locationClient;
    private final ObjectMapper objectMapper;

    public void createGeoView(HttpSession httpSession, Model model) throws JsonProcessingException {
        Integer regionPage = ViewUtils.getPageAttribute(httpSession, "regionTable.page");
        Integer countryPage = ViewUtils.getPageAttribute(httpSession, "countryTable.page");
        Integer locationPage = ViewUtils.getPageAttribute(httpSession, "locationTable.page");
        addRegionTable(regionPage, model);
        addCountryTable(countryPage, model);
        addLocationTable(locationPage, model);
    }

    @SuppressWarnings("all")
    private void addCountryTable(Integer countryPage, Model model) throws JsonProcessingException {
        PageRequest pageRequest = PageRequest.of(countryPage, 8, Sort.by("countryName"));
        Page<CountryDto> countries = countryClient.getAll(null, pageRequest);
        Table<CountryDto> countryTable = Table.of(
                        countries.getContent(),
                        country -> country.getId().toString(),
                        List.of(
                                country -> country.getId().toString(),
                                CountryDto::getCountryName,
                                CountryDto::getRegionName
                        )
                ).andIsModifiable()
                .withJson(objectMapper.writeValueAsString(countries.getContent()))
                .withPagination(
                        Table.Pagination.of(8, countryPage, countries.getTotalElements(), "/geo")
                )
                .andCanAddRow()
                .withAddButtonText("Add Country")
                .withTitle("Countries");
        Form countryForm = Form.builder()
                .elements(
                        List.of(
                                Form.Label.builder().innerText("Country Id").build(),
                                Form.Input.builder().name("id").type(Form.Input.Type.TEXT).build(),
                                Form.Label.builder().innerText("Country Name").build(),
                                Form.Input.builder().name("countryName").type(Form.Input.Type.TEXT).build(),
                                Form.Label.builder().innerText("Region").build(),
                                Form.Select.builder().name("regionId").options(
                                        regionClient.getAll(Pageable.unpaged()).stream()
                                                .sorted((r1, r2) -> r1.getRegionName().compareTo(r2.getRegionName()))
                                                .map(region -> Pair.of(region.getId().toString(), region.getRegionName())).toList()
                                ).build()
                        )
                )
                .title("Country Form")
                .formAction("/geo/country")
                .build();
        countryForm.withJson(objectMapper.writeValueAsString(countryForm));
        countryTable.withForm(countryForm);
        model.addAttribute("countryTable", countryTable);
    }

    private void addLocationTable(Integer locationPage, Model model) throws JsonProcessingException {
        PageRequest pageRequest = PageRequest.of(locationPage, 8, Sort.by("country_countryName", "city"));
        Page<LocationDto> locations = locationClient.getAll(null, pageRequest);
        Table<LocationDto> locationTable = Table.of(
                        locations.getContent(),
                        location -> location.getId().toString(),
                        List.of(
                                LocationDto::getCountryName,
                                LocationDto::getStateProvince,
                                LocationDto::getCity,
                                LocationDto::getPostalCode,
                                LocationDto::getStreetAddress
                        )
                ).andIsModifiable()
                .withHeaders(List.of("Country Name", "State Province", "City", "Postal Code", "Street Address"))
                .withJson(objectMapper.writeValueAsString(locations.getContent()))
                .withPagination(
                        Table.Pagination.of(8, locationPage, locations.getTotalElements(), "/geo")
                )
                .andCanAddRow()
                .withAddButtonText("Add Location")
                .withTitle("Locations");
        Form locationForm = Form.builder()
                .elements(
                        List.of(
                                Form.Label.builder().innerText("Country Name").build(),
                                Form.Input.builder().name("countryName").type(Form.Input.Type.TEXT).build(),
                                Form.Label.builder().innerText("State Province").build(),
                                Form.Input.builder().name("stateProvince").type(Form.Input.Type.TEXT).build(),
                                Form.Label.builder().innerText("City").build(),
                                Form.Input.builder().name("city").type(Form.Input.Type.TEXT).build(),
                                Form.Label.builder().innerText("Postal Code").build(),
                                Form.Input.builder().name("postalCode").type(Form.Input.Type.TEXT).build(),
                                Form.Label.builder().innerText("Street Address").build(),
                                Form.Input.builder().name("streetAddress").type(Form.Input.Type.TEXT).build()
                        )
                )
                .title("Location Form")
                .formAction("/geo/location")
                .build();
        locationForm.withJson(objectMapper.writeValueAsString(locationForm));
        locationTable.withForm(locationForm);
        model.addAttribute("locationTable", locationTable);
    }

    private void addRegionTable(Integer regionPage, Model model) throws JsonProcessingException {
        PageRequest pageRequest = PageRequest.of(regionPage, 8, Sort.by("regionName"));
        Page<RegionDto> regions = regionClient.getAll(pageRequest);
        Table<RegionDto> regionTable = Table.of(
                        regions.getContent(),
                        region -> region.getId().toString(),
                        List.of(
//                                region -> region.getId().toString(),
                                RegionDto::getRegionName
                        )
                ).andIsModifiable()
                .withJson(objectMapper.writeValueAsString(regions.getContent()))
                .withPagination(
                        Table.Pagination.of(8, regionPage, regions.getTotalElements(), "/geo")
                )
                .andCanAddRow()
                .withAddButtonText("Add Region")
                .withTitle("Regions");
        Form regionForm = Form.builder()
                .elements(
                        List.of(
//                                Form.Label.builder().innerText("Region Id").build(),
//                                Form.Input.builder().name("id").type(Form.Input.Type.TEXT).build(),
                                Form.Label.builder().innerText("Region Name").build(),
                                Form.Input.builder().name("regionName").type(Form.Input.Type.TEXT).build()
                        )
                )
                .title("Region Form")
                .formAction("/geo/region")
                .build();
        regionForm.withJson(objectMapper.writeValueAsString(regionForm));
        regionTable.withForm(regionForm);
        model.addAttribute("regionTable", regionTable);
    }

}
