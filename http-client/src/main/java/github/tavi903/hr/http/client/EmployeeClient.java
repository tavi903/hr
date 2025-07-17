package github.tavi903.hr.http.client;


import github.tavi903.hr.dto.EmployeeDto;
import github.tavi903.hr.dto.EmployeeSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "employeeClient", url="${hr.backend.url}", path="/employee")
public interface EmployeeClient {
    @PostMapping
    EmployeeDto create(@RequestBody EmployeeDto employeeDto);

    @PutMapping
    EmployeeDto update(@RequestBody EmployeeDto employeeDto);

    @PostMapping("search")
    Page<EmployeeDto> search(@RequestBody EmployeeSearchDto searchDto, Pageable pageable);
}
