package github.tavi903.hr.http.client;

import github.tavi903.hr.dto.RegionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "regionClient", url="${hr.backend.url}", path="/region")
public interface RegionClient {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    RegionDto create(@RequestBody RegionDto regionDto);

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    RegionDto update(@RequestBody RegionDto regionDto);

    @GetMapping(value = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<RegionDto> getAll(Pageable pageable);
}
