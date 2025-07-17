package github.tavi903.hr.controller;

import github.tavi903.hr.dto.CountryDto;
import github.tavi903.hr.service.CountryService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static github.tavi903.hr.config.AppConstants.MANAGER_ROLE;
import static github.tavi903.hr.config.AppConstants.HR_ROLE;

@RestController
@RequestMapping("/api/v1/country")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @RolesAllowed(MANAGER_ROLE)
    @PutMapping
    public ResponseEntity<CountryDto> createOrUpdate(@Validated @RequestBody CountryDto countryDto) {
        CountryDto countryCreated = countryService.createOrUpdate(countryDto);
        return ResponseEntity.ok(countryCreated);
    }


    @RolesAllowed(HR_ROLE)
    @GetMapping("get-all")
    public List<CountryDto> getAll(@RequestParam(required = false) Long regionId) {
        return countryService.findByRegionId(regionId);
    }

}
