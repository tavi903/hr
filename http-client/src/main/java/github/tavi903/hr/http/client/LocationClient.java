package github.tavi903.hr.http.client;

import github.tavi903.hr.dto.LocationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "locationClient", url="${hr.backend.url}", path="/location")
public interface LocationClient {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    LocationDto create(@RequestBody LocationDto locationDto);

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    LocationDto update(@RequestBody LocationDto locationDto);

    @GetMapping(value = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<LocationDto> getAll(@RequestParam(required = false) String countryId, Pageable pageable);
}
