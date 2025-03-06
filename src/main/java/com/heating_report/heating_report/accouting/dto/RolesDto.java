package com.heating_report.heating_report.accouting.dto;


import lombok.*;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolesDto {
    String login;
    Set<String> roles;
}