package github.tavi903.hr.frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.tavi903.hr.dto.EmployeeDto;
import github.tavi903.hr.dto.EmployeeSearchDto;
import github.tavi903.hr.frontend.View.ViewUtils;
import github.tavi903.hr.frontend.components.Form;
import github.tavi903.hr.frontend.components.Table;
import github.tavi903.hr.http.client.EmployeeClient;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.function.ServerRequest;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("employee")
public class EmployeeController {
    private static final TypeReference<Map<String, String>> MAP_TYPE_REFERENCE = new TypeReference<>() {};
    private static final DateTimeFormatter SIMPLE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final EmployeeClient employeeClient;
    private final ObjectMapper objectMapper;

    @GetMapping
    public String employee(Model model, HttpSession httpSession) throws JsonProcessingException, ParseException {
        Integer page = ViewUtils.getPageAttribute(httpSession, "employeeTable.page");
        PageRequest pageRequest = PageRequest.of(page, 12, Sort.by("lastName", "firstName"));

        Map<String, String> searchMap = getSearchMap(httpSession);
        EmployeeSearchDto searchDto = fromSearchMap(searchMap);
        Page<EmployeeDto> employeeDtoPage = employeeClient.search(searchDto, pageRequest);

        Form searchForm = Form.builder()
                .elements(
                        List.of(
                                Form.Label.builder().innerText("FirstName").build(),
                                Form.Input.builder().name("firstName")
                                        .value(searchMap.getOrDefault("firstName", ""))
                                        .type(Form.Input.Type.TEXT)
                                        .build(),
                                Form.Label.builder().innerText("LastName").build(),
                                Form.Input.builder().name("lastName")
                                        .value(searchMap.getOrDefault("lastName", ""))
                                        .type(Form.Input.Type.TEXT)
                                        .build(),
                                Form.Label.builder().innerText("HireDate After").build(),
                                Form.Input.builder().name("hireDateAfter")
                                        .value(searchMap.getOrDefault("hireDateAfter", ""))
                                        .type(Form.Input.Type.DATE)
                                        .build()
                        )
                )
                .build();
        searchForm.withJson(objectMapper.writeValueAsString(searchForm));

        Table<EmployeeDto> table = Table.of(
                employeeDtoPage.getContent(),
                employeeDto -> employeeDto.getId().toString(),
                List.of(
                        EmployeeDto::getFirstName,
                        EmployeeDto::getLastName,
                        employeeDto -> LocalDate.ofInstant(employeeDto.getHireDate().toInstant(), ZoneId.systemDefault()).format(SIMPLE_DATE_FORMAT),
                        EmployeeDto::getPhoneNumber,
                        EmployeeDto::getJobTitle,
                        employeeDto -> employeeDto.getSalary().toString()
                )
        )
        .withHeaders(
                List.of("FirstName", "LastName", "HireDate", "Phone Number", "Job Title", "Salary")
        )
        .withPagination(
                Table.Pagination.of(12, page, employeeDtoPage.getTotalElements(), "/employee")
        ).andIsSearchEnabled()
                .withFilename("employees.csv")
                .withPathToDownloadCsv("employee")
                .withSearchForm(searchForm);
        model.addAttribute("employeeTable", table);
        return "employee";
    }

    @SneakyThrows
    private Map<String, String> getSearchMap(HttpSession httpSession) {
        String searchDtoString = (String) httpSession.getAttribute("employeeTable.searchDto");
        if (searchDtoString != null)
            return objectMapper.readValue(searchDtoString, MAP_TYPE_REFERENCE);
        return Map.of();
    }

    private EmployeeSearchDto fromSearchMap(Map<String, String> searchMap) {
        EmployeeSearchDto searchDto = EmployeeSearchDto.builder().build();
        if (!searchMap.isEmpty()) {
            EmployeeSearchDto.EmployeeSearchDtoBuilder searchBuilder = EmployeeSearchDto.builder();
            if (searchMap.containsKey("firstName"))
                searchBuilder.firstNameLike(searchMap.get("firstName"));
            if (searchMap.containsKey("lastName"))
                searchBuilder.lastNameLike(searchMap.get("lastName"));
            if (searchMap.get("hireDateAfter") != null &&  !"".equals(searchMap.get("hireDateAfter"))) {
                LocalDate localDate = LocalDate.parse(searchMap.get("hireDateAfter"), SIMPLE_DATE_FORMAT);
                LocalDateTime localDateTime = localDate.atTime(0, 0);
                searchBuilder.hireDateGreaterThanOrEqual(new Date(localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()));
            }
            searchDto = searchBuilder.build();
        }
        return searchDto;
    }

    @GetMapping(value = "/download-csv", produces = "text/csv")
    @ResponseBody
    public ResponseEntity<byte[]> downloadCsv(HttpSession httpSession) {
        var searchMap = getSearchMap(httpSession);
        var searchDto = fromSearchMap(searchMap);
        Page<EmployeeDto> employees = employeeClient.search(searchDto, PageRequest.of(0, Integer.MAX_VALUE, Sort.by("lastName", "firstName")));
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("FirstName,LastName,HireDate,Phone Number,Job Title,Salary\n");
        employees.getContent().forEach(
                employeeDto -> {
                    csvBuilder.append(
                            String.join(",",
                                employeeDto.getFirstName(),
                                employeeDto.getLastName(),
                                LocalDate.ofInstant(employeeDto.getHireDate().toInstant(),ZoneId.systemDefault()).format(SIMPLE_DATE_FORMAT),
                                employeeDto.getPhoneNumber(),
                                employeeDto.getJobTitle(),
                                employeeDto.getSalary().toString()
                            )
                    );
                    csvBuilder.append("\n");
                }
        );
        return new ResponseEntity<>(
                csvBuilder.toString().getBytes(StandardCharsets.UTF_8),
                MultiValueMap.fromSingleValue(Map.of(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"employees.csv\"")),
                HttpStatus.OK
        );
    }

}
