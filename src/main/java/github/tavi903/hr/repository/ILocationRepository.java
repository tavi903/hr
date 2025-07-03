package github.tavi903.hr.repository;

import github.tavi903.hr.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findByCountryId(String countryId, Pageable pageable);
}
