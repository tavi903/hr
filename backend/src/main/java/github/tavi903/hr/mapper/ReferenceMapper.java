package github.tavi903.hr.mapper;

import github.tavi903.hr.entity.Country;
import github.tavi903.hr.entity.Department;
import github.tavi903.hr.entity.Employee;
import github.tavi903.hr.entity.Job;
import github.tavi903.hr.entity.Location;
import github.tavi903.hr.entity.Region;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class ReferenceMapper {
    @PersistenceContext
    private EntityManager em;

    public Job mapStringToJob(String id) {
        if (id == null || id.isEmpty()) return null;
        return em.getReference(Job.class, id);
    }
    public Department mapLongToDepartment(Long id) {
        if (id == null) return null;
        return em.getReference(Department.class, id);
    }
    public Employee mapLongToEmployee(Long id) {
        if (id == null) return null;
        return em.getReference(Employee.class, id);
    }
    public Region mapLongToRegion(Long id) {
        if (id == null) return null;
        return em.getReference(Region.class, id);
    }
    public Country mapStringToCountry(String id) {
        if (id == null || id.isEmpty()) return null;
        return em.getReference(Country.class, id);
    }
    public Location mapLongToLocation(Long id) {
        if (id == null) return null;
        return em.getReference(Location.class, id);
    }
}
