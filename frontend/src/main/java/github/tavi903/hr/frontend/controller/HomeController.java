package github.tavi903.hr.frontend.controller;

import github.tavi903.hr.dto.SummaryDataDto;
import github.tavi903.hr.http.client.DashboardClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class HomeController {
    private final DashboardClient dashboardClient;

    @GetMapping
    public String home(Model model) {
        SummaryDataDto summaryData = dashboardClient.getSummaryData();
        model.addAttribute("summaryData", summaryData);
        return "home";
    }

}
