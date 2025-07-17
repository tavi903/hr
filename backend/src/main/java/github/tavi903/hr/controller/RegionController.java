package github.tavi903.hr.controller;

import github.tavi903.hr.dto.RegionDto;
import github.tavi903.hr.entity.Region;
import github.tavi903.hr.service.RegionService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static github.tavi903.hr.config.AppConstants.MANAGER_ROLE;
import static github.tavi903.hr.config.AppConstants.HR_ROLE;

@RestController
@RequestMapping(value = "/api/v1/region")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @RolesAllowed(MANAGER_ROLE)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionDto> create(@Validated @RequestBody RegionDto regionDto) {
        RegionDto regionCreated = regionService.createOrUpdate(regionDto);
        return ResponseEntity.ok(regionCreated);
    }

    @RolesAllowed(MANAGER_ROLE)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionDto> update(@Validated @RequestBody RegionDto regionDto) {
        RegionDto regionCreated = regionService.update(regionDto);
        return ResponseEntity.ok(regionCreated);
    }

    @RolesAllowed(HR_ROLE)
    @GetMapping(value = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RegionDto> getAll() {
        return regionService.findAll(
                        Pageable.unpaged(Sort.sort(Region.class).by(Region::getRegionName).ascending())
                )
                .getContent();
    }

}
