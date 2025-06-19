package github.tavi903.hr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "JOBS")
@Data
@EqualsAndHashCode(of = "id")
@ToString
public class Job {
    @Id
    private String id;
    private String jobTitle;
    private Double minSalary;
    private Double maxSalary;
}
