package github.tavi903.hr.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name = "JOB_HISTORY")
@Data
public class JobHistory {
    @EmbeddedId
    private PrimaryKey primaryKey;
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "JOB_ID")
    private Job job;
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class PrimaryKey {
        private Long employeeId;
        private Date startDate;
    }

    public static PrimaryKey createPK(Long employeeId, Date startDate) {
        return new PrimaryKey(employeeId, startDate);
    }


}
