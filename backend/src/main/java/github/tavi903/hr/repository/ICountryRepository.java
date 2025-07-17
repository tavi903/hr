package github.tavi903.hr.repository;

import github.tavi903.hr.entity.Country;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICountryRepository extends JpaRepository<Country, String> {
    List<Country> findAllByRegion_id(Long regionId, Sort sort);
}
