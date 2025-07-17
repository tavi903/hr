package github.tavi903.hr.frontend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.tavi903.hr.dto.RegionDto;
import github.tavi903.hr.exception.HrException;
import github.tavi903.hr.http.client.RegionClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class HomeController {
    private final RegionClient regionClient;
    private final ObjectMapper objectMapper;

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/geo")
    public String geo(Model model) throws JsonProcessingException {
        createGeoView(model);
        return "geo";
    }

    @SuppressWarnings("all")
    private void createGeoView(Model model) throws JsonProcessingException {
        List<RegionDto> regions = regionClient.getAll();
        Table<RegionDto> regionTable = Table.of(
                regions,
                region -> region.getId().toString(),
                List.of(
                        region -> region.getId().toString(),
                        RegionDto::getRegionName
                )
        ).andIsModifiable()
                .withJson(objectMapper.writeValueAsString(regions))
                .withTitle("Regions");
        model.addAttribute("regionTable", regionTable);
    }

    @PostMapping(value = "/region/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateRegion(RegionDto regionDto, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        try {
            regionClient.update(regionDto);
        } catch (HrException e) {
            redirectAttributes.addFlashAttribute("modalType", "modification");
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("currentRegion", objectMapper.writeValueAsString(regionDto));
        }
        return "redirect:/geo";
    }
    @PostMapping(value = "/region/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createRegion(RegionDto regionDto, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        try {
            regionClient.create(regionDto);
        } catch (HrException e) {
            redirectAttributes.addFlashAttribute("modalType", "creation");
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("currentRegion", objectMapper.writeValueAsString(regionDto));
        }
        return "redirect:/geo";
    }


}
