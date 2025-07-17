package github.tavi903.hr.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Builder
@Value
public class EmployeeDto {
    Long id;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z ]+")
    @Size(min = 3, max = 25)
    String firstName;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z ]+")
    @Size(min = 3, max = 25)
    String lastName;
    @NotBlank
    @Email(message = "Email Not Valid!", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    String email;
    String phoneNumber;
    @NotNull
    Date hireDate;
    @NotBlank
    String jobId;
    String jobTitle;
    @NotNull
    @Positive
    Double salary;
    Double commissionPct;
    Long managerId;
    @NotNull
    Long departmentId;
    String departmentName;
}
