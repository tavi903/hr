package github.tavi903.hr;

import github.tavi903.hr.repository.ICountryRepository;
import github.tavi903.hr.repository.IDepartmentRepository;
import github.tavi903.hr.repository.IEmployeeRepository;
import github.tavi903.hr.repository.IJobHistoryRepository;
import github.tavi903.hr.repository.IJobRepository;
import github.tavi903.hr.repository.IRegionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("local")
class HumanResourcesApplicationTests {

	@Autowired
	private IRegionRepository regionRepository;
	@Autowired
	private ICountryRepository countryRepository;
	@Autowired
	private IJobRepository jobRepository;
	@Autowired
	private IJobHistoryRepository jobHistoryRepository;
	@Autowired
	private IDepartmentRepository departmentRepository;
	@Autowired
	private IEmployeeRepository employeeRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void tryRepo() {
//		countryRepository.findById("IT");
//		System.out.println(jobRepository.findAll());
//		System.out.println(jobHistoryRepository.findAll());
		System.out.println(jobHistoryRepository.findByPrimaryKey_EmployeeId(101L));
	}

}
