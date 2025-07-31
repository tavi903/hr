package github.tavi903.hr.http.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.tavi903.hr.exception.HrException;
import github.tavi903.hr.dto.DepartmentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

@SuppressWarnings("all")
@SpringBootTest(classes = TestingApp.class)
@TestPropertySource(properties = {
        "hr.backend.url=http://localhost:8080/api/v1/"
})
public class ClientsTest {

    @Autowired
    private CountryClient countryClient;
    @Autowired
    private DepartmentClient departmentClient;
    @Autowired
    private RegionClient regionClient;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void given_CountryClient_when_GetAll_thenRetrieveAllTheData() {
        Assertions.assertEquals(25, countryClient.getAll(null, Pageable.unpaged()).getContent().size());
    }

    @Test
    void given_DepartmentClient_when_GetAll_thenRetrievedPaged() {
        Assertions.assertEquals(2, departmentClient.getAll("GB", PageRequest.of(0, 20)).getContent().size());
        departmentClient.createOrUpdate(
          DepartmentDto.builder()
                  .locationId(2500L)
                  .departmentName("Testing")
                  .build()
        );
        Assertions.assertEquals(3, departmentClient.getAll("GB", PageRequest.of(0, 20)).getContent().size());
    }

    @Test
    void given_createDepartmentWithoutLocation_thenAnErrorIsReturned() {
        Exception actualException = null;
        try {
            DepartmentDto response = departmentClient.createOrUpdate(
                    DepartmentDto.builder()
                            .departmentName("Testing")
                            .build()
            );
        } catch (HrException e) {
            actualException = e;
        }
        Assertions.assertInstanceOf(HrException.class, actualException);
    }

    @Test
    void given_getAllRegion_thenAllDataRetrieved() {
        Assertions.assertEquals(5, regionClient.getAll(Pageable.unpaged()).getContent().size());
    }
}
