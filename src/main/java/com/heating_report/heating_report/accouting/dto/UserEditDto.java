package com.heating_report.heating_report.accouting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserEditDto {
    @Valid
    @Pattern(regexp = "^(?![-\s])[a-zA-Z\s-]+(?<![-\s])$", message = "First name must contain only letters and spaces")
    @NotNull(message = "First name must contain only letters and spaces")
    @NotBlank(message = "First name must contain only letters and spaces")
     String firstName;

    @Pattern(regexp = "^(?![-\s])[a-zA-Z\s-]+(?<![-\s])$", message = "Last name must contain only letters and spaces")
    @NotNull(message = "Last name must contain only letters and spaces")
    @NotBlank(message = "Last name must contain only letters and spaces")
     String lastName;
     String email;
     String image;
     Boolean status;
     String roles;
     boolean isEmailActivated;
     String codeForEmail;
}
