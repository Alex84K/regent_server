package com.heating_report.heating_report.email_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewEmailDto {
    String emailId;
    String email;
    String userId;
    String role;
}
