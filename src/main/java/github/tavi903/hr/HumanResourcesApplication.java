package github.tavi903.hr;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.sql.SQLException;

@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
public class HumanResourcesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumanResourcesApplication.class, args);
	}

	@Profile(AppConstants.PROFILE_LOCAL)
	@Bean(
		initMethod = "start",
		destroyMethod = "stop"
	)
	public Server h2Server(@Autowired Environment env) throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
	}

}
