package github.tavi903.hr;

import github.tavi903.hr.dto.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(classes = EmployeeControllerTest.WebConfig.class)
public class EmployeeControllerTest {
    @Autowired
    private WebApplicationContext applicationContext;

    @Test
    void given_SomeEmployee_when_InvokingController_thenTheyWillBeReturned() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/get-all?page=0&size=5"))
                .andExpect(status().isOk())
                .andReturn();
        // TODO: use JsonPath to analyze the response
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
    @Test
    void given_SomeEmployee_when_InvokingController_thenTheyWillBeReturnedV2() throws Exception {
        ParameterizedTypeReference<Page<EmployeeDto>> parameterizedTypeReference = new ParameterizedTypeReference<>() {};
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        WebTestClient client = MockMvcWebTestClient.bindTo(mockMvc).build();
        WebTestClient.ResponseSpec response =
                client.get().uri("/api/v1/employee/get-all?page=0&size=5", HttpMethod.GET, null, parameterizedTypeReference).exchange();
        response.expectStatus().is2xxSuccessful();
    }

    @EnableWebMvc
    @ComponentScan(basePackages = "github.tavi903.hr")
    @PropertySource(value = "classpath:application.properties")
    static class WebConfig {

    }

}
