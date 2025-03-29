package com.heating_report.heating_report.planer_events.service;

import com.heating_report.heating_report.planer_events.dto.EventRespDto;
import com.heating_report.heating_report.planer_events.dto.NewEventDto;

public interface EventListService {
    Iterable<EventRespDto> getAllEvents();
    Iterable<EventRespDto> getAllEventsByMonth(Integer month, Integer year);
    EventRespDto addEvent(NewEventDto event);
    EventRespDto getEvent(String eventId);
    EventRespDto updateEvent(EventRespDto event);
    EventRespDto deleteEvent(String eventId);
}
