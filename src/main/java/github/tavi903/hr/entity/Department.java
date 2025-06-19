package github.tavi903.hr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "DEPARTMENTS")
@Data
public class Department {
    @Id
    private Long id;
    private String departmentName;
    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private Location location;
    private Long managerId;
}
