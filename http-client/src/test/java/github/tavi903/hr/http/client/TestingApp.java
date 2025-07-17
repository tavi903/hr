package github.tavi903.hr.http.client;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class TestingApp {
    public static void main(String[] args) {
        SpringApplication.run(TestingApp.class, args);
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("manager", "password");
    }

}
