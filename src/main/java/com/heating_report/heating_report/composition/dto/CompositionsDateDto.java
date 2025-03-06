package com.heating_report.heating_report.composition.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CompositionsDateDto {
    LocalDate before;
    LocalDate after;
}
