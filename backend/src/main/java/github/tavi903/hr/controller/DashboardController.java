package github.tavi903.hr.controller;

import github.tavi903.hr.dto.SummaryDataDto;
import github.tavi903.hr.service.SummaryDataService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static github.tavi903.hr.config.AppConstants.HR_ROLE;

@RestController
@RequestMapping("api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final SummaryDataService summaryDataService;

    @RolesAllowed(HR_ROLE)
    @GetMapping
    public SummaryDataDto getSummaryData() {
        return summaryDataService.getSummaryData();
    }


}
