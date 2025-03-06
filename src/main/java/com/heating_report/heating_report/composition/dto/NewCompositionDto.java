package com.heating_report.heating_report.composition.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewCompositionDto {
    String name;
    String book;
    String number;
    String theme;
}
