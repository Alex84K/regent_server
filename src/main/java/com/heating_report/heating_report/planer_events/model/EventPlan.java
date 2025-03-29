package com.heating_report.heating_report.planer_events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "events")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EventPlan {
    @Id
    String id;
    @Setter
    String title;
    @Setter
    String book;
    @Setter
    String number;
    @Setter
    String regent;
    @Setter
    LocalDateTime start;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EventPlan eventPlan = (EventPlan) o;
        return Objects.equals(title, eventPlan.title) && Objects.equals(book, eventPlan.book) && Objects.equals(number, eventPlan.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, book, number);
    }
}
