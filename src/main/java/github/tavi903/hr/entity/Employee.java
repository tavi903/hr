package github.tavi903.hr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "EMPLOYEES")
@Data
public class Employee {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date hireDate;
    @ManyToOne
    @JoinColumn(name = "JOB_ID")
    private Job job;
    private Double salary;
    private Double commissionPct;
    @ManyToOne
    @JoinColumn(name = "MANAGER_ID")
    private Employee manager;
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;
}
