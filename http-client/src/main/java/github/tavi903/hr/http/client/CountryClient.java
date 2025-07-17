package github.tavi903.hr.http.client;

import github.tavi903.hr.dto.CountryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "countryClient", url="${hr.backend.url}", path="/country")
public interface CountryClient {
    @PutMapping
    CountryDto createOrUpdate(CountryDto countryDto); //  @RequestHeader(name = "Authorization") String basicAuth

    @GetMapping("get-all")
    List<CountryDto> getAll(@RequestParam(required = false) Long regionId);
}
