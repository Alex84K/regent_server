package com.heating_report.heating_report.composition.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CompositionDto {
    String id;
    String name;
    String book;
    String number;
    String theme;
    LocalDate lastData;
    Boolean inWork;
    String lastDirigent;
}
