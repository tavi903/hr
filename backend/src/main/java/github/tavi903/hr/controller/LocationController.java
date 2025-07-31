package github.tavi903.hr.controller;

import github.tavi903.hr.dto.LocationDto;
import github.tavi903.hr.service.LocationService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static github.tavi903.hr.config.AppConstants.MANAGER_ROLE;
import static github.tavi903.hr.config.AppConstants.HR_ROLE;

@RestController
@RequestMapping(value = "/api/v1/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @RolesAllowed(MANAGER_ROLE)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationDto> create(@Validated @RequestBody LocationDto locationDto) {
        LocationDto regionCreated = locationService.create(locationDto);
        return ResponseEntity.ok(regionCreated);
    }

    @RolesAllowed(MANAGER_ROLE)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationDto> update(@Validated @RequestBody LocationDto locationDto) {
        LocationDto regionCreated = locationService.update(locationDto);
        return ResponseEntity.ok(regionCreated);
    }

    @RolesAllowed(HR_ROLE)
    @GetMapping(value = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<LocationDto> getAll(@RequestParam(required = false) String countryId, Pageable pageable) {
        return locationService.findAll(countryId, pageable);
    }

}
