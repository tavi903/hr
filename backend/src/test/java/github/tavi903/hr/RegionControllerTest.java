package github.tavi903.hr;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.tavi903.hr.dto.RegionDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class RegionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void given_aRegionToInsert_when_PostRegion_thenGetAllRetrievesIt() throws Exception {
        RegionDto regionDto = RegionDto.builder().regionName("Paperopoli").build();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/region")
                        .header("Authorization", "Basic bWFuYWdlcjpwYXNzd29yZA==")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regionDto))
                )
                .andExpect(status().isOk());
    }

}
