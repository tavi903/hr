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
@Table(name = "LOCATIONS")
@Data
@EqualsAndHashCode(of = "id")
@ToString
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationSeq")
    @SequenceGenerator(name = "locationSeq", sequenceName = "locations_seq", allocationSize = 100)
    private Long id;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;
}
