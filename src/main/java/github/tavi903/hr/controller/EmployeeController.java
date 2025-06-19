package github.tavi903.hr.controller;

import github.tavi903.hr.dto.EmployeeDto;
import github.tavi903.hr.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService service;

    @GetMapping("get-all")
    public Page<EmployeeDto> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

//    @GetMapping("get-all")
//    public Page<EmployeeDto> getAll(Pageable pageable) {
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

    @GetMapping("get-all-async")
    public DeferredResult<Page<EmployeeDto>> getAllAsync(Pageable pageable) {
        DeferredResult<Page<EmployeeDto>> deferredResult = new DeferredResult<>();
        new Thread(() -> {
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            deferredResult.setResult(service.findAll(pageable));
        }
        ).start();
        return deferredResult;
    }

}
