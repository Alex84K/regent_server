package com.heating_report.heating_report.accouting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.heating_report.heating_report.accouting.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String telefon;
    private String password;
    private String image;
    private LocalDateTime dateRegistered;
    private LocalDateTime lastLoginTime;
    @JsonProperty("status")
    private Boolean status;
    private String roles;
    @JsonProperty("isEmailConfirmed")
    private boolean isEmailActivated;
    private String codeForEmail;
}

