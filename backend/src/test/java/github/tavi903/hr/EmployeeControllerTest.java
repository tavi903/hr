package github.tavi903.hr;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.tavi903.hr.dto.EmployeeDto;
import github.tavi903.hr.dto.EmployeeSearchDto;
import github.tavi903.hr.utils.PageJacksonModule;
import github.tavi903.hr.utils.SortJacksonModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@SuppressWarnings("all")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {
    @Autowired
    private WebApplicationContext applicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new PageJacksonModule());
        objectMapper.registerModule(new SortJacksonModule());
    }

    @Test
    void given_SomeEmployee_when_InvokingController_thenTheyWillBeReturned() throws Exception {
        var parameterizedTypeReference = new ParameterizedTypeReference<Page<EmployeeDto>>() {};
        var response = testRestTemplate.exchange(
                "/api/v1/employee/search?page=0&size=5", HttpMethod.POST,
                new HttpEntity<>(EmployeeSearchDto.builder().build(), MultiValueMap.fromSingleValue(Map.of("Authorization", "Basic bWFuYWdlcjpwYXNzd29yZA=="))),
                parameterizedTypeReference
        );
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(107, response.getBody().getTotalElements());
    }

    class SecurityConfigurer implements MockMvcConfigurer {

    }


    /**
     * Approccio che si basa su TestRestTemplate non funziona perché necessita un web server reale sotto.
     */
//    @Test
//    void given_SomeEmployee_when_InvokingController_thenTheyWillBeReturnedV2() throws Exception {
//        ParameterizedTypeReference<Page<EmployeeDto>> parameterizedTypeReference = new ParameterizedTypeReference<>() {};
//        ResponseEntity<Page<EmployeeDto>> response =
//                restTemplate.exchange("/api/v1/employee/get-all?page=0&size=5", HttpMethod.GET, null, parameterizedTypeReference);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }

    /**
     * Utilizzando MockMvcWebTestClient e avendo spring-webflux nel pom.xml almeno per i test si riesce a usare WebTestClient?
     * Vantaggi? Molto pochi e non ha molto senso, alla fine bisogna comunque testare usando json / jsonPath / xPath
     */
    @Disabled
    @Test
    void given_SomeEmployee_when_InvokingController_thenTheyWillBeReturnedV2() throws Exception {
        ParameterizedTypeReference<Page<EmployeeDto>> parameterizedTypeReference = new ParameterizedTypeReference<>() {};
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        WebTestClient client = MockMvcWebTestClient.bindTo(mockMvc).build();
        WebTestClient.ResponseSpec response =
                client.post().uri("/api/v1/employee/search?page=0&size=5").bodyValue(EmployeeSearchDto.builder().build()).exchange();
        response.expectStatus().is2xxSuccessful();
        response.expectBody(parameterizedTypeReference)
                .consumeWith(body -> Assertions.assertEquals(107, body.getResponseBody().getTotalElements()));
    }

}
