package github.tavi903.hr.json;

import github.tavi903.hr.dto.RegionDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class RegionJsonTest {
    @Autowired
    private JacksonTester<RegionDto> jacksonTester;

    @Test
    void given_aRegion_when_Serialize_thenReturnTheJson() throws IOException {
        RegionDto region = RegionDto.builder().id(22L).regionName("PippoLandia").build();
        assertThat(this.jacksonTester.write(region)).isEqualToJson("{\"id\": 22, \"regionName\": \"PippoLandia\"}");
    }

    @Test
    void given_AJsonRegion_when_Deserialize_thenReturnCorrectRegion() throws IOException {
        RegionDto region = jacksonTester.parse("{\"id\": 22, \"regionName\": \"PippoLandia\"}").getObject();
        RegionDto expected = RegionDto.builder().id(22L).regionName("PippoLandia").build();
        Assertions.assertEquals(expected, region);
    }

}
