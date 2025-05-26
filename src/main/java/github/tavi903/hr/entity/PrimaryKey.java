package github.tavi903.hr.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Embeddable
@EqualsAndHashCode
@ToString
@Data
public class PrimaryKey {
    private Long id;

    public static PrimaryKey of(Long id) {
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.setId(id);
        return primaryKey;
    }
}
