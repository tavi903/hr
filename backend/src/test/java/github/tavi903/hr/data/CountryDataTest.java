package github.tavi903.hr.data;

import github.tavi903.hr.repository.ICountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CountryDataTest {
    @Autowired
    private ICountryRepository countryRepository;

    @Test
    void when_aCountrySearchByInitialChar_then_DataIsRetrieved() {
        countryRepository.findByInitialLetter('C').forEach(System.out::println);
    }

}
