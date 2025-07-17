package github.tavi903.hr.config;

import lombok.SneakyThrows;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

import static github.tavi903.hr.config.AppConstants.PROFILE_POSTGRES;

/**
 * Nota: aggiunto anche in spring.factories
 */
public class PostgresDBInitializer implements SpringApplicationRunListener {

    public PostgresDBInitializer(SpringApplication application, String[] args) {
        // Il costruttore è richiesto da Spring Boot
    }


    @SuppressWarnings("all")
    @SneakyThrows
    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext,
                                    ConfigurableEnvironment environment) {
        if (Arrays.stream(environment.getActiveProfiles()).anyMatch(profile -> PROFILE_POSTGRES.equals(profile))) {
            DataSource dataSource = new SimpleDriverDataSource(
                    new org.postgresql.Driver(),
                    environment.getProperty("spring.datasource.url"),
                    environment.getProperty("spring.datasource.username"),
                    environment.getProperty("spring.datasource.password")
            );
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DROP SCHEMA IF EXISTS public CASCADE; CREATE SCHEMA public;");
            preparedStatement.execute();
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
            databasePopulator.addScript(new ClassPathResource("db-scripts/schema-postgres.sql"));
            databasePopulator.addScript(new ClassPathResource("db-scripts/data-postgres.sql"));
            databasePopulator.execute(dataSource);
        }
    }

}
