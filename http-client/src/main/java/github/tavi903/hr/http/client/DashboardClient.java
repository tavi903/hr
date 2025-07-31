package github.tavi903.hr.http.client;

import github.tavi903.hr.dto.SummaryDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "dashboardClient", url="${hr.backend.url}", path="/dashboard")
public interface DashboardClient {
    @GetMapping
    SummaryDataDto getSummaryData();
}
