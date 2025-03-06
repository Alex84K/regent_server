package com.heating_report.heating_report.accouting.dto;

import lombok.Getter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Getter
public class UserRegistrationDto {

    @Valid
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!$#%]?)(?!^[0-9]*$)[a-zA-Z0-9!$#%]{3,10}$", message = "Username must be unique, 3-10 characters long, and can include letters, numbers, and !, $, #, %. It cannot be only numbers.")
    @NotNull(message = "Username is mandatory")
    String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!$#%])[A-Za-z\\d!$#%]{8,}$", message = "Password must be 8 or more characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character (!, $, #, %).")
    @NotNull(message = "Password is mandatory")
    String password;
    @NotBlank(message = "Email has invalid format")
    @NotNull(message = "Email is mandatory")
    @Pattern(regexp ="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Email has invalid format")
    String email;
    public String getEmail() {
        return email.toLowerCase();
    }
}


