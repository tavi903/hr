package github.tavi903.hr.json;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;

import java.util.Date;

@JsonTest
public class DateJsonTest {
    @Autowired
    private JacksonTester<DateWrapper> jacksonTester;

    @Test
    @SneakyThrows
    void given_aDate_when_Deserialized_thenHowItWorks() {
        String dataStr = "{\"date\": \"2025-07-05\"}";
        ObjectContent<DateWrapper> parsedJson = jacksonTester.parse(dataStr);
        System.out.println(parsedJson.getObject());
    }

    @Data
    @NoArgsConstructor
    static class DateWrapper {
        private Date date;
    }

}
