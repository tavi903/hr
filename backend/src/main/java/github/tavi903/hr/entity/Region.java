package github.tavi903.hr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "regions")
@Data
@EqualsAndHashCode(of = "id")
@ToString
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "regionSeq")
    @SequenceGenerator(name = "regionSeq", sequenceName = "regions_seq", allocationSize = 100)
    private Long id;
    private String regionName;
}
