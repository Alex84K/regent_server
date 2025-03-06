package com.heating_report.heating_report.composition.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "composition")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Composition {
    @Id
    String id;
    @Setter
    String name;
    @Setter
    String book;
    @Setter
    String number;
    @Setter
    String theme;
    @Setter
    LocalDate lastData;
    @Setter
    Boolean inWork;
    @Setter
    String lastDirigent;
}
