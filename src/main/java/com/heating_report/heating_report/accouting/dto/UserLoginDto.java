package com.heating_report.heating_report.accouting.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserLoginDto {
    String username;
    @NotBlank(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    String password;
}
