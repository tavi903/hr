package github.tavi903.hr.controller;

import github.tavi903.hr.dto.DepartmentDto;
import github.tavi903.hr.service.DepartmentService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static github.tavi903.hr.config.AppConstants.MANAGER_ROLE;
import static github.tavi903.hr.config.AppConstants.HR_ROLE;

@RestController
@RequestMapping(value = "/api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @RolesAllowed(MANAGER_ROLE)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartmentDto> createOrUpdate(@Validated @RequestBody DepartmentDto departmentDto) {
        DepartmentDto departmentUpdated = departmentService.createOrUpdate(departmentDto);
        return ResponseEntity.ok(departmentUpdated);
    }

    @RolesAllowed(HR_ROLE)
    @GetMapping(value = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<DepartmentDto> getAll(String countryId, Pageable pageable) {
        return departmentService.findDepartmentsByCountry(countryId, pageable);
    }

}
