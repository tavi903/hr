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
@Table(name = "LOCATIONS")
@Data
@EqualsAndHashCode(of = "id")
@ToString
public class Location {
    @Id
    private Long id;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;
    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;
}
