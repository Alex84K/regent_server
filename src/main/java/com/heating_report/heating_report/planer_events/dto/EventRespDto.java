package com.heating_report.heating_report.planer_events.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRespDto {
    String id;
    String title;
    String book;
    String number;
    String regent;
    LocalDateTime start;
}
