package github.tavi903.hr.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "regions")
@Data
@EqualsAndHashCode(of = "primaryKey")
@ToString
public class Region {
    @EmbeddedId
    private PrimaryKey primaryKey;
    private String regionName;
}
