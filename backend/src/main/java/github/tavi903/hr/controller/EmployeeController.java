package github.tavi903.hr.controller;

import github.tavi903.hr.dto.EmployeeDto;
import github.tavi903.hr.dto.EmployeeSearchDto;
import github.tavi903.hr.service.EmployeeService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static github.tavi903.hr.config.AppConstants.MANAGER_ROLE;
import static github.tavi903.hr.config.AppConstants.HR_ROLE;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService service;

    @RolesAllowed(MANAGER_ROLE)
    @PostMapping
    public EmployeeDto create(@Validated @RequestBody EmployeeDto employeeDto) {
        return service.create(employeeDto);
    }

    @RolesAllowed(MANAGER_ROLE)
    @PutMapping
    public EmployeeDto update(@Validated @RequestBody EmployeeDto employeeDto) {
        return service.update(employeeDto);
    }

    @RolesAllowed(HR_ROLE)
    @PostMapping("search")
    public Page<EmployeeDto> search(@RequestBody EmployeeSearchDto searchDto, Pageable pageable) {
        return service.searchEmployees(searchDto, pageable);
    }

//    @GetMapping("get-all")
//    public Page<EmployeeDto> getAll(Pageable pageable) {
//        return service.findAll(pageable);
//    }
//
//    @GetMapping("get-all-newThread")
//    public Page<EmployeeDto> getAllNewThread(Pageable pageable) {
//        AtomicReference<Page<EmployeeDto>> result = new AtomicReference<>();
//        new Thread(() -> {
//            try {
//                Thread.sleep(3_000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            result.set(service.findAll(pageable));
//        }
//        ).start();
//        return result.get();
//    }
//
//    @GetMapping("get-all-async")
//    public DeferredResult<Page<EmployeeDto>> getAllAsync(Pageable pageable) {
//        DeferredResult<Page<EmployeeDto>> deferredResult = new DeferredResult<>();
//        new Thread(() -> {
//            try {
//                Thread.sleep(3_000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            deferredResult.setResult(service.findAll(pageable));
//        }
//        ).start();
//        return deferredResult;
//    }

}
