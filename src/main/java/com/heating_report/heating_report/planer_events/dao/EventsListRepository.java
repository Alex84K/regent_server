package com.heating_report.heating_report.planer_events.dao;

import com.heating_report.heating_report.planer_events.dto.NewEventDto;
import com.heating_report.heating_report.planer_events.model.EventPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventsListRepository extends MongoRepository<EventPlan, String> {
    Optional<EventPlan> findEventById(String eventId);
}
