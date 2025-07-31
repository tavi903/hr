package github.tavi903.hr.repository;

import github.tavi903.hr.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICountryRepository extends JpaRepository<Country, String> {
    Page<Country> findAllByRegion_id(Long regionId, Pageable pageable);
    @Query("select c from Country c where countryName LIKE ?1%")
    List<Country> findByInitialLetter(char initialLetter);
}
