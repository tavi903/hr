package github.tavi903.hr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class JobDto {
    @NotBlank
    @Pattern(regexp = "[A-Z_]+")
    @Size(min = 3, max = 25)
    String id;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z ]+")
    @Size(min = 3, max = 25)
    String jobTitle;
    @NotNull
    @Positive
    Double minSalary;
    @NotNull
    @Positive
    Double maxSalary;
}
