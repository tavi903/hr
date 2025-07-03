package github.tavi903.hr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class JobDto {
    @NotBlank
    @Pattern(regexp = "[A-Z_]+")
    @Size(min = 3, max = 25)
    private String id;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z ]+")
    @Size(min = 3, max = 25)
    private String jobTitle;
    @NotNull
    @Positive
    private Double minSalary;
    @NotNull
    @Positive
    private Double maxSalary;
}
