package random;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Classe usata per testare le API che permettono di lavorare con le date in Java.
 */
public class DateTest {
    @Test
    void thisIsDate() {
        // creates with number of milliseconds January 1, 1970, 00:00:00 GMT
        java.util.Date date = new java.util.Date();
        System.out.println(date); // return JVM Timezone, Fri Jul 04 21:11:42 CEST 2025
        Instant instant = date.toInstant(); // Instant is UTC-0, 2025-07-04T19:11:42.663Z
        System.out.println(instant);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("America/New_York"));
        System.out.println(zonedDateTime); // 2025-07-04T15:11:42.663-04:00[America/New_York]
        LocalDate now = LocalDate.now();
        System.out.println(now); // 2025-07-04
        LocalDateTime nowWithTime = LocalDateTime.now(); // 2025-07-04T21:33:10.775462315
        System.out.println(nowWithTime); // 2025-07-04T21:34:34.707545130
    }

    @Test
    void dateConversion() {
        System.out.println(        Date.from(LocalDateTime.of(2016, 1, 1, 0, 0).toInstant(ZoneOffset.UTC)).getTime());
    }
}
