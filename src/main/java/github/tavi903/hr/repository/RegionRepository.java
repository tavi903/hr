package github.tavi903.hr.repository;

import github.tavi903.hr.entity.PrimaryKey;
import github.tavi903.hr.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, PrimaryKey> {
}
