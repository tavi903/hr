package github.tavi903.hr;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Profile(AppConstants.PROFILE_POSTGRES)
@Component
@RequiredArgsConstructor
public class PostgresDBInitializer {
    private final DataSource dataSource;

    @PostConstruct
    public void init() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DROP SCHEMA IF EXISTS public CASCADE; CREATE SCHEMA public;");
        preparedStatement.execute();
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource("db-scripts/schema-postgres.sql"));
        databasePopulator.addScript(new ClassPathResource("db-scripts/data-postgres.sql"));
        databasePopulator.execute(dataSource);
    }

}
