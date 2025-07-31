package github.tavi903.hr.service;

import github.tavi903.hr.dto.SummaryDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryDataService {
    private final static String SQL_COUNT_COUNTRIES_WITH_EMP =
            "SELECT COUNT(*) AS NUM_EMPLOYEES, c.ID, c.COUNTRY_NAME" +
                    " FROM EMPLOYEES e" +
                    " INNER JOIN DEPARTMENTS d" +
                    "    ON (d.ID = e.DEPARTMENT_ID)" +
                    " INNER JOIN LOCATIONS l" +
                    "    ON (l.ID = d.LOCATION_ID)" +
                    " INNER JOIN COUNTRIES c" +
                    "    ON (c.ID = l.COUNTRY_ID)" +
                    " GROUP BY c.ID, c.COUNTRY_NAME" +
                    " ORDER BY NUM_EMPLOYEES DESC";

    private static final String SQL_COUNT_LOCATION_WITH_EMP =
        "SELECT COUNT(*) AS NUM_EMPLOYEES, l.CITY" +
                " FROM EMPLOYEES e" +
                " INNER JOIN DEPARTMENTS d" +
                "   ON (d.ID = e.DEPARTMENT_ID)" +
                " INNER JOIN LOCATIONS l" +
                "   ON (l.ID = d.LOCATION_ID)" +
                " GROUP BY l.CITY" +
                " ORDER BY NUM_EMPLOYEES DESC";

    private final JdbcTemplate jdbcTemplate;

    public SummaryDataDto getSummaryData() {
        List<Pair<Integer, String>> countriesWithNumEmp = new ArrayList<>();
        jdbcTemplate.query(SQL_COUNT_COUNTRIES_WITH_EMP, resultSet -> {
            countriesWithNumEmp.add(
                    Pair.of(resultSet.getInt(1), resultSet.getString(3))
            );
        });
        List<Pair<Integer, String>> citiesWithNumEmp = new ArrayList<>();
        jdbcTemplate.query(SQL_COUNT_LOCATION_WITH_EMP, resultSet -> {
            citiesWithNumEmp.add(
                    Pair.of(resultSet.getInt(1), resultSet.getString(2))
            );
        });
        return SummaryDataDto.builder()
                .numEmployees(countriesWithNumEmp.stream().mapToInt(Pair::getFirst).sum())
                .numCountries(countriesWithNumEmp.size())
                .numCities(citiesWithNumEmp.size())
                .countryLabels(countriesWithNumEmp.stream().map(Pair::getSecond).toList())
                .numEmployeesPerCountry(countriesWithNumEmp.stream().map(Pair::getFirst).toList())
                .cityLabels(citiesWithNumEmp.stream().map(Pair::getSecond).toList())
                .numEmployeesPerCity(citiesWithNumEmp.stream().map(Pair::getFirst).toList())
                .build();
    }

}
