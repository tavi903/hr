package github.tavi903.hr.http.client;

import github.tavi903.hr.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "departmentClient", url="${hr.backend.url}", path="/department")
public interface DepartmentClient {
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    DepartmentDto createOrUpdate(@RequestBody DepartmentDto departmentDto);

    @GetMapping(value = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<DepartmentDto> getAll(@RequestParam(required = false) String countryId, Pageable pageable);
}
