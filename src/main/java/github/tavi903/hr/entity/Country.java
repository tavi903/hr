package github.tavi903.hr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "COUNTRIES")
@Data
@EqualsAndHashCode(of = "id")
@ToString
public class Country {
    @Id
    private String id;
    private String countryName;
    @ManyToOne
    @JoinColumn(name = "REGION_ID")
    private Region region;
}
