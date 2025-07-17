package github.tavi903.hr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DEPARTMENTS")
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "departmentName"})
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departmentSeq")
    @SequenceGenerator(name = "departmentSeq", sequenceName = "departments_seq", allocationSize = 100)
    private Long id;
    private String departmentName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private Employee manager;
}
