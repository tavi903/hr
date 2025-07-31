package github.tavi903.hr.frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import github.tavi903.hr.dto.CountryDto;
import github.tavi903.hr.dto.LocationDto;
import github.tavi903.hr.dto.RegionDto;
import github.tavi903.hr.exception.HrException;
import github.tavi903.hr.frontend.View.GeoView;
import github.tavi903.hr.frontend.View.ViewUtils;
import github.tavi903.hr.http.client.CountryClient;
import github.tavi903.hr.http.client.LocationClient;
import github.tavi903.hr.http.client.RegionClient;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/geo")
public class GeoController {
    private final GeoView geoView;
    private final RegionClient regionClient;
    private final CountryClient countryClient;
    private final LocationClient locationClient;

    @GetMapping
    public String geo(
            HttpSession httpSession,
            Model model
    ) throws JsonProcessingException {
        geoView.createGeoView(httpSession, model);
        return "geo";
    }



    @PostMapping(value = "/region/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateRegion(RegionDto regionDto, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        try {
            regionClient.update(regionDto);
        } catch (HrException e) {
            redirectAttributes.addFlashAttribute("formError", e.getMessage());
            redirectAttributes.addFlashAttribute("formData", regionDto);
            redirectAttributes.addFlashAttribute("tableId", "regionTable");
            redirectAttributes.addFlashAttribute("formActionType", "/update");
        }
        return "redirect:/geo";
    }
    @PostMapping(value = "/region/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createRegion(RegionDto regionDto, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        try {
            regionClient.create(regionDto);
        } catch (HrException e) {
            redirectAttributes.addFlashAttribute("formError", e.getMessage());
            redirectAttributes.addFlashAttribute("formData", regionDto);
            redirectAttributes.addFlashAttribute("tableId", "regionTable");
            redirectAttributes.addFlashAttribute("formActionType", "/create");
        }
        return "redirect:/geo";
    }

    @PostMapping(value = "/country/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateCountry(CountryDto countryDto, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        try {
            countryClient.createOrUpdate(countryDto);
        } catch (HrException e) {
            redirectAttributes.addFlashAttribute("formError", e.getMessage());
            redirectAttributes.addFlashAttribute("formData", countryDto);
            redirectAttributes.addFlashAttribute("tableId", "countryTable");
            redirectAttributes.addFlashAttribute("formActionType", "/update");
        }
        return "redirect:/geo";
    }
    @PostMapping(value = "/country/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createCountry(CountryDto countryDto, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        try {
            countryClient.createOrUpdate(countryDto);
        } catch (HrException e) {
            redirectAttributes.addFlashAttribute("formError", e.getMessage());
            redirectAttributes.addFlashAttribute("formData", countryDto);
            redirectAttributes.addFlashAttribute("tableId", "countryTable");
            redirectAttributes.addFlashAttribute("formActionType", "/create");
        }
        return "redirect:/geo";
    }

    @PostMapping(value = "/location/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateLocation(LocationDto locationDto, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        try {
            locationClient.update(locationDto);
        } catch (HrException e) {
            redirectAttributes.addFlashAttribute("formError", e.getMessage());
            redirectAttributes.addFlashAttribute("formData", locationDto);
            redirectAttributes.addFlashAttribute("tableId", "locationTable");
            redirectAttributes.addFlashAttribute("formActionType", "/update");
        }
        return "redirect:/geo";
    }
    @PostMapping(value = "/location/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createLocation(LocationDto locationDto, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        try {
            locationClient.create(locationDto);
        } catch (HrException e) {
            redirectAttributes.addFlashAttribute("formError", e.getMessage());
            redirectAttributes.addFlashAttribute("formData", locationDto);
            redirectAttributes.addFlashAttribute("tableId", "locationTable");
            redirectAttributes.addFlashAttribute("formActionType", "/create");
        }
        return "redirect:/geo";
    }


}

