package github.tavi903.hr;

import github.tavi903.hr.entity.PrimaryKey;
import github.tavi903.hr.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
class HumanResourcesApplicationTests {

	@Autowired
	private RegionRepository regionRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void tryRepo() {
		System.out.println(regionRepository.findAll());
		System.out.println(regionRepository.findById(PrimaryKey.of(30L)));
	}

}
